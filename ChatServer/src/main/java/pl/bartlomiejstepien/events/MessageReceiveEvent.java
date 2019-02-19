package pl.bartlomiejstepien.events;

import pl.bartlomiejstepien.entities.Message;
import pl.bartlomiejstepien.entities.UserConnection;

public class MessageReceiveEvent implements Event
{
    private final UserConnection userConnection;
    private final Message message;

    public MessageReceiveEvent(UserConnection userConnection, Message message)
    {
        this.userConnection = userConnection;
        this.message = message;
    }

    public Message getMessage()
    {
        return this.message;
    }

    public UserConnection getUserConnection()
    {
        return this.userConnection;
    }
}
