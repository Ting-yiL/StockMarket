package nl.rug.aoop.application.stockExchange;

import nl.rug.aoop.networking.server.ClientHandler;
import nl.rug.aoop.networking.server.Server;

import java.util.Map;

public class STXManager {
    private Server server;
    private Map<Integer, ClientHandler> clientHandlerMap;

    public STXManager (Server server) {
        this.server = server;
    }


}
