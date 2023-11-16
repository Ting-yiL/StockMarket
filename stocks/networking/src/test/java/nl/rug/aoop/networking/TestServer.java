package nl.rug.aoop.networking;

import nl.rug.aoop.networking.handler.MQServerMessageHandler;
import nl.rug.aoop.networking.handler.MessageHandler;
import nl.rug.aoop.networking.server.Server;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

public class TestServer {
    private Server server;
    private ExecutorService service;
    private final int PORT = 6200;
    private MQServerMessageHandler messageHandler;

    @BeforeEach
    void setUp() throws IOException {
        this.messageHandler = Mockito.mock(MQServerMessageHandler.class);
        this.service = Executors.newCachedThreadPool();
        this.server = new Server(PORT, messageHandler);
        this.service.submit(server);
        await().until(() -> server.isRunning());
    }


    @AfterEach
    void tearDown() {
        server.terminate();
        service.shutdown();
    }

    @Test
    void testServerInitialization() {
        assertTrue(server.isRunning());
        assertEquals(PORT, server.getPort());
    }

    @Test
    void testClientConnectionAcceptance() throws IOException {
        Socket clientSocket = new Socket("localhost", PORT);
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        out.println("Hello Server");
        assertTrue(clientSocket.isConnected());
        clientSocket.close();
    }

    @Test
    void testServerTermination() {
        server.terminate();
        assertFalse(server.isRunning());
    }
}
