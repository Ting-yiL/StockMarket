package nl.rug.aoop.networking;

import nl.rug.aoop.networking.client.Client;
import nl.rug.aoop.networking.handler.MQServerMessageHandler;
import nl.rug.aoop.networking.handler.MessageHandler;
import nl.rug.aoop.networking.server.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.verify;

public class TestServerClientIntegration {
    public static final int TIMEOUT = 5000;
    private Server server;
    private Client client;
    private MQServerMessageHandler serverMessageHandlerMock;

    @BeforeEach
    public void setUp() throws IOException {
        this.serverMessageHandlerMock = Mockito.mock(MQServerMessageHandler.class);

        server = new Server(0, serverMessageHandlerMock);
        new Thread(server).start();
        await().atMost(Duration.ofMillis(TIMEOUT)).until(server::isRunning);

        client = new Client(new InetSocketAddress("localhost", server.getPort()), Mockito.mock(MessageHandler.class));
        new Thread(client).start();
        await().atMost(Duration.ofMillis(TIMEOUT)).until(client::isRunning);
    }

    @Test
    public void tearDown() {
        client.terminate();
        await().atMost(Duration.ofMillis(TIMEOUT)).until(() -> !client.isRunning());
        server.terminate();
        await().atMost(Duration.ofMillis(TIMEOUT)).until(() -> !server.isRunning());
    }

    @Test
    public void testSendMessageFromClientToServer() {
        String messageToSend = "Hello, Server!";
        client.sendMessage(messageToSend);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        verify(serverMessageHandlerMock).handleMessage(messageToSend, 0);
    }
}
