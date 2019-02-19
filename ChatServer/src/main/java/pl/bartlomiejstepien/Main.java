package pl.bartlomiejstepien;

import pl.bartlomiejstepien.listeners.MessageReceiveListener;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
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
