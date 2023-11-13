package nl.rug.aoop.messagequeue.consumer;

import nl.rug.aoop.messagequeue.message.Message;

/**
 * MQConsumer - An interface of consumer.
 * @author Ting-Yi Lin
 * @version 1.0
 */
public interface MQConsumer {

    /**
     * Returning the head message from the queue.
     * @return The head message of the queue, of type <b><i>Message</i></b>.
     */
    Message poll();
}
