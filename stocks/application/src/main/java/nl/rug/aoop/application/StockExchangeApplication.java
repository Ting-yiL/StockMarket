package nl.rug.aoop.application;

import nl.rug.aoop.application.stockExchange.StockExchangeServer;

import java.io.IOException;
import java.nio.file.Path;

public class StockExchangeApplication {
    private static final int TIMEOUT = 5000;
    private final int port = 6200;
    private final Path STOCKPATH = Path.of("stocks","data", "stocks.yaml");
    private final Path TRADERPATH = Path.of("stocks","data", "traders.yaml");
    private StockExchangeServer stockExchangeServer;

    public static void main(String[] args) throws IOException {
        StockExchangeApplication app = new StockExchangeApplication();
        app.initialize();
        app.run();

    }

    public void initialize() throws IOException {
        stockExchangeServer = new StockExchangeServer(port, STOCKPATH, TRADERPATH);
    }

    public void run() {
        this.stockExchangeServer.runStockExchangeServer();
    }
}
