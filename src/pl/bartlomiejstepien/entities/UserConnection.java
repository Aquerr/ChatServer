package pl.bartlomiejstepien.entities;

import pl.bartlomiejstepien.ChatServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class UserConnection
{
    //Backwards reference
    private final ChatServer chatServer;

    private final String userName;
    private final PrintWriter streamWriter;
    private final BufferedReader streamReader;

    private final Thread userThread = new Thread(this::startListening);

    public UserConnection(ChatServer chatServer, String userName, PrintWriter streamWriter, BufferedReader streamReader)
    {
        this.chatServer = chatServer;
        this.userName = userName;
        this.streamWriter = streamWriter;
        this.streamReader = streamReader;
        userThread.start();
    }

    public String getUsername() {
        return this.userName;
    }

    private void startListening()
    {
        String message;
        try
        {
            while (((message = this.streamReader.readLine()) != null))
            {
                this.chatServer.sendMessage(this.userName, message);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void sendMessageToUser(String message)
    {
        this.streamWriter.println(message);
        this.streamWriter.flush();
    }
}
