package nl.rug.aoop.messagequeue.queue;

import nl.rug.aoop.messagequeue.message.Message;

/**
 * MessageQueue - An interface of message queue.
 * @author Ting-Yi Lin, Mohammad AI Shakoush
 * @version 1.0
 */
public interface MessageQueue {
    /**
     * Adding the new message to the message queue.
     *
     * @param message The message that will be added to the queue, of type <b><i>Message</i></b>.
     */
    void enqueue(Message message) throws NullPointerException;

    /**
     * Returning the head message of the message queue.
     * @return The head message, of type <b><i>Message</i></b>.
     */
    Message getHead();

    /**
     * Removing the head message from the message queue.
     * @return The head message, of the <b><i>Message</i></b>.
     */
    Message dequeue();

    /**
     * Returning the size of the queue.
     * @return the size of the queue, of type <b><i>Int</i></b>.
     */
    int getSize();
}
