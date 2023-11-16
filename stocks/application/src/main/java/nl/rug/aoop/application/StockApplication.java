package nl.rug.aoop.application;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.stockExchange.StockExchangeClient;
import nl.rug.aoop.initialization.SimpleViewFactory;
import nl.rug.aoop.application.stockExchange.StockExchangeData;
import nl.rug.aoop.application.stock.StockMap;
import nl.rug.aoop.application.trader.TraderData;
import nl.rug.aoop.networking.client.Client;
import nl.rug.aoop.networking.handler.NetworkConsumerMessageHandler;
import nl.rug.aoop.networking.messagequeue.NetworkConsumer;
import nl.rug.aoop.networking.messagequeue.NetworkMedium;
import nl.rug.aoop.util.YamlLoader;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class StockApplication {
    private static final int TIMEOUT = 5000;
    private final int port = 6200;
    private final Path STOCKPATH = Path.of("stocks","data", "stocks.yaml");
    private final Path TRADERPATH = Path.of("stocks","data", "traders.yaml");
    private StockExchangeData stockExchange;
    private StockExchangeClient stockExchangeClient;
    private Thread messageListenerThread;

    public static void main(String[] args) {
        StockApplication app = new StockApplication();
        app.initialize();
        app.startOrderListener();
    }

    private void initialize() {
        try {
            this.loadStockExchangeData(this.STOCKPATH, this.TRADERPATH);
            this.setUpUI(new SimpleViewFactory());
            this.setUpNetwork();
        } catch (IOException e) {
            log.error("Cannot run Stock Application due to" + e, e);
        }
    }

    public void  startOrderListener() {
        this.stockExchangeClient.startListeningOrder();

        this.messageListenerThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                this.stockExchangeClient.listenOrder();
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        this.messageListenerThread.start();
        Runtime.getRuntime().addShutdownHook(new Thread(this::terminate));
    }

    private void terminate() {
        log.info("Terminating Stock Application");
        this.stockExchangeClient.stopListeningOrder();
        this.messageListenerThread.interrupt();
    }

    public void setUpNetwork() throws IOException {
        NetworkConsumer networkConsumer = new NetworkConsumer(this.port);

        this.stockExchangeClient = new StockExchangeClient(networkConsumer, this.stockExchange);
    }

    private void setUpUI(SimpleViewFactory stockView) {
        stockView.createView(this.stockExchange);
    }

    private void loadStockExchangeData(Path stockPath, Path tradePath) throws IOException {
        YamlLoader yamlLoader1 = new YamlLoader(stockPath);
        YamlLoader yamlLoader2 = new YamlLoader(tradePath);

        List<TraderData> tradersList;
        StockMap stocks;
        stocks = yamlLoader1.load(StockMap.class);
        tradersList = yamlLoader2.load(new TypeReference<>() {});
        this.stockExchange = new StockExchangeData(stocks, tradersList);
    }
}
