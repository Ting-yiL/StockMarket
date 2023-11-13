package nl.rug.aoop.networking.handler;

import nl.rug.aoop.networking.messagequeue.Communicator;

public interface ReferenceMessageHandler {
    void handleMessage(String message, Communicator reference);
}
