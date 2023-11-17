package nl.rug.aoop.application;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.stock.StockMap;
import nl.rug.aoop.application.stockExchange.StockExchangeData;
import nl.rug.aoop.application.trader.*;
import nl.rug.aoop.networking.handler.MessageLogger;
import nl.rug.aoop.networking.messagequeue.NetworkProducer;
import nl.rug.aoop.util.YamlLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;


@Slf4j
public class TraderApplication {
    private static final int TIMEOUT = 5000;
    private final int port = 6200;
    private TraderBot bot;
    private TraderClient traderClient;

    public static void main(String[] args) {
        TraderApplication app = new TraderApplication();
        app.initialize();
        app.startTrading();
    }

    public void startTrading() {
        log.info("start trading");
        this.bot.trade();
    }

    private void initialize() {
        this.loadTraderData();
        try {
            this.setUpNetWork();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void terminate() {
        log.info("Terminating Stock Application");
        this.bot.terminate();
    }

    private void loadTraderData() {
        log.info("loading data");
        StockPortfolio stockPortfolio = new StockPortfolio();
        stockPortfolio.getOwnedShares().put("NVDA", 3);
        stockPortfolio.getOwnedShares().put("AMD", 23);
        stockPortfolio.getOwnedShares().put("AAPL", 15);
        stockPortfolio.getOwnedShares().put("ADBE", 1);
        stockPortfolio.getOwnedShares().put("FB", 3);
        //this.trader = new TraderData("bot1", "Just Bob", 10450, stockPortfolio);
    }
    private void setUpNetWork() throws IOException {
        log.info("Setting up the network");

        this.traderClient = new TraderClient(this.port,"bot1");
        this.bot = new TraderBot(this.traderClient);
    }
}
