package nl.rug.aoop.messagequeue.queue;

import nl.rug.aoop.messagequeue.message.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestUnorderedMessageQueue {
    private MessageQueue queue;
    private Message message1;
    private Message message2;
    private Message message3;

    @BeforeEach
    void setUp() {
        queue = new UnorderedMessageQueue();
        message1 = new Message("header1", "body1");
        message2 = new Message("header2", "body2");
        message3 = new Message("header3", "body3");
    }

    @Test
    void testQueueConstructor() {
        assertNotNull(queue);
        assertEquals(0, queue.getSize());
    }

    @Test
    void testQueueSingleEnqueue() {
        queue.enqueue(message3);

        assertEquals(message3, queue.dequeue());
    }

    @Test
    void testQueueMultipleEnqueue() {
        queue.enqueue(message3);
        queue.enqueue(message1);
        queue.enqueue(message2);

        assertEquals(message3, queue.dequeue());
        assertEquals(message1, queue.dequeue());
        assertEquals(message2, queue.dequeue());
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
    void testDequeueFromEmptyQueue() {
        assertNull(queue.dequeue());
    }

    @Test
    void testSingleDequeue() {
        queue.enqueue(message1);

        assertEquals(message1, queue.dequeue());
        assertEquals(0, queue.getSize());
    }

    @Test
    void testMultipleDequeue() {
        queue.enqueue(message1);
        queue.enqueue(message2);

        assertEquals(message1, queue.dequeue());
        assertEquals(message2, queue.dequeue());
        assertEquals(0, queue.getSize());
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
