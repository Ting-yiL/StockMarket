package nl.rug.aoop.messagequeue.producer;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.networking.client.Client;
import nl.rug.aoop.networking.handler.MessageHandler;
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

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class TestNetworkProducer {
    /*
    private BufferedReader serverIn;
    private PrintWriter serverOut;
    private int serverPort;
    private boolean serverStarted;

    private void startTempServer() {
        new Thread( () -> {
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
    public void testConstructorWithRunningServer() throws IOException{
        startTempServer();
        InetSocketAddress address = new InetSocketAddress("localhost", serverPort);
        MessageHandler mockHandler = Mockito.mock(MessageHandler.class);

        NetworkProducer networkProducer = new NetworkProducer(address, mockHandler);
        assertTrue(networkProducer.isConnected());
        log.info("here");
    }

    @Test
    public void testWithPutMessage() throws IOException {
        startTempServer();
        InetSocketAddress address = new InetSocketAddress("localhost", serverPort);
        MessageHandler mockHandler = Mockito.mock(MessageHandler.class);

        NetworkProducer networkProducer = new NetworkProducer(address, mockHandler);
        new Thread(networkProducer).start();
        await().atMost(Duration.ofSeconds(1)).until(() -> networkProducer.isRunning());
        assertTrue(networkProducer.isRunning());
        assertTrue(networkProducer.isConnected());

        Message message = new Message("Test", "Test");
        networkProducer.put(message);

        //Mockito.verify(mockHandler).handleMessage(message);
    }

     */

}
