package nl.rug.aoop.networking.messagequeue;

import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;
import nl.rug.aoop.networking.handler.ServerMessageHandler;
import nl.rug.aoop.networking.messagequeue.handler.MQServerMessageHandler;
import nl.rug.aoop.networking.server.Server;
import org.junit.jupiter.api.Test;

public class TestMQServer {
    private Server server;
    private ThreadSafeMessageQueue queue;
    private MQCommunicator mqCommunicator;
    private MQServerMessageHandler messageHandler;

    @Test
    void SetUp() {
        this.queue = new ThreadSafeMessageQueue();
        this.mqCommunicator = new MQCommunicator();
        this.messageHandler = new MQServerMessageHandler(this.queue, this.mqCommunicator);
    }
}
