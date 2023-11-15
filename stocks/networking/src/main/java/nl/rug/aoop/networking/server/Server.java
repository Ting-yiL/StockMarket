package nl.rug.aoop.networking.server;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.networking.handler.MQServerMessageHandler;
import nl.rug.aoop.networking.handler.MessageHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
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
    private Map<Integer, ClientHandler> clientHandlerMap;
    private final ServerSocket serverSocket;
    private final ExecutorService service;
    private MessageHandler messageHandler;
    private int id = 0;
    @Getter
    private boolean running = false;

    /**
     * The constructor of the Server.
     * @param port The port in which the connection happens.
     * @throws IOException In case of IOException error.
     */
    public Server(int port, MessageHandler messageHandler) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.service = Executors.newCachedThreadPool();
        this.clientHandlerMap = new HashMap<>();
        this.messageHandler = messageHandler;
    }

    /**
     * Getting the port from the server socket.
     * @return Port of the socket
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
                clientHandlerMap.put(this.id, new ClientHandler(socket, (MQServerMessageHandler) this.messageHandler,
                        this.id, this));
                this.service.submit(clientHandlerMap.get(id));
                this.id++;
                //this.messageHandler.setClientHandlers((List<ClientHandler>) clientHandlerMap.values());
            } catch (IOException e) {
                log.error("Socket error", e);
            }
        }
    }

    public void removeClientHandler(int id) {
        clientHandlerMap.remove(id);
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
