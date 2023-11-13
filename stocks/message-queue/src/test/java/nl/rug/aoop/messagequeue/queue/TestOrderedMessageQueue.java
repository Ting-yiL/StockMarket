package nl.rug.aoop.messagequeue.queue;

import nl.rug.aoop.messagequeue.message.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TestOrderedMessageQueue {
    private MessageQueue queue = null;
    private Message message1;
    private Message message2;
    private Message message3;
    private Message message4;
    private Message message5;
    private Message message6;

    @BeforeEach
    void setUp() {
        queue = new OrderedMessageQueue();

        LocalDateTime message1Time = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime message2Time = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime message3Time = LocalDateTime.of(2023, 1, 1, 0, 0);

        message1 = new Message("header1", "body1", message1Time);
        message2 = new Message("header2", "body2", message2Time);
        message3 = new Message("header3", "body3", message3Time);

        LocalDateTime message4Time = LocalDateTime.of(2023, 1, 1, 1, 0);
        LocalDateTime message5Time = LocalDateTime.of(2023, 1, 1, 2, 0);
        LocalDateTime message6Time = LocalDateTime.of(2023, 1, 1, 3, 0);

        message4 = new Message("header1", "body1", message4Time);
        message5 = new Message("header2", "body2", message5Time);
        message6 = new Message("header3", "body3", message6Time);
    }

    @Test
    void testQueueConstructor() {
        assertNotNull(queue);
        assertEquals(0, queue.getSize());
    }

    @Test
    void testSingleEnqueue() {
        queue.enqueue(message1);

        assertEquals(message1, queue.dequeue());
    }

    @Test
    void testMultipleEnqueue() {
        queue.enqueue(message1);
        queue.enqueue(message2);
        queue.enqueue(message3);

        assertEquals(message1, queue.dequeue());
        assertEquals(message2, queue.dequeue());
        assertEquals(message3, queue.dequeue());
    }

    @Test
    void testEnqueueNullMessage() {
        assertThrows(NullPointerException.class, () -> {queue.enqueue(null);});
    }

    @Test
    void testGetSize() {
        assertEquals(0, queue.getSize());

        queue.enqueue(message3);
        queue.enqueue(message1);
        queue.enqueue(message2);

        assertEquals(3, queue.getSize());

        queue.dequeue();
        assertEquals(2, queue.getSize());

        queue.dequeue();
        assertEquals(1, queue.getSize());

        queue.dequeue();
        assertEquals(0, queue.getSize());
    }

    @Test
    void testGetSizeWhenOverDequeued() {
        queue.enqueue(message1);
        queue.dequeue();
        queue.dequeue();

        assertEquals(0, queue.getSize());
    }

    @Test
    void testGetHeadFromEmptyQueue() {
        assertNull(queue.getHead());
    }

    @Test
    void testGetHead() {
        queue.enqueue(message1);

        assertEquals(message1, queue.getHead());
        assertEquals(1, queue.getSize());
    }

    @Test
    void testDequeueEmptyQueue() {
        assertNull(queue.dequeue());
    }

    @Test
    void testQueueOrderingWithSameLocalDateTime() {
        queue.enqueue(message3);
        queue.enqueue(message1);
        queue.enqueue(message2);

        assertEquals(message3, queue.dequeue());
        assertEquals(message1, queue.dequeue());
        assertEquals(message2, queue.dequeue());
    }

    @Test
    void testQueueSingleDequeue() {
        queue.enqueue(message6);

        assertEquals(message6, queue.dequeue());
    }

    @Test
    void testQueueMultipleDequeueOrderingWithDifferentLocalDateTime() {
        queue.enqueue(message6);
        queue.enqueue(message5);
        queue.enqueue(message4);

        assertEquals(message4, queue.dequeue());
        assertEquals(message5, queue.dequeue());
        assertEquals(message6, queue.dequeue());
    }

    @Test
    void testQueueMultipleDequeueOrderingWithMixedLocalDateTime() {
        queue.enqueue(message6);
        queue.enqueue(message5);
        queue.enqueue(message1);
        queue.enqueue(message2);
        queue.enqueue(message4);
        queue.enqueue(message3);

        assertEquals(message1, queue.dequeue());
        assertEquals(message2, queue.dequeue());
        assertEquals(message3, queue.dequeue());
        assertEquals(message4, queue.dequeue());
        assertEquals(message5, queue.dequeue());
        assertEquals(message6, queue.dequeue());
    }

    @Test
    void testUsingEnqueueDequeueAlternatively() {
        queue.enqueue(message1);
        assertEquals(message1, queue.dequeue());
        queue.enqueue(message2);
        assertEquals(message2, queue.dequeue());
        queue.enqueue(message3);
        assertEquals(message3, queue.dequeue());
    }
}
