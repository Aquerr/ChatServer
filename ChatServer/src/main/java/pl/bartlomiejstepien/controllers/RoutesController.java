package pl.bartlomiejstepien.controllers;

import pl.bartlomiejstepien.ChatServer;
import pl.bartlomiejstepien.routes.Result;
import pl.bartlomiejstepien.routes.Route;
import pl.bartlomiejstepien.routes.Routes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.*;

/**
 * Main Controller used by application to determine what class and method should be used.
 */
public final class RoutesController
{
    private static RoutesController INSTANCE;
    private ChatServer chatServer;

    public RoutesController(ChatServer chatServer)
    {
        this.chatServer = chatServer;
    }

    public static RoutesController getInstance(ChatServer chatServer) {
        if(INSTANCE == null)
        {
            INSTANCE = new RoutesController(chatServer);
        }
        return INSTANCE;
    }

    public void handleRequestSocket(final Socket client)
    {
        try
        {
            //Read HTTP Request
            final BufferedReader clientStreamReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            final List<String> list = new ArrayList<>();
            String line = null;
            while((line = clientStreamReader.readLine()) != null) {
                System.out.println(line);
                list.add(line);
                if(line.contains("keep-alive"))
                    break;
            }

            //1 line = Request method type, request url and HTTP version
            String[] firstLine = list.get(0).split(" ");
            String httpMethodType = firstLine[0];
            String url = firstLine[1];
            String httpversion = firstLine[2];

            //Search for method in controllers
            final Result result = runRoute(httpMethodType, url);

            if(url.contains("sendMessage"))
            {
                for(Socket socket : chatServer.getClients())
                {
                    PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                    printWriter.println(result.getResponseBody());
                    printWriter.flush();
                }
            }

            //Send Response
            final PrintWriter clientStreamWriter = new PrintWriter(client.getOutputStream());
            clientStreamWriter.println(result.getHttpVersion() + " " + result.getStatusCode());
            clientStreamWriter.println("Content-Type: " + result.getContentType());
            clientStreamWriter.println("");
            clientStreamWriter.println(result.getResponseBody());
            clientStreamWriter.flush();
            clientStreamWriter.close();
            clientStreamReader.close();
            client.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private Result runRoute(final String methodType, final String url)
    {
        String[] splitUrl = url.split("/");
        final Map<String, String> params = new HashMap<>();

        Class routesClass = Routes.class;
        Field[] fields = routesClass.getFields();
        Route matchedRoute = null;
        for(final Field field : fields)
        {
            try
            {
                Route route = (Route) field.get(null);
                if(!route.getHttpMethod().equals(methodType))
                    continue;

                String[] splitRouteUrl = route.getUrl().split("/");
                if(splitUrl.length != splitRouteUrl.length)
                    continue;

                boolean correctUrlParts = true;
                for(int i = 0; i < splitRouteUrl.length; i++)
                {
                    final String routeUrlPart = splitRouteUrl[i];
                    final String urlPart = splitUrl[i];

                    if(!routeUrlPart.equals(urlPart)) {
                        //Check parameter
                        //If it is not then break loop
                        if(routeUrlPart.startsWith("{") && routeUrlPart.endsWith("}"))
                        {
                            params.put(routeUrlPart.replace("{", "").replace("}", "").trim(), urlPart);
                        }
                        else
                        {
                            correctUrlParts = false;
                            break;
                        }
                    }

                    if(routeUrlPart.equals(urlPart))
                        continue;
                }

                if(correctUrlParts) {
                    matchedRoute = route;
                    break;
                }
            }
            catch(IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }

        if(matchedRoute == null) {
//            return Optional.empty();
            throw new UnsupportedOperationException("No route has been found for " + url);
        }

        //Get controller
        try
        {
            final String methodName = matchedRoute.getMethodName().substring(0, matchedRoute.getMethodName().indexOf("("));
            final Class controllerClass = Class.forName(matchedRoute.getClassName());
            for(final Method method : controllerClass.getMethods())
            {
                if(method.getName().equals(methodName) && method.getParameterCount() == matchedRoute.getParams().length)
                {
//                    final Parameter[] parameters = method.getParameters();
                    return (Result) method.invoke(null, params.values().toArray());
                }
            }
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch(InvocationTargetException e)
        {
            e.printStackTrace();
        }

        return null;

//        else return Optional.of(matchedRoute);
    }
}
