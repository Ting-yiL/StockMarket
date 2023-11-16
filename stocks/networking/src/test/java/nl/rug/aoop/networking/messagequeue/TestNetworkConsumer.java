package nl.rug.aoop.networking.messagequeue;

import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;
import nl.rug.aoop.networking.handler.MQServerMessageHandler;
import nl.rug.aoop.networking.server.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;

import static org.awaitility.Awaitility.await;

public class TestNetworkConsumer {
    private static final int TIMEOUT = 5000;
    private Server server;
    private ThreadSafeMessageQueue queue;
    private NetworkConsumer consumer;
    private MQServerMessageHandler serverMessageHandler;

    @BeforeEach
    public void setUp() throws IOException {
        this.queue = new ThreadSafeMessageQueue();
        this.serverMessageHandler = new MQServerMessageHandler(this.queue);

        server = new Server(0, this.serverMessageHandler);
        new Thread(server).start();
        await().atMost(Duration.ofMillis(TIMEOUT)).until(server::isRunning);

        this.consumer = new NetworkConsumer(server.getPort());
        this.consumer.start();
    }

    @Test
    public void tearDown() {
        consumer.stop();
        server.terminate();
        await().atMost(Duration.ofMillis(TIMEOUT)).until(() -> !server.isRunning());
    }

    @Test
    public void TestPoll() {
        this.consumer.poll();
    }
}
