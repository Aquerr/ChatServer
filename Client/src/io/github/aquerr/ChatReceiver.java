package io.github.aquerr;

import java.io.BufferedReader;
import java.io.IOException;

public class ChatReceiver implements Runnable
{
    private final BufferedReader inputStream;

    public ChatReceiver(BufferedReader inputStream)
    {
        this.inputStream = inputStream;
    }

    public BufferedReader getInputStream()
    {
        return this.inputStream;
    }

    public void startListening()
    {
        Thread listeningThread = new Thread(this);
        listeningThread.start();
    }

    @Override
    public void run()
    {
        try
        {
            String receivedMessage;
            while((receivedMessage = this.inputStream.readLine()) != null)
            {
                System.out.println(receivedMessage);
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
