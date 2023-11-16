package nl.rug.aoop.messagequeue.message;

import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestNetworkMessage {
    private Message message1;
    private NetworkMessage networkMessage1;
    private String messageHeader;
    private String messageBody;
    private LocalDateTime messageTime;

    @BeforeEach
    void setUp() {
        messageHeader = "header";
        messageBody = "body";
        messageTime = LocalDateTime.of(2023, 1, 1, 0, 0);
        message1 = new Message(messageHeader, messageBody, messageTime);
    }

    @Test
    void testToJSON() {
        System.out.println(message1.toJson());
        assertNotNull(message1.toJson());
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

    @Test
    void testFromJSONFromNonNetworkType() {
        String random = "ABC";
        System.out.println(random);
        Message output1 = Message.fromJson(random);
        System.out.println(output1);
    }
}
