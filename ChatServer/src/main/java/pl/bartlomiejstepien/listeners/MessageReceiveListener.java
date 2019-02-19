package pl.bartlomiejstepien.listeners;

import pl.bartlomiejstepien.events.MessageReceiveEvent;

public class MessageReceiveListener implements Listener
{
    @EventListener
    public void onMessageReceive(MessageReceiveEvent event)
    {
        System.out.println(event.getMessage());
    }
}
