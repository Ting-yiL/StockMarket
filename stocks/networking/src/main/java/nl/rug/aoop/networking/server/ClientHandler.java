package nl.rug.aoop.networking.server;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.networking.handler.MQServerMessageHandler;
import nl.rug.aoop.networking.handler.MessageHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

/**
 * The ClientHandler of the server, perform operations with the client instead of the server itself.
 * @author Dylan Bonatz, Ting-Yi Lin
 * @version 1.0
 */
@Slf4j
public class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean running;
    private final MessageHandler messageHandler;
    private final int id;
    private Server server;
    @Setter
    @Getter
    private int traderId = -1;

    /**
     * The Constructor of the Client Handler.
     * @param socket The socket of the Client Handler.
     * @param messageHandler The messageHandler you want to use.
     * @param id The id of the client handler.
     */
    public ClientHandler(Socket socket, MessageHandler messageHandler, int id, Server server) {
        this.socket = socket;
        this.id  = id;
        this.messageHandler = messageHandler;
        this.server = server;
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            log.error("Error initializing input and output streams for client handler with ID: " + this.id, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        this.running = true;
        boolean terminatingConnection;
        //sendMessage("Hello client " + this.id + ", you are now connected to the server!");

        try {
            while (this.running) {
                String inputLine = in.readLine();
                terminatingConnection = Objects.equals(inputLine, "Terminating connection");
                if (inputLine == null || terminatingConnection) {
                    terminate();
                    break;
                }
                log.info("Received from client " + this.id + ": " + inputLine);
                messageHandler.handleMessage(inputLine);
            }
        } catch (IOException e) {
            log.error("Error reading string from client " + this.id);
        }
    }

    /**
     * Sends a message to the connected client.
     *
     * @param message The message to be sent to the client.
     */
    public void sendMessage(String message) {
        log.info("Sending: " + message + " to Client id: " + id);
        if (message != null && out != null) {
            out.println(message);
        }
    }

    /**
     * Terminate the Client Handler.
     */
    public void terminate() {
        running = false;
        try {
            server.removeClientHandler(id);
            in.close();
            out.close();
            socket.close();
            log.info("Client Handler " + id + " terminated");
        } catch (IOException e) {
            log.error("Cannot successfully close socket");
        }
    }
}
