package nl.rug.aoop.application.stockExchange;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.networking.messagequeue.NetworkConsumer;

@Slf4j
public class StockExchangeClient {
    private StockExchangeData stockExchangeData;
    private NetworkConsumer networkConsumer;
    private StockExchangeMessageHandler messageHandler;

    public StockExchangeClient(NetworkConsumer networkConsumer, StockExchangeData stockExchangeData) {
        this.stockExchangeData = stockExchangeData;
        this.networkConsumer = networkConsumer;
        this.messageHandler  = new StockExchangeMessageHandler(this.stockExchangeData);
    }

    public void startListeningOrder() {
        this.networkConsumer.start();
    }

    public void stopListeningOrder() {
        this.networkConsumer.stop();
    }

    public void listenOrder() {
        Message message = this.networkConsumer.poll();
        if (message != null) {
            log.info("Receiving message from Network Consumer");
            this.messageHandler.handleMessage(message);
        }
    }
}
