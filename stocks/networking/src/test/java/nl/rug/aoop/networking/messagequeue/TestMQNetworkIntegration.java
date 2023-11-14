package nl.rug.aoop.networking.messagequeue;

import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;
import nl.rug.aoop.networking.client.Client;
import nl.rug.aoop.networking.handler.MQServerMessageHandler;
import nl.rug.aoop.networking.handler.MessageLogger;
import nl.rug.aoop.networking.handler.NetworkConsumerMessageHandler;
import nl.rug.aoop.networking.server.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMQNetworkIntegration {
    private static final int TIMEOUT = 5000;
    private Server server;
    private ThreadSafeMessageQueue queue;
    private NetworkConsumer consumer;
    private NetworkProducer producer;
    private Client consumerClient;
    private Client producerClient;
    private MQServerMessageHandler serverMessageHandler;
    private NetworkConsumerMessageHandler consumerMessageHandler;
    private MessageLogger producerMessageHandler;
    private Communicator communicator;
    private Message message;

    @BeforeEach
    public void setUp() throws IOException {
        this.message = new Message("Test Head", "Test Body");

        this.queue = new ThreadSafeMessageQueue();
        this.serverMessageHandler = new MQServerMessageHandler(this.queue);

        server = new Server(0, this.serverMessageHandler);
        new Thread(server).start();
        await().atMost(Duration.ofMillis(TIMEOUT)).until(server::isRunning);

        this.communicator = new NetworkCommunicator();
        this.consumerMessageHandler = new NetworkConsumerMessageHandler(this.communicator);
        this.consumerClient = new Client(new InetSocketAddress("localhost", server.getPort()), this.consumerMessageHandler);
        new Thread(consumerClient).start();
        await().atMost(Duration.ofMillis(TIMEOUT)).until(consumerClient::isRunning);
        this.consumer = new NetworkConsumer(this.consumerClient, (NetworkCommunicator) this.communicator);

        this.producerMessageHandler = new MessageLogger();
        this.producerClient = new Client(new InetSocketAddress("localhost", server.getPort()), this.producerMessageHandler);
        new Thread(producerClient).start();
        await().atMost(Duration.ofMillis(TIMEOUT)).until(producerClient::isRunning);
        this.producer = new NetworkProducer(this.producerClient);
    }

    @Test
    public void tearDown() {
        consumerClient.terminate();
        await().atMost(Duration.ofMillis(TIMEOUT)).until(() -> !consumerClient.isRunning());
        producerClient.terminate();
        await().atMost(Duration.ofMillis(TIMEOUT)).until(() -> !producerClient.isRunning());
        server.terminate();
        await().atMost(Duration.ofMillis(TIMEOUT)).until(() -> !server.isRunning());
    }

    @Test
    public void TestPutMessage() {
        this.producer.put(this.message);
    }

    @Test
    public void TestMixedMessage() {
        this.producer.put(this.message);
        this.consumer.poll();
    }
}
