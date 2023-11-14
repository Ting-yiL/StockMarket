package nl.rug.aoop.application;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;
import nl.rug.aoop.networking.handler.MQServerMessageHandler;
import nl.rug.aoop.networking.server.Server;

import java.io.IOException;

@Slf4j
public class ServerApplication {
    private static final int TIMEOUT = 5000;
    private final int port = Integer.parseInt(System.getenv("MESSAGE_QUEUE_PORT"));
    private Server server;
    private ThreadSafeMessageQueue queue;
    private MQServerMessageHandler serverMessageHandler;

    public static void main(String[] args) {
        ServerApplication app = new ServerApplication();
        app.run();
    }

    private void setUpServer() throws IOException {
        this.queue = new ThreadSafeMessageQueue();
        this.serverMessageHandler = new MQServerMessageHandler(this.queue);
        this.server = new Server(this.port, this.serverMessageHandler);
    }

    private void run() {
        try {
            this.setUpServer();
        } catch (IOException e) {
            log.error("Cannot run Server Application due to" + e, e);
        }
    }
}
