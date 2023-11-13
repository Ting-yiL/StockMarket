package nl.rug.aoop.networking.messagequeue;

import nl.rug.aoop.messagequeue.message.Message;

public interface Communicator {
    void receiveMessage(Message message);
}
