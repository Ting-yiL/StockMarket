package nl.rug.aoop.application;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.stockExchange.StockExchangeClient;
import nl.rug.aoop.application.stockExchange.StockExchangeMessageHandler;
import nl.rug.aoop.initialization.SimpleViewFactory;
import nl.rug.aoop.application.stockExchange.StockExchangeData;
import nl.rug.aoop.application.stock.StockMap;
import nl.rug.aoop.application.trader.TraderData;
import nl.rug.aoop.networking.client.Client;
import nl.rug.aoop.networking.handler.NetworkConsumerMessageHandler;
import nl.rug.aoop.networking.messagequeue.NetworkCommunicator;
import nl.rug.aoop.networking.messagequeue.NetworkConsumer;
import nl.rug.aoop.util.YamlLoader;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.util.List;

@Slf4j
public class StockApplication {
    private static final int TIMEOUT = 5000;
    private final int port = 6200;
    private final Path STOCKPATH = Path.of("stocks","data", "stocks.yaml");
    private final Path TRADERPATH = Path.of("stocks","data", "traders.yaml");
    private StockExchangeData stockExchange;
    private Client client;
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
        this.messageListenerThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                this.stockExchangeClient.listenOrder();
            }
        });

        this.messageListenerThread.start();
        Runtime.getRuntime().addShutdownHook(new Thread(this::terminate));
    }

    private void terminate() {
        log.info("Terminating Stock Application");
        this.messageListenerThread.interrupt();
        this.client.terminate();
    }

    public void setUpNetwork() throws IOException {
        InetSocketAddress address = new InetSocketAddress(this.port);
        NetworkCommunicator communicator = new NetworkCommunicator();
        NetworkConsumerMessageHandler ConsumerMessageHandler = new NetworkConsumerMessageHandler(communicator);

        this.client = new Client(address, ConsumerMessageHandler);

        StockExchangeMessageHandler stockExchangeMessageHandler = new StockExchangeMessageHandler(this.stockExchange);
        NetworkConsumer networkConsumer = new NetworkConsumer(this.client, communicator);

        this.stockExchangeClient = new StockExchangeClient(networkConsumer, this.stockExchange, stockExchangeMessageHandler);
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
