package nl.rug.aoop.application.stockExchange;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.stock.StockMap;
import nl.rug.aoop.application.trader.TraderData;
import nl.rug.aoop.networking.client.Client;
import nl.rug.aoop.networking.server.ClientHandler;

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
        this.clientHandlerMap.get(traderId).sendMessage(traderData.toJson());
    }

    public void updateTraderStockMap(String traderId) {
        StockMap stockMap = stockExchangeData.getStocks();
        this.clientHandlerMap.get(traderId).sendMessage(stockMap.toJson());
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
