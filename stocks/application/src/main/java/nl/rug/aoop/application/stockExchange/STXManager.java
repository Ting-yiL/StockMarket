package nl.rug.aoop.application.stockExchange;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.stock.StockMap;
import nl.rug.aoop.application.trader.TraderData;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.networking.client.Client;
import nl.rug.aoop.networking.server.ClientHandler;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class STXManager {
    private Map<String, ClientHandler> clientHandlerMap;
    private StockExchangeData stockExchangeData;

    public STXManager (StockExchangeData stockExchangeData) {
        this.stockExchangeData = stockExchangeData;
        this.clientHandlerMap = new HashMap<>();
    }

    public void registerTraderConnection(String traderId, ClientHandler clientHandler) {
        log.info("Register " + traderId + " with clientHandler ID " + clientHandler.getId());
        this.clientHandlerMap.put(traderId, clientHandler);
    }

    public void updateTraderProfile(String traderId) {
        TraderData traderData = stockExchangeData.getTraderById(traderId);
        Message message = new Message("StockMap", traderData.toJson());
        NetworkMessage networkMessage = new NetworkMessage("UpdateProfile", message);
        this.clientHandlerMap.get(traderId).sendMessage(networkMessage.toJson());
    }

    public void updateTraderStockMap(String traderId) {
        StockMap stockMap = stockExchangeData.getStocks();
        Message message = new Message("StockMap", stockMap.toJson());
        NetworkMessage networkMessage = new NetworkMessage("UpdateStockMap", message);
        this.clientHandlerMap.get(traderId).sendMessage(networkMessage.toJson());
    }

    public void updateAllTraderProfile() {
        for (String traderId : this.clientHandlerMap.keySet()) {
            this.updateTraderProfile(traderId);
        }
    }

    public void updateAllTraderStockMap() {
        for (String traderId : this.clientHandlerMap.keySet()) {
            this.updateTraderStockMap(traderId);
        }
    }
}
