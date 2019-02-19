package pl.bartlomiejstepien;

import pl.bartlomiejstepien.entities.Message;
import pl.bartlomiejstepien.entities.UserConnection;
import pl.bartlomiejstepien.listeners.Listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ChatServer implements Runnable
{
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final ServerSocket serverSocket;
    private final List<UserConnection> connectedUsers = new ArrayList<>();
    private final EventManager eventManager = EventManager.getInstance();

//    private final PrintWriter serverWriteStream;
//    private final BufferedReader serverReadStream;

    public ChatServer(int port) throws IOException
    {
        this.serverSocket = new ServerSocket(port);
//        this.serverWriteStream = new PrintWriter(this.serverSocket);
    }

    public EventManager getEventManager()
    {
        return this.eventManager;
    }

    public void sendMessage(String fromNickname, Message message)
    {
        for (UserConnection userConnection : connectedUsers)
        {
//            if (userConnection.getUsername().equals(fromNickname))
//                continue;

            userConnection.sendMessage(message);
        }

//        System.out.println(message);
    }

    public void registerEventListener(Listener listener)
    {
        this.eventManager.registerEventListener(listener);
    }

    @Override
    public void run()
    {
        //Listens for new clients
        while (true)
        {
            try
            {
                Socket client = this.serverSocket.accept();

                //Ask for login
                BufferedReader clientStreamReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter clientStreamWriter = new PrintWriter(client.getOutputStream());

                String username = loginUser(clientStreamReader, clientStreamWriter);

                UserConnection userConnection = new UserConnection(this, username, clientStreamWriter, clientStreamReader);
                this.connectedUsers.add(userConnection);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private String loginUser(BufferedReader clientStreamReader, PrintWriter clientStreamWriter) throws IOException
    {
        clientStreamWriter.println("Enter your username: ");
        clientStreamWriter.flush();
        String username;
        username = clientStreamReader.readLine();
        if (username.equals("") || username.equals(" "))
        {
            clientStreamWriter.println("Bad username! Enter username again: ");
            clientStreamWriter.flush();
            return loginUser(clientStreamReader, clientStreamWriter);
        }
        else if (this.connectedUsers.stream().anyMatch(x->x.getUsername().equals(username)))
        {
            clientStreamWriter.println("This username is already taken! Enter username again: ");
            clientStreamWriter.flush();
            return loginUser(clientStreamReader, clientStreamWriter);
        }
        else
        {
            clientStreamWriter.println("Correctly logged in as " + username + "!");
            clientStreamWriter.flush();
            return username;
        }
    }
}
