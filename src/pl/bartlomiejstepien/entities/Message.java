package pl.bartlomiejstepien.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message
{
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("YY-MM-dd HH:mm");

    private final String rawMessage;
    private final LocalDateTime dateTime;
    private final String formattedMessage;
    private final String userNickname;

    public Message(String userNickname, String rawMessage, LocalDateTime dateTime)
    {
        this.rawMessage = rawMessage;
        this.dateTime = dateTime;
        this.userNickname = userNickname;

        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("[");
        messageBuilder.append(DATE_TIME_FORMATTER.format(dateTime));
        messageBuilder.append("]");
        messageBuilder.append("[");
        messageBuilder.append(userNickname);
        messageBuilder.append("]: ");
        messageBuilder.append(rawMessage);

        this.formattedMessage = messageBuilder.toString();
    }

    public String getFormattedMessage()
    {
        return this.formattedMessage;
    }

    public String getUserNickname()
    {
        return this.userNickname;
    }

    public String getRawMessage()
    {
        return this.rawMessage;
    }

    @Override
    public String toString()
    {
        return this.formattedMessage;
    }
}
