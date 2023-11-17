package nl.rug.aoop.application.trader;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.order.BuyOrder;
import nl.rug.aoop.application.order.LimitOrder;
import nl.rug.aoop.application.order.SellOrder;
import nl.rug.aoop.application.stock.StockMap;
import nl.rug.aoop.application.trader.tradingStrategy.SmartTrading;
import nl.rug.aoop.application.trader.tradingStrategy.Trading;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.networking.client.Client;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@Getter
@Setter
@Slf4j
public class TraderBot {
    private int TIMEOUT = 5000;
    private SmartTrading strategy;
    private TraderClient traderClient;
    private Thread tradeThread;
    private Thread listenThread;

    public TraderBot(TraderClient traderClient) {
        this.traderClient = traderClient;
        this.strategy = new SmartTrading(traderClient.getStockMap(), traderClient.getTraderData());
    }

    public BuyOrder generateBuyOrder() {
        log.info("Bot generating Buy Order");
        return this.strategy.generateBuyOrder();
    }

    public SellOrder generateSellOrder() {
        log.info("Bot generating Sell Order");
        return this.strategy.generateSellOrder();
    }

    private void updateStrategyData() {
        log.info("Update strategy Data");
        this.strategy.setTraderData(this.traderClient.getTraderData());
        this.strategy.setStockMap(this.traderClient.getStockMap());
    }

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

    public void trade() {
        log.info("Start trading...");
        this.startListening();
        this.startTrading();
    }

    public void startListening() {
        log.info("Bot start listening...");
        this.traderClient.startListening();
    }

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

    public void terminate() {
        log.info("Terminate trading...");
        this.traderClient.endListening();
    };
}
