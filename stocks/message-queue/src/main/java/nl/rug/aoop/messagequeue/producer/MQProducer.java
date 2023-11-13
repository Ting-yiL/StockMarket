package nl.rug.aoop.messagequeue.producer;

import nl.rug.aoop.messagequeue.message.Message;

/**
 * MQProducer - An interface of producer.
 * @author Ting-Yi Lin
 * @version 1.0
 */
public interface MQProducer {

    /**
     * Adding the message to the message queue.
     * @param message The message that will be added to the queue, of type <b><i>Message</i></b>.
     */
    void put(Message message);
}
