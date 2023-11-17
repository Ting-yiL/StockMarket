package nl.rug.aoop.application.trader;

import com.fasterxml.jackson.core.type.TypeReference;
import nl.rug.aoop.application.stock.StockMap;
import nl.rug.aoop.networking.handler.MessageLogger;
import nl.rug.aoop.networking.messagequeue.NetworkProducer;
import nl.rug.aoop.util.YamlLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TraderBotFacade {
    private List<TraderData> traders;
    private StockMap stockMap;
    private List<TraderBot> traderBotList = new ArrayList<>() {
    };
    private int port;

    public TraderBotFacade(int port, Path stockPath, Path traderPath) throws IOException {
        this.port = port;
        YamlLoader yamlLoader1 = new YamlLoader(stockPath);
        YamlLoader yamlLoader2 = new YamlLoader(traderPath);
        stockMap = yamlLoader1.load(StockMap.class);
        traders = yamlLoader2.load(new TypeReference<>() {});
    }

    public void createBotsConnection() throws IOException {
        for (TraderData trader : traders) {
            NetworkProducer networkProducerBuffer = new NetworkProducer(this.port, new MessageLogger());
            TraderClient traderClientBuffer = new TraderClient(trader.getId(), networkProducerBuffer);
            traderClientBuffer.setTraderData(trader);
            traderClientBuffer.setStockMap(this.stockMap);
            traderBotList.add(new TraderBot(traderClientBuffer));
        }
    }

    public void startTrading() throws InterruptedException {
        for (TraderBot traderBot : this.traderBotList) {
            traderBot.trade();
        }
    }

    public void stopTrading() throws InterruptedException {
        for (TraderBot traderBot : this.traderBotList) {
            traderBot.terminate();
        }
    }
}
