package nl.rug.aoop.networking.handler;

import nl.rug.aoop.networking.messagequeue.Medium;

public interface ReferenceMessageHandler {
    void handleMessage(String message, Medium reference);
}
