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
    private TraderData trader;
    private TraderBot bot;
    private TraderClient traderClient;
    private StockExchangeData stockExchangeData;
    private Thread botThread;
    private final Path STOCKPATH = Path.of("stocks","data", "stocks.yaml");
    private final Path TRADERPATH = Path.of("stocks","data", "traders.yaml");

    public static void main(String[] args) {
        TraderApplication app = new TraderApplication();
        app.initialize();
        app.startTrading();
    }

    public void startTrading() {
        log.info("start trading");
        this.botThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    this.bot.trade();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        this.botThread.start();
        Runtime.getRuntime().addShutdownHook(new Thread(this::terminate));
    }

    private void initialize() {
        try {
            this.loadStockExchangeData(STOCKPATH,TRADERPATH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.loadTraderData();
        try {
            this.setUpNetWork();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void terminate() {
        log.info("Terminating Stock Application");
        this.botThread.interrupt();
    }

    private void loadTraderData() {
        log.info("loading data");
        StockPortfolio stockPortfolio = new StockPortfolio();
        stockPortfolio.getOwnedShares().put("NVDA", 3);
        stockPortfolio.getOwnedShares().put("AMD", 23);
        stockPortfolio.getOwnedShares().put("AAPL", 15);
        stockPortfolio.getOwnedShares().put("ADBE", 1);
        stockPortfolio.getOwnedShares().put("FB", 3);
        this.trader = new TraderData("bot1", "Just Bob", 10450, stockPortfolio);
    }
    private void setUpNetWork() throws IOException {
        log.info("Setting up the network");

        NetworkProducer networkProducer = new NetworkProducer(this.port, new MessageLogger());
        this.traderClient = new TraderClient("bot1", networkProducer);
        this.traderClient.setTraderData(this.trader);
        this.traderClient.setStockMap(this.stockExchangeData.getStocks());
        this.bot = new TraderBot(this.traderClient);
    }

    private void loadStockExchangeData(Path stockPath, Path tradePath) throws IOException {
        YamlLoader yamlLoader1 = new YamlLoader(stockPath);
        YamlLoader yamlLoader2 = new YamlLoader(tradePath);

        List<TraderData> tradersList;
        StockMap stocks;
        stocks = yamlLoader1.load(StockMap.class);
        tradersList = yamlLoader2.load(new TypeReference<>() {});
        this.stockExchangeData = new StockExchangeData(stocks, tradersList);
    }
}
