package nl.rug.aoop.messagequeue.message;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * NetworkMessageAdapter - An adapter for NetworkMessage used to read and write json that build on top of gson.
 * @author Dylan Bonatz, Ting-Yi Lin
 * @version 1.0
 */
public class NetworkMessageAdapter extends TypeAdapter<NetworkMessage> {
    /**
     * The Command of the Network Message.
     */
    public static final String COMMAND = "Command";
    /**
     * The Header of the Network Message.
     */
    public static final String HEADER_FIELD = "Header";
    /**
     * The Body of the Network Message.
     */
    public static final String BODY_FIELD = "Body";
    /**
     * The Timestamp of the Network Message.
     */
    public static final String TIME_FIELD = "Time";

    /**
     * Write a Json String.
     * @param writer A Json Writer.
     * @param message The NetworkMessage that will be JSON-ized.
     * @throws IOException In case of IOException error.
     */
    @Override
    public void write(JsonWriter writer, NetworkMessage message) throws IOException {
        writer.beginObject();
        writer.name(COMMAND);
        writer.value(message.getCommand());
        writer.name(HEADER_FIELD);
        writer.value(message.getHeader());
        writer.name(BODY_FIELD);
        writer.value(message.getBody());
        writer.name(TIME_FIELD);
        writer.value(message.getTimestamp().toString());
        writer.endObject();
    }

    /**
     * Read a Json String.
     * @param reader Json Reader.
     * @return NetworkMessage of the String
     * @throws IOException In case of IOException error.
     */
    @Override
    public NetworkMessage read(JsonReader reader) throws IOException {
        String command = null;
        String header = null;
        String body = null;
        LocalDateTime timestamp = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String fieldName = reader.nextName();
            switch (fieldName) {
                case COMMAND -> {
                    command = reader.nextString();
                }
                case HEADER_FIELD -> {
                    header = reader.nextString();
                }
                case BODY_FIELD -> {
                    body = reader.nextString();
                }
                case TIME_FIELD -> {
                    timestamp = LocalDateTime.parse(reader.nextString());
                }
                default -> reader.skipValue();
            }
        }
        reader.endObject();

        return new NetworkMessage(command, header, body, timestamp);
    }
}
