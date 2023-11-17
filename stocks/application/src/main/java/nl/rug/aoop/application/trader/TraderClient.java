package nl.rug.aoop.application.trader;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.stock.StockMap;
import nl.rug.aoop.application.trader.command.TraderClientCommandHandlerFactory;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.networking.messagequeue.NetworkProducer;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

@Getter
@Setter
@Slf4j
public class TraderClient {
    String traderId;
    private TraderData traderData;
    private NetworkProducer networkProducer;
    private TraderClientMessageHandler messageHandler;
    private StockMap stockMap;
    private AtomicBoolean status = new AtomicBoolean(false);

    public TraderClient(int port, String traderId) throws IOException {
        this.traderId = traderId;
        this.messageHandler = new TraderClientMessageHandler(this);
        this.networkProducer = new NetworkProducer(port, this.messageHandler);
    }

    public String generateRegisterProfileMessage() {
        Message message = new Message("Trader ID", traderId);
        NetworkMessage networkMessage = new NetworkMessage("RegisterProfile", message);
        return networkMessage.toJson();
    }

    public void initializeTraderProfile() {
        this.networkProducer.getClient().sendMessage(this.generateRegisterProfileMessage());
    }

    public boolean initializedTraderProfile() {
        return (this.traderData != null) && (this.stockMap != null);
    }

    public void putOrder(Message message) {
        this.networkProducer.put(message);
    }

    public void startListening() {
        log.info("Starting to listen...");
        this.networkProducer.start();
    }

    public void endListening() {
        log.info("End listening...");
        this.networkProducer.stop();
    }
}
