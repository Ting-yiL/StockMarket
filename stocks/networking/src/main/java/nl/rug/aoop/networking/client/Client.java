package nl.rug.aoop.networking.client;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.networking.handler.MessageHandler;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.time.Duration;

import static org.awaitility.Awaitility.await;

/**
 * Client of the Client/Server.
 * @author Dylan Bonatz, Ting-Yi Lin
 * @version 1.0
 */
@Slf4j
@Getter
public class Client implements Runnable {
    @Getter
    private final int TIMEOUT = 10000;
    @Getter
    private boolean running = false;
    @Getter
    private boolean connected;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private final MessageHandler messageHandler;

    /**
     * The constructor of Client.
     * @param address The InetSocketAddress.
     * @param messageHandler The messageHandler.
     * @throws IOException The IOException.
     */
    public Client(InetSocketAddress address, MessageHandler messageHandler) throws IOException {
        this.messageHandler = messageHandler;
        initSocket(address);
        connected = true;
    }

    /**
     * Initialize the socket that will perform connection with the server.
     * @param address The InetSocketAddress that you want to perform connection to the server.
     */
    private void initSocket(InetSocketAddress address) throws IOException {
        log.info("Initializing a client socket");
        socket = new Socket();
        socket.connect(address, TIMEOUT);

        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        if (!socket.isConnected()) {
            throw new IOException("The socket is not connected!");
        }
    }

    /**
     * Sending String to the Server.
     * @param message The message you want to send to the server.
     */
    public void sendMessage(String message) throws IllegalArgumentException {
        log.info("Sending a message from the client");
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Attempting to send an invalid message.");
        }
        if (this.connected) {
            out.println(message);
        }
    }

    /**
     * Running the client socket.
     */
    @Override
    public void run() {
        log.info("Attempt running the client");
        running = true;
        while (running) {
            try {
                String incomingMessage = in.readLine();
                if (incomingMessage == null) {
                    terminate();
                    break;
                }
                log.info("receive message");
                messageHandler.handleMessage(incomingMessage);
            } catch (IOException e) {
                log.error("Could not receive message " + e.getMessage());
                terminate();
            }
        }
    }

    /**
     * The termination of the connection between Client and Server.
     */
    public void terminate() {
        log.info("Attempting to terminate the client");
        running = false;
        try {
            this.sendMessage("Terminating connection");
            await().atLeast(Duration.ofSeconds(1));
            socket.close();
        } catch (IOException e) {
            log.error("Cannot successfully close socket");
        }
        connected = false;
    }
}
