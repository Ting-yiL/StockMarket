package nl.rug.aoop.application.util;

import nl.rug.aoop.messagequeue.handler.NetworkProducerMessageHandler;
import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;
import nl.rug.aoop.networking.server.Server;

public class MQServer {
    private Server server;
    private ThreadSafeMessageQueue messageQueue;
    private NetworkProducerMessageHandler messageHandler;

    public MQServer() {
        messageQueue = new ThreadSafeMessageQueue();
        messageHandler = new NetworkProducerMessageHandler(messageQueue);
    }
}
