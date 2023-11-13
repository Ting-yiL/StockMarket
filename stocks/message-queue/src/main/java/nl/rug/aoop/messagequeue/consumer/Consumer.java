package nl.rug.aoop.messagequeue.consumer;

import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.queue.MessageQueue;

/**
 * Consumer - An implementation of <i>MQConsumer interface</i>,
 * it is an interactive class that can poll message from the Message Queue.
 * @author Ting-Yi Lin
 * @version 1.0
 */
public class Consumer implements MQConsumer {
    private final MessageQueue queue;

    /**
     * A constructor of Consumer.
     * @param queue A queue that the consumer has access to
     *              , of type <b><i>OrderedQueue</i></b> or <b><i>Unordered Queue</i></b>.
     */
    public Consumer(MessageQueue queue) {
        this.queue = queue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Message poll() {
        Message message = null;
        if (this.queue != null) {
            message = this.queue.dequeue();
        }
        return message;
    }
}
