package nl.rug.aoop.networking;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.networking.client.Client;
import nl.rug.aoop.networking.handler.MessageHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class TestClient {
    private BufferedReader serverIn;
    private PrintWriter serverOut;
    private int serverPort;
    private boolean serverStarted;
    private InetSocketAddress address;
    private MessageHandler mockHandler;
    private int timeout = 10000;

    @BeforeEach
    public void setup() {
        startTempServer();
        this.address = new InetSocketAddress("localhost", serverPort);
        this.mockHandler = Mockito.mock(MessageHandler.class);
    }

    private void startTempServer() {
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(0);
                this.serverPort = serverSocket.getLocalPort();
                this.serverStarted = true;
                Socket socket = serverSocket.accept();
                this.serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.serverOut = new PrintWriter(socket.getOutputStream(), true);
                log.info("Server Started");
            } catch (IOException e) {
                log.error("Could not start server", e);
            }
        }).start();

        await().atMost(Duration.ofSeconds(1)).until(() -> this.serverStarted);
    }

    @Test
    public void testConstructorWithRunningServer() throws IOException {
        Client client = new Client(address, mockHandler);
        assertTrue(client.isConnected());
    }

    @Test
    public void testSendMessage() {
        Client client = null;
        try {
            client = new Client(address, mockHandler);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        client.sendMessage("Test");

        AtomicBoolean messageReceived = new AtomicBoolean(false);

        await().atMost(Duration.ofSeconds(1)).until(() -> {
            try {
                if ("Test".equals(serverIn.readLine())) {
                    messageReceived.set(true);
                    return true;
                }
                return false;
            } catch (IOException e) {
                log.error("Error reading from server", e);
                return false;
            }
        });

        assertTrue(messageReceived.get());
    }

    @Test
    public void testSendMessageToServer() throws IOException {
        Client client = new Client(address, mockHandler);
        String sentMessage = "TestMessage";
        client.sendMessage(sentMessage);

        AtomicBoolean isMessageReceived = new AtomicBoolean(false);

        await().atMost(Duration.ofSeconds(1)).until(() -> {
            try {
                if (sentMessage.equals(serverIn.readLine())) {
                    isMessageReceived.set(true);
                    return true;
                }
            } catch (IOException e) {
                log.error("Error reading from server", e);
            }
            return false;
        });

        assertTrue(isMessageReceived.get());
    }


    @Test
    public void testSendNullMessage() throws IllegalArgumentException, IOException {
        Client client = new Client(address, mockHandler);
        assertThrows(IllegalArgumentException.class, () -> client.sendMessage(null));
    }

    @Test
    public void testConstructorWithoutRunningServer() {
        InetSocketAddress invalidAddress = new InetSocketAddress("localhost", 12345); // Use a port that's likely not in use
        assertThrows(IOException.class, () -> new Client(invalidAddress, mockHandler));
    }

    @Test
    public void testConstructorInvalidPort() {
        assertThrows(IllegalArgumentException.class, () -> {
            InetSocketAddress invalidAddress = new InetSocketAddress("localhost", -1);
            new Client(invalidAddress, mockHandler);
        });
    }

    @Test
    public void testConstructorInvalidHost() {
        InetSocketAddress invalidAddress = new InetSocketAddress("invalid_host_name", serverPort);
        assertThrows(IOException.class, () -> new Client(invalidAddress, mockHandler));
    }

    @Test
    public void testTerminateClosesConnection() throws IOException {
        Client client = new Client(address, mockHandler);
        assertTrue(client.isConnected());
        client.terminate();
        assertFalse(client.isConnected());
        assertTrue(client.getSocket().isClosed());
    }

    @Test
    public void testMultipleMessages() throws IOException {
        Client client = new Client(address, mockHandler);
        new Thread(client).start();
        await().atMost(Duration.ofSeconds(1)).until(client::isRunning);
        assertTrue(client.isRunning());
        assertTrue(client.isConnected());

        String[] messages = {"hello", "world", "test"};
        for (String message : messages) {
            this.serverOut.println(message);
        }
        await().atMost(Duration.ofSeconds(2)).until(() -> true);
        for (String message : messages) {
            Mockito.verify(mockHandler).handleMessage(message);
        }
    }
}
