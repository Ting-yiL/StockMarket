package nl.rug.aoop.application.stockExchange;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.stock.StockMap;
import nl.rug.aoop.application.trader.TraderData;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.networking.server.ClientHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * The STX Manager class - Managing the connections of all clientsHandlers.
 */
@Slf4j
public class STXManager {
    private Map<String, ClientHandler> clientHandlerMap;
    private StockExchangeData stockExchangeData;

    /**
     * The constructor of STXManager.
     * @param stockExchangeData The StockExchangeData.
     */
    public STXManager(StockExchangeData stockExchangeData) {
        this.stockExchangeData = stockExchangeData;
        this.clientHandlerMap = new HashMap<>();
    }

    /**
     * Registering Trader Connection.
     * @param traderId The trader ID.
     * @param clientHandler The clientHandler.
     */
    public void registerTraderConnection(String traderId, ClientHandler clientHandler) {
        log.info("Register " + traderId + " with clientHandler ID " + clientHandler.getId());
        this.clientHandlerMap.put(traderId, clientHandler);
    }

    /**
     * Sending a message to update the trader's portfolio from server.
     * @param traderId The trader's id.
     * @param orderJson The order that is resolved(Can be null).
     */
    public void updateTraderProfile(String traderId, String orderJson) {
        TraderData traderData = stockExchangeData.getTraderById(traderId);
        Message message = new Message(orderJson, traderData.toJson());
        NetworkMessage networkMessage = new NetworkMessage("UpdateProfile", message);
        this.clientHandlerMap.get(traderId).sendMessage(networkMessage.toJson());
    }

    /**
     * Sending a message to update the trader's StockMap from server.
     * @param traderId The trader's id.
     */
    public void updateTraderStockMap(String traderId) {
        StockMap stockMap = stockExchangeData.getStocks();
        Message message = new Message("StockMap", stockMap.toJson());
        NetworkMessage networkMessage = new NetworkMessage("UpdateStockMap", message);
        this.clientHandlerMap.get(traderId).sendMessage(networkMessage.toJson());
    }

    /**
     * Sending a message to update all trader's portfolio from server.
     */
    public void updateAllTraderProfile() {
        for (String traderId : this.clientHandlerMap.keySet()) {
            this.updateTraderProfile(traderId, null);
        }
    }

    /**
     * Sending a message to update all trader's StockMap from server.
     */
    public void updateAllTraderStockMap() {
        for (String traderId : this.clientHandlerMap.keySet()) {
            this.updateTraderStockMap(traderId);
        }
    }
}
