package nl.rug.aoop.application;

import nl.rug.aoop.application.trader.StockPortfolio;
import nl.rug.aoop.application.trader.TraderClient;
import nl.rug.aoop.application.trader.TraderClientMessageHandler;
import nl.rug.aoop.application.trader.TraderData;
import nl.rug.aoop.application.trader.command.TraderClientCommandHandlerFactory;
import nl.rug.aoop.networking.client.Client;
import nl.rug.aoop.networking.handler.MessageLogger;
import nl.rug.aoop.networking.messagequeue.NetworkProducer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class TraderApplication {
    private static final int TIMEOUT = 5000;
    private final int port = 6200;
    private TraderData trader;
    private Client client;
    private TraderClient traderClient;
    private Thread botThread;

    private void initialize() {

    }

    private void loadTraderData() {
        StockPortfolio stockPortfolio = new StockPortfolio();
        stockPortfolio.getOwnedShares().put("NVDA", 3);
        stockPortfolio.getOwnedShares().put("AMD", 23);
        stockPortfolio.getOwnedShares().put("AAPL", 15);
        stockPortfolio.getOwnedShares().put("ADBE", 1);
        stockPortfolio.getOwnedShares().put("FB", 3);
        this.trader = new TraderData("bot1", "Just Bob", 10450, stockPortfolio);
    }
    private void setUpNetWork() throws IOException {
        InetSocketAddress address = new InetSocketAddress(this.port);

        this.client = new Client(address, new MessageLogger());

        NetworkProducer producer = new NetworkProducer(this.client);

        this.traderClient = new TraderClient("bot1", producer, new TraderClientMessageHandler())
    }
}
