package nl.rug.aoop.application.trader;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.stock.StockMap;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.networking.messagequeue.NetworkProducer;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The TraderClient class.
 */
@Getter
@Setter
@Slf4j
public class TraderClient {
    private String traderId;
    private TraderData traderData;
    private NetworkProducer networkProducer;
    private TraderClientMessageHandler messageHandler;
    private StockMap stockMap;
    private AtomicBoolean status = new AtomicBoolean(false);
    private DebtTracker debtTracker;

    /**
     * The constructor of traderClient.
     * @param port The port.
     * @param traderId The traderID.
     * @throws IOException IOException.
     */
    public TraderClient(int port, String traderId) throws IOException {
        this.traderId = traderId;
        this.messageHandler = new TraderClientMessageHandler(this);
        this.networkProducer = new NetworkProducer(port, this.messageHandler);
        this.debtTracker = new DebtTracker(this);
    }

    /**
     * Generating register profile message.
     * @return The message string.
     */
    public String generateRegisterProfileMessage() {
        Message message = new Message("Trader ID", traderId);
        NetworkMessage networkMessage = new NetworkMessage("RegisterProfile", message);
        return networkMessage.toJson();
    }

    /**
     * Initializing the trader profile.
     */
    public void initializeTraderProfile() {
        this.networkProducer.getClient().sendMessage(this.generateRegisterProfileMessage());
    }

    /**
     * Return if the trader's data is ready or not.
     * @return The status.
     */
    public boolean initializedTraderProfile() {
        return (this.traderData != null) && (this.stockMap != null);
    }

    /**
     * Receiving resolved Order.
     * @param message The message order.
     */
    public void receiveResolvedOrder(Message message) {
        log.info("Receive resolved order");
        this.debtTracker.removePendingOrder(message);
    }

    /**
     * Putting the order to the server.
     * @param message The order message.
     */
    public void putOrder(Message message) {
        if (this.debtTracker.verifyOrder(message)) {
            this.networkProducer.put(message);
            this.debtTracker.addPendingOrder(message);
        }
    }

    /**
     * Starts listening.
     */
    public void startListening() {
        log.info("Starting to listen...");
        this.networkProducer.start();
    }

    /**
     * Ends listening.
     */
    public void endListening() {
        log.info("End listening...");
        this.networkProducer.stop();
    }
}
