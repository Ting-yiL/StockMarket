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

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@Slf4j
public class TraderBot {
    private Trading strategy;
    private TraderClient traderClient;
    private Thread tradeThread;

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

    public Message generateOrderCommandMessage() {
        log.info("Generating Order Command");
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

    public void trade() throws InterruptedException {
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

    public void terminate() {};
}
