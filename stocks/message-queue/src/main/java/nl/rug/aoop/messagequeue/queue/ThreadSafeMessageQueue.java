package nl.rug.aoop.messagequeue.queue;

import nl.rug.aoop.messagequeue.message.Message;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * ThreadSafeMessageQueue - An queue that prevents issues of multiple users trying to access it
 * by implementing PriorityBlockingQueue.
 * @author Dylan Bonatz, Ting-Yi Lin
 * @version 1.0
 */
public class ThreadSafeMessageQueue implements MessageQueue {
    private final PriorityBlockingQueue<Message> queue;

    /**
     * The constructor of the ThreadSafeMessageQueue.
     */
    public ThreadSafeMessageQueue() {
        this.queue = new PriorityBlockingQueue<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enqueue(Message message) throws NullPointerException {
        if (message == null) {
            throw new NullPointerException("Message cannot be null");
        }
        queue.offer(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Message getHead() {
        if (this.getSize() > 0) {
            return queue.peek();
        } else {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Message dequeue() {
        if (this.getSize() > 0) {
            return queue.poll();
        } else {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSize() {
        return queue.size();
    }
}
