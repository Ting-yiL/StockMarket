package nl.rug.aoop.networking.messagequeue;

import nl.rug.aoop.messagequeue.message.Message;

public interface Medium {
    void receiveMessage(Message message);
    Message retrieveMessage();
}
