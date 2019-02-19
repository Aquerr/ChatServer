package pl.bartlomiejstepien;

import pl.bartlomiejstepien.listeners.MessageReceiveListener;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try
        {
            ChatServer chatServer = new ChatServer(4444);
            chatServer.registerEventListener(new MessageReceiveListener());
            chatServer.run();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
