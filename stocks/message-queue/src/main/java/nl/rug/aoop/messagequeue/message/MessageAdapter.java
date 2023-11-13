package nl.rug.aoop.messagequeue.message;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * MessageAdapter - An adapter used to read and write json that build on top of gson.
 * @author Dylan Bonatz, Ting-Yi Lin
 * @version 1.0
 */
class MessageAdapter extends TypeAdapter<Message> {
    public static final String HEADER_FIELD = "Header";
    public static final String BODY_FIELD = "Body";
    public static final String TIME_FIELD = "Time";

    /**
     * Writing JSON string.
     * @param writer A JSON Writer.
     * @param message The message that you want to write.
     * @throws IOException In case of IOException error.
     */
    @Override
    public void write(JsonWriter writer, Message message) throws IOException {
        writer.beginObject();
        writer.name(HEADER_FIELD);
        writer.value(message.getHeader());
        writer.name(BODY_FIELD);
        writer.value(message.getBody());
        writer.name(TIME_FIELD);
        writer.value(message.getTimestamp().toString());
        writer.endObject();
    }

    /**
     * Reading a JSON String.
     * @param reader A JSON Reader.
     * @return The message that is transformed from JSON.
     * @throws IOException In case of IOException error.
     */
    @Override
    public Message read(JsonReader reader) throws IOException {
        String header = null;
        String body = null;
        LocalDateTime timestamp = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String fieldName = reader.nextName();
            switch (fieldName) {
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

        return new Message(header, body, timestamp);
    }
}
