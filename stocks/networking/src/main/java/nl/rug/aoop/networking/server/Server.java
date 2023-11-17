package nl.rug.aoop.networking.server;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.networking.handler.MessageHandlerWithReference;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server of the Client/Server.
 * @author Dylan Bonatz, Ting-Yi Lin
 * @version 1.0
 */
@Slf4j
public class Server implements Runnable{
    private final ServerSocket serverSocket;
    private final ExecutorService service;
    @Getter
    private MessageHandlerWithReference messageHandler;
    private int id = 0;
    @Getter
    private boolean running = false;
    @Getter
    private Map<Integer, ClientHandler> clientHandlerMap= new HashMap();

    /**
     * The constructor of the Server.
     * @param port The port in which the connection happens.
     * @param messageHandler The messageHandler.
     * @throws IOException In case of IOException error.
     */
    public Server(int port, MessageHandlerWithReference messageHandler) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.service = Executors.newCachedThreadPool();
        this.messageHandler = messageHandler;
    }

    /**
     * Getting the port from the server socket.
     * @return Port of the socket.
     */
    public int getPort() {
        return this.serverSocket.getLocalPort();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        this.running = true;
        log.info("Server started, listening for client connections...");
        while(this.running) {
            try {
                Socket socket = this.serverSocket.accept();
                log.info("New connection from client");
                log.info("Accepted new client connection from: " + socket.getRemoteSocketAddress());
                ClientHandler clientHandlerBuffer = new ClientHandler(socket, this.messageHandler, this.id, this);
                this.clientHandlerMap.put(this.id, clientHandlerBuffer);
                this.service.submit(clientHandlerBuffer);
                this.id++;
            } catch (IOException e) {
                log.error("Socket error", e);
            }
        }
    }

    /**
     * Remove clientHandler by the provided id.
     * @param id The id.
     */
    public void removeClientHandler(int id) {
        this.clientHandlerMap.remove(id);
    }

    /**
     * Terminate the server.
     */
    public void terminate() {
        this.running = false;
        try {
            this.serverSocket.close();
            this.service.shutdown();
        } catch (IOException e) {
            log.error("Error closing the server socket", e);
        }
        this.service.shutdown();
    }
}
