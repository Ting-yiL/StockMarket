package nl.rug.aoop.application.stockExchange;

import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.networking.messagequeue.NetworkConsumer;

public class StockExchangeClient {
    private StockExchangeData stockExchangeData;
    private NetworkConsumer networkConsumer;
    private StockExchangeMessageHandler messageHandler;

    public StockExchangeClient(NetworkConsumer networkConsumer,
                               StockExchangeData stockExchangeData, StockExchangeMessageHandler messageHandler) {
        this.stockExchangeData = stockExchangeData;
        this.networkConsumer = networkConsumer;
        this.messageHandler = messageHandler;
    }

    public void listenOrder() {
        Message message = this.networkConsumer.poll();
        if (message != null) {
            this.messageHandler.handleMessage(message);
        }
    }
}
