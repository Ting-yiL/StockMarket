package nl.rug.aoop.messagequeue.message;

import java.time.LocalDateTime;

import com.google.gson.*;
import lombok.Getter;


/**
 * Message - A class that contains the message that Consumer and Producer communicate with each others
 * pass queue, Message is an immutable class.
 * @author Ting-Yi Lin, Mohammad AI Shakoush
 * @version 1.0
 */
@Getter
public class Message implements Comparable<Message> {
    private final String header;
    private final String body;
    private final LocalDateTime timestamp;

    /**
     * A message constructor without specifying timestamp,
     * when called the timestamp is set by default to <i>LocalDateTime.now()</i>.
     * @param messageHeader The header of the message, of type <b><i>String</i></b>.
     * @param messageBody The body of the message, of type <b><i>String</i></b>.
     */
    public Message(String messageHeader, String messageBody) {
        this(messageHeader, messageBody, null);
    }

    /**
     * A message constructor specifying timestamp that the message is made.
     * @param messageHeader The header of the message, of type <b><i>String</i></b>.
     * @param messageBody The body of the message, of type <b><i>String</i></b>.
     * @param messageTime The timestamp of the message, of type <b><i>LocalDateTime</i></b>.
     */
    public Message(String messageHeader, String messageBody, LocalDateTime messageTime) {
        if (messageTime == null) {
            messageTime = LocalDateTime.now();
        }
        this.header = messageHeader;
        this.body = messageBody;
        this.timestamp = messageTime;
    }

    /**
     * Convert Message to JSON String.
     * @return JSON String version of the message.
     */
    public String toJson() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Message.class, new MessageAdapter().nullSafe())
                .create();
        return gson.toJson(this);
    }

    /**
     * Convert JSON String to Message.
     * @param json The JSON String.
     * @return The Message version of the String.
     */
    public static Message fromJson(String json) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Message.class, new MessageAdapter().nullSafe())
                .create();
        return gson.fromJson(json, Message.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Message other) {
        return this.getTimestamp().compareTo(other.getTimestamp());
    }
}
