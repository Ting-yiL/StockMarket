package nl.rug.aoop.networking;

import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;
import nl.rug.aoop.networking.client.Client;
import nl.rug.aoop.networking.handler.MQServerMessageHandler;
import nl.rug.aoop.networking.handler.MessageHandler;
import nl.rug.aoop.networking.handler.MessageLogger;
import nl.rug.aoop.networking.server.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.verify;

public class TestServerClientIntegration {
    public static final int TIMEOUT = 5000;
    private Server server;
    private Client client1;
    private Client client2;
    private MQServerMessageHandler serverMessageHandler;
    private ThreadSafeMessageQueue queueMock = Mockito.mock(ThreadSafeMessageQueue.class);
    private Message message = new Message("Test head", "Test Body");
    private NetworkMessage messageToSend = new NetworkMessage("Test", message);

    @BeforeEach
    public void setUp() throws IOException {
        this.serverMessageHandler = new MQServerMessageHandler(queueMock);

        server = new Server(0, serverMessageHandler);
        new Thread(server).start();
        await().atMost(Duration.ofMillis(TIMEOUT)).until(server::isRunning);

        client1 = new Client(new InetSocketAddress("localhost", server.getPort()), new MessageLogger());
        new Thread(client1).start();
        await().atMost(Duration.ofMillis(TIMEOUT)).until(client1::isRunning);

        client2 = new Client(new InetSocketAddress("localhost", server.getPort()), Mockito.mock(MessageHandler.class));
        new Thread(client2).start();
        await().atMost(Duration.ofMillis(TIMEOUT)).until(client2::isRunning);
    }

    @Test
    public void tearDown() {
        client1.terminate();
        await().atMost(Duration.ofMillis(TIMEOUT)).until(() -> !client1.isRunning());
        client2.terminate();
        await().atMost(Duration.ofMillis(TIMEOUT)).until(() -> !client2.isRunning());
        server.terminate();
        await().atMost(Duration.ofMillis(TIMEOUT)).until(() -> !server.isRunning());
    }

    @Test
    public void testSendMessageFromClientToServer() {
        String messageToSend = "Hello, Server!";
        client1.sendMessage(this.messageToSend.toJson());

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSendMessageFromServerToClient() {
        String messageToSend = "Hello, Client!";
        server.getMessageHandler().getClientHandlers().get(0).sendMessage(messageToSend);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSendMultipleMessageFromSameClientToServer() {
        String messageToSend = "Hello, Server!";
        client1.sendMessage(this.messageToSend.toJson());

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        client1.sendMessage(this.messageToSend.toJson());

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSendMultipleMessageFromDifferentClientToServer() {
        String messageToSend = "Hello, Server!";
        client1.sendMessage(this.messageToSend.toJson());

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        client2.sendMessage(this.messageToSend.toJson());

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        client1.sendMessage(this.messageToSend.toJson());

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        client2.sendMessage(this.messageToSend.toJson());

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
