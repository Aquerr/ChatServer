package pl.bartlomiejstepien;

import pl.bartlomiejstepien.listeners.MessageReceiveListener;

import java.io.*;

public class Main {

    public static void main(String[] args) {

//        try
//        {
//            ServerSocket serverSocket = new ServerSocket(9999, 10000);
//            Socket socket = serverSocket.accept();
////            System.out.println(socket);
//
////            InputStream inputStream = socket.getInputStream();
////            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
////            String line = null;
////            while((line = bufferedReader.readLine()) != null) {
////                System.out.println(line);
////            }
//
//            OutputStream outputStream = socket.getOutputStream();
//            PrintWriter printWriter = new PrintWriter(outputStream);
//            printWriter.println("HTTP/1.1 200 OK");
//            printWriter.println("Content-Type: text/html; charset=UTF-8");
//            printWriter.println("");
//            printWriter.println("<html><head></head><body>ELO</body></html>");
//            printWriter.flush();
//
//            Thread.sleep(2000);
//            printWriter.close();
//            System.out.println("ServerSocket closed!");
////            bufferedReader.close();
//        }
//        catch(IOException e)
//        {
//            e.printStackTrace();
//        }
//        catch(InterruptedException e)
//        {
//            e.printStackTrace();
//        }


        try
        {
            System.out.println("Starting server on port 25568");
            ChatServer chatServer = new ChatServer(25568);
            chatServer.registerEventListener(new MessageReceiveListener());
            chatServer.run();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
