package nl.rug.aoop.messagequeue.message;

import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class TestMessage {
    private Message message1;
    private Message message2;
    private String messageHeader;
    private String messageBody;
    private LocalDateTime messageTime;

    @BeforeEach
    void setUp() {
        messageHeader = "header";
        messageBody = "body";
        messageTime = LocalDateTime.of(2023, 1, 1, 0, 0);
        message1 = new Message(messageHeader, messageBody);
        message2 = new Message(messageHeader, messageBody, messageTime);
    }

    @Test
    void testMessageConstructor() {
        assertEquals(messageHeader, message1.getHeader());
        assertEquals(messageBody, message1.getBody());
        assertNotNull(message1.getTimestamp());
    }

    @Test
    void testMessageConstructorWithLocalDateTime() {
        assertEquals(messageHeader, message2.getHeader());
        assertEquals(messageBody, message2.getBody());
        assertEquals(messageTime, message2.getTimestamp());

    }

    @Test
    void testMessageConstructorWithNullLocalDateTime() {
        Message message3 = new Message(messageHeader, messageBody, null);
        assertEquals(messageHeader, message3.getHeader());
        assertEquals(messageBody, message3.getBody());
        assertNotNull(message3.getTimestamp());
    }

    @Test
    void testMessageImmutable() {
        List<Field> fields = List.of(Message.class.getDeclaredFields());
        fields.forEach(field -> {
            assertTrue(Modifier.isFinal(field.getModifiers()), field.getName() + " is not final");
        });
    }

    @Test
    void testToJSON() {
        System.out.println(message1.toJson());
        assertNotNull(message1.toJson());
    }

    @Test
    void testToJSONNullMessage() {
    }

    @Test
    void testFromJSON() {
        String message1Json = message1.toJson();
        System.out.println(message1Json);
        Message output1 = Message.fromJson(message1Json);
        assertEquals(output1.getHeader(), message1.getHeader());
        assertEquals(output1.getBody(), message1.getBody());
        assertEquals(output1.getTimestamp(), message1.getTimestamp());
    }

}
