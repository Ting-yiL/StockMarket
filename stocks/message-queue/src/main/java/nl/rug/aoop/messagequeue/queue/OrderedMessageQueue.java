package nl.rug.aoop.messagequeue.queue;

import nl.rug.aoop.messagequeue.message.Message;

import java.util.*;
import java.time.LocalDateTime;

/**
 * OrderedQueue - An implementation of MessageQueue where messages are ordered based on <i>their timestamp</i>.
 * @author Ting-Yi Lin, Mohammad AI Shakoush
 * @version 1.0
 */
public class OrderedMessageQueue implements MessageQueue {
    private int currentSize = 0;
    private final SortedMap<LocalDateTime, UnorderedMessageQueue> sortedMap;

    /**
     * A constructor of OrderedQueue. The queue uses the SortedMap data structure.
     */
    public OrderedMessageQueue() {
        this.sortedMap = new TreeMap<>();
    }

    /**
     * Adding the new message to the message queue, and ordered by timestamp.
     *
     * @param message The message that will be added to the queue, of type <b><i>Message</i></b>.
     */
    @Override
    public void enqueue(Message message) throws NullPointerException {
        if (message == null) {
            throw new NullPointerException("Null message is unaccepted");
        }
        LocalDateTime messageTimestamp = message.getTimestamp();
        this.sortedMap.putIfAbsent(messageTimestamp, new UnorderedMessageQueue());
        UnorderedMessageQueue currentQueue = this.sortedMap.get(messageTimestamp);
        currentQueue.enqueue(message);
        this.currentSize++;
    }

    /**
     * Returning the head message of the message queue.
     * If the queue is empty, this method returns null.
     * @return The head message, of the <b><i>Message</i></b>.
     */
    @Override
    public Message getHead() {
        Message myMessage = null;
        if (this.currentSize > 0) {
            UnorderedMessageQueue currentQueue = this.sortedMap.get(sortedMap.firstKey());
            myMessage = currentQueue.getHead();
        }
        return myMessage;
    }

    /**
     * Removing the head message from the message queue.
     * If the queue is empty, this method returns null.
     * @return The head message, of the <b><i>Message</i></b>.
     */
    @Override
    public Message dequeue() {
        Message myMessage = null;
        if (this.currentSize > 0) {
            LocalDateTime headDateTime = this.sortedMap.firstKey();
            UnorderedMessageQueue currentQueue = this.sortedMap.get(headDateTime);
            myMessage = currentQueue.dequeue();
            if (currentQueue.getSize() == 0) {
                this.sortedMap.remove(headDateTime);
            }
            this.currentSize--;
        }
        return myMessage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSize() {
        return this.currentSize;
    }
}
