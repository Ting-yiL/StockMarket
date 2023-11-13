package nl.rug.aoop.messagequeue.consumer;

import nl.rug.aoop.messagequeue.consumer.Consumer;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.queue.MessageQueue;
import nl.rug.aoop.messagequeue.queue.OrderedMessageQueue;
import nl.rug.aoop.messagequeue.queue.UnorderedMessageQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TestConsumer {
    private Consumer consumer1;
    private MessageQueue queue;
    private Message message1;
    private Message message2;
    private Message message3;
    private Message message4;
    private Message message5;
    private Message message6;

    @BeforeEach
    void setUp() {
        message1 = new Message("header1", "body1");
        message2 = new Message("header2", "body2");
        message3 = new Message("header3", "body3");

        LocalDateTime message1Time = LocalDateTime.of(2023, 1, 1, 1, 0);
        LocalDateTime message2Time = LocalDateTime.of(2023, 1, 1, 3, 0);
        LocalDateTime message3Time = LocalDateTime.of(2023, 1, 1, 0, 0);

        message4 = new Message("header1", "body1", message1Time);
        message5 = new Message("header2", "body2", message2Time);
        message6 = new Message("header3", "body3", message3Time);
    }

    @Test
    void testConsumerConstructor() {
        consumer1 = new Consumer(queue);
        assertNotNull(consumer1);
    }

    @Test
    void testPollWithNullQueue() {
        consumer1 = new Consumer(queue);
        assertNull(consumer1.poll());
    }

    @Test
    void testPollWithUnorderedQueue() {
        queue = new UnorderedMessageQueue();
        consumer1 = new Consumer(queue);

        queue.enqueue(message1);
        queue.enqueue(message2);
        queue.enqueue(message3);

        assertEquals(message1, consumer1.poll());
        assertEquals(message2, consumer1.poll());
        assertEquals(message3, consumer1.poll());
    }

    @Test
    void testPollWithOrderedQueue() {
        queue = new OrderedMessageQueue();
        consumer1 = new Consumer(queue);

        queue.enqueue(message4);
        queue.enqueue(message5);
        queue.enqueue(message6);

        assertEquals(message6, consumer1.poll());
        assertEquals(message4, consumer1.poll());
        assertEquals(message5, consumer1.poll());
    }
}
