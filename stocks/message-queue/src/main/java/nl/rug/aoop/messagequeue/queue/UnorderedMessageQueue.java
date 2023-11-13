package nl.rug.aoop.messagequeue.queue;

import nl.rug.aoop.messagequeue.message.Message;

import java.util.*;

/**
 * UnorderedQueue - An implementation of MessageQueue where messages are ordered based on <i>when they arrived</i>.
 * @author Ting-Yi Lin, Mohammad AI Shakoush
 * @version 1.0
 */
public class UnorderedMessageQueue implements MessageQueue {
    private int currentSize = 0;
    private final Queue<Message> myQueue;

    /**
     * A constructor of UnorderedQueue. The queue uses the Queue data structure.
     */
    public UnorderedMessageQueue() {
        this.myQueue = new LinkedList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enqueue(Message message) throws NullPointerException{
        if (message == null) {
            throw new NullPointerException("Null message is unaccepted");
        }
        this.myQueue.add(message);
        this.currentSize++;
    }

    /**
     * Returning the head message of the message queue.
     * If the queue is empty, this method returns null.
     * @return The head message, of the <b><i>Message</i></b>.
     */
    @Override
    public Message getHead() {
        Message headQueue = null;
        if (this.currentSize > 0) {
            headQueue = this.myQueue.element();
        }
        return headQueue;
    }

    /**
     * Removing the head message from the message queue.
     * If the queue is empty, this method returns null.
     * @return The head message, of the <b><i>Message</i></b>.
     */
    @Override
    public Message dequeue() {
        Message headQueue = null;
        if (this.currentSize > 0) {
            headQueue = this.myQueue.remove();
            this.currentSize--;
        }
        return headQueue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSize() {
        return this.currentSize;
    }
}
