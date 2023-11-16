package nl.rug.aoop.application.trader;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.stock.StockMap;
import nl.rug.aoop.application.trader.command.TraderClientCommandHandlerFactory;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.networking.messagequeue.NetworkProducer;

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

    public TraderClient(String traderId, NetworkProducer networkProducer) {
        this.traderId = traderId;
        this.networkProducer = networkProducer;
        this.messageHandler = new TraderClientMessageHandler(this);
    }

    public String generateRequestProfileMessage() {
        Message message = new Message("Trader ID", traderId);
        NetworkMessage networkMessage = new NetworkMessage("RequestProfile", message);
        return networkMessage.toJson();
    }

    public String generateRequestStockMapUpdate() {
        Message message = new Message("", "");
        NetworkMessage networkMessage = new NetworkMessage("RequestStockMap", message);
        return networkMessage.toJson();
    }

    public void initializeTraderProfile() {
        this.networkProducer.getClient().sendMessage(this.generateRequestProfileMessage());
        this.networkProducer.getClient().sendMessage(this.generateRequestStockMapUpdate());
    }

    public boolean initializedTraderProfile() {
        return (this.traderData != null) && (this.stockMap != null);
    }

    public void putOrder(Message message) {
        this.networkProducer.put(message);
    }

    public void startListening() {
        this.networkProducer.start();
    }

    public void endListening() {
        this.networkProducer.stop();
    }
}
