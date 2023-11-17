package nl.rug.aoop.application.trader;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.order.BuyOrder;
import nl.rug.aoop.application.order.SellOrder;
import nl.rug.aoop.application.trader.tradingStrategy.SmartTrading;
import nl.rug.aoop.messagequeue.message.Message;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

/**
 * The TraderBot class.
 */
@Getter
@Setter
@Slf4j
public class TraderBot {
    private int TIMEOUT = 5000;
    private SmartTrading strategy;
    private TraderClient traderClient;
    private Thread tradeThread;
    private Thread listenThread;

    /**
     * The constructor of the TraderBot.
     * @param traderClient The traderClient.
     */
    public TraderBot(TraderClient traderClient) {
        this.traderClient = traderClient;
        this.strategy = new SmartTrading(traderClient.getStockMap(), traderClient.getTraderData());
    }

    /**
     * Generating buyOrder.
     * @return A buyOrder.
     */
    public BuyOrder generateBuyOrder() {
        log.info("Bot generating Buy Order");
        return this.strategy.generateBuyOrder();
    }

    /**
     * Generating sellOrder.
     * @return A sellOrder.
     */
    public SellOrder generateSellOrder() {
        log.info("Bot generating Sell Order");
        return this.strategy.generateSellOrder();
    }

    /**
     * Updating strategy data.
     */
    private void updateStrategyData() {
        log.info("Update strategy Data");
        this.strategy.setTraderData(this.traderClient.getTraderData());
        this.strategy.setStockMap(this.traderClient.getStockMap());
    }

    /**
     * Generating Order Command Message.
     * @return The Order Command Message.
     */
    public Message generateOrderCommandMessage() {
        log.info("Generating Order Command");
        this.updateStrategyData();
        Random random = new Random();
        String command;
        String orderMessage;
        if (random.nextBoolean()) {
            command = "BuyOrder";
            orderMessage = this.generateBuyOrder().toJson();
        } else {
            command = "SellOrder";
            orderMessage = this.generateSellOrder().toJson();
        }
        return new Message(command, orderMessage);
    }

    /**
     * Starts trading.
     */
    public void trade() {
        log.info("Start trading...");
        this.startListening();
        this.startTrading();
    }

    /**
     * Starts listening for incoming messages.
     */
    public void startListening() {
        log.info("Bot start listening...");
        this.traderClient.startListening();
    }

    /**
     * Starts a trading thread.
     */
    public void startTrading() {
        log.info("Bot Sending request for the trader profile...");
        this.traderClient.initializeTraderProfile();
        await().atMost(Duration.ofMillis(TIMEOUT)).until(this.traderClient::initializedTraderProfile);
        this.tradeThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                log.info("Attempting to trade...");
                int waitTime = (int) (Math.random() * ( 15 + 1));
                Message message = this.generateOrderCommandMessage();
                if (message != null) {
                    this.traderClient.putOrder(message);
                }
                try {
                    TimeUnit.SECONDS.sleep(waitTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        this.tradeThread.start();
        Runtime.getRuntime().addShutdownHook(new Thread(this::terminate));
    }

    /**
     * Termination the client's connection.
     */
    public void terminate() {
        log.info("Terminate trading...");
        this.traderClient.endListening();
    }
}
