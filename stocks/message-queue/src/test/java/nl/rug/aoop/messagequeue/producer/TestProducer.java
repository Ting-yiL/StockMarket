package nl.rug.aoop.messagequeue.producer;

import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.producer.Producer;
import nl.rug.aoop.messagequeue.queue.MessageQueue;
import nl.rug.aoop.messagequeue.queue.OrderedMessageQueue;
import nl.rug.aoop.messagequeue.queue.UnorderedMessageQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestProducer {
    private Producer producer;
    private Message message;
    private MessageQueue queue;

    @BeforeEach
    void setUp() {
        String messageHeader = "header";
        String messageBody = "body";
        message = new Message(messageHeader, messageBody);
    }

    @Test
    void testProducerConstructor() {
        producer = new Producer(queue);
        assertNotNull(producer);
    }

    @Test
    void testPutWithNullQueue() {
        producer = new Producer(queue);
        assertThrows(NullPointerException.class, () -> {
            producer.put(message);
        });
    }

    @Test
    void testPutWithUnorderedQueue() {
        queue = new UnorderedMessageQueue();

        producer = new Producer(queue);
        producer.put(message);

        assertEquals(message, queue.getHead());
    }

    @Test
    void testPutWithOrderedQueue() {
        queue = new OrderedMessageQueue();

        producer = new Producer(queue);
        producer.put(message);

        assertEquals(message, queue.getHead());
    }

}
