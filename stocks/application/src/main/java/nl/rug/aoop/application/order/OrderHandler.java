package nl.rug.aoop.application.order;

import nl.rug.aoop.messagequeue.message.Message;

public interface OrderHandler {
    void handleOrder(Message message);
}
