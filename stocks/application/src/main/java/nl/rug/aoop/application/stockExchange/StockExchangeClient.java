package nl.rug.aoop.application.stockExchange;

import nl.rug.aoop.networking.messagequeue.NetworkConsumer;
import nl.rug.aoop.networking.client.Client;

public class StockExchangeClient extends NetworkConsumer {
    public StockExchangeClient(Client client) {
        super(client);
    }
}
