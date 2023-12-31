package nl.rug.aoop.messagequeue.message;

import com.google.gson.*;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;


/**
 * NetworkMessage - Similar to Message but instead of being added to the queue, it serves the
 * purpose of being transmitted between client/server.
 * @author Dylan Bonatz, Ting-Yi Lin
 * @version 1.0
 */
@Getter
@Slf4j
public class NetworkMessage implements Comparable<NetworkMessage> {
    private String command;
    private final String header;
    private final String body;
    private final LocalDateTime timestamp;
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(NetworkMessage.class, new NetworkMessageAdapter().nullSafe())
            .create();

    /**
     * The constructor of NetworkMessage.
     * @param command The command of the message.
     * @param messageHeader The header of the message.
     * @param messageBody The body of the message.
     */
    public NetworkMessage(String command, String messageHeader, String messageBody) {
        this(command, messageHeader, messageBody, null);
    }

    /**
     * This constructor of NetworkMessage.
     * @param command The command of the message.
     * @param message The message.
     */
    public NetworkMessage(String command, Message message) {
        this(command, message.getHeader(), message.getBody(), message.getTimestamp());
    }

    /**
     * The constructor of NetworkMessage that time is specified.
     * @param command The command of the message.
     * @param messageHeader The header of the message.
     * @param messageBody The body of the message.
     * @param messageTime The timestamp of the message.
     */
    public NetworkMessage(String command, String messageHeader, String messageBody, LocalDateTime messageTime) {
        if (messageTime == null) {
            messageTime = LocalDateTime.now();
        }
        this.command = command;
        this.header = messageHeader;
        this.body = messageBody;
        this.timestamp = messageTime;
    }

    /**
     * Convert NetworkMessage to JSON String.
     * @return JSON String version of the NetworkMessage.
     */
    public String toJson() {
        return GSON.toJson(this);
    }

    /**
     * Convert JSON String to NetworkMessage.
     * @param json The JSON String.
     * @return The Network Message version of the String.
     */
    public static NetworkMessage fromJson(String json) throws IllegalStateException{
        try{
            log.info("Decrypting Json");
            NetworkMessage networkMessage = GSON.fromJson(json, NetworkMessage.class);
            log.info("Json Decrypted");
            return networkMessage;
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Input is not NetworkMessage", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(NetworkMessage other) {
        return this.getTimestamp().compareTo(other.getTimestamp());
    }
}
