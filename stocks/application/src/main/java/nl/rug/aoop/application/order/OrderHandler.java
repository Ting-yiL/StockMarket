package nl.rug.aoop.application.order;

import nl.rug.aoop.messagequeue.message.Message;

/**
 * The Order Handler.
 */
public interface OrderHandler {
    /**
     * Handle a message order.
     * @param message The message.
     */
    void handleOrder(Message message);
}
