package nl.rug.aoop.messagequeue.producer;

import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.queue.MessageQueue;

/**
 * Producer - An implementation of <i>MQProducer interface</i>,
 * it is an interactive class that can put message to the Message Queue.
 * @author Ting-Yi Lin
 * @version 1.0
 */
public class Producer implements MQProducer {
    private final MessageQueue queue;

    /**
     * A constructor of Producer.
     * @param queue A queue that the producer has access to
     *              , of type <b><i>OrderedQueue</i></b> or <b><i>Unordered Queue</i></b>.
     */
    public Producer(MessageQueue queue) {
        this.queue = queue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void put(Message message) {
        this.queue.enqueue(message);
    }
}
