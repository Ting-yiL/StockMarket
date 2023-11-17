package nl.rug.aoop.application.trader;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.order.BuyOrder;
import nl.rug.aoop.application.order.SellOrder;
import nl.rug.aoop.messagequeue.message.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The debtTracker - a class that keeps track of what orders a trader has made.
 */
@Slf4j
public class DebtTracker {
    private List<BuyOrder> pendingBuyOrder = new ArrayList<>();
    private List<SellOrder> pendingSellOrder = new ArrayList<>();
    private double fundDebt = 0;
    private Map<String, Integer> shareDebt = new HashMap<>();
    private TraderClient traderClient;

    /**
     * The constructor of traderClient.
     * @param traderClient The traderClient.
     */
    public DebtTracker(TraderClient traderClient) {
        this.traderClient = traderClient;
    }

    /**
     * Removing a pending order.
     * @param message the order message.
     */
    public void removePendingOrder(Message message) {
        log.info("Removing pending order");
        if (message.getHeader().equals("BuyOrder")) {
            BuyOrder buyOrder = BuyOrder.fromJson(message.getBody());
            this.pendingBuyOrder.add(buyOrder);
            this.fundDebt -= buyOrder.getPrice() * buyOrder.getQuantity();
        } else {
            SellOrder sellOrder = SellOrder.fromJson(message.getBody());
            String stockSymbol = sellOrder.getStockSymbol();
            this.pendingSellOrder.remove(sellOrder);
            if (!this.shareDebt.containsKey(sellOrder.getStockSymbol())) {
                this.shareDebt.put(stockSymbol, 0);
            }
            this.shareDebt.replace(stockSymbol, this.shareDebt.get(stockSymbol) - sellOrder.getQuantity());
        }
    }

    /**
     * Adding a pending order.
     * @param message The message.
     */
    public void addPendingOrder(Message message) {
        log.info("Adding pending order");
        if (message.getHeader().equals("BuyOrder")) {
            BuyOrder buyOrder = BuyOrder.fromJson(message.getBody());
            this.pendingBuyOrder.add(buyOrder);
            this.fundDebt += buyOrder.getPrice() * buyOrder.getQuantity();
        } else {
            SellOrder sellOrder = SellOrder.fromJson(message.getBody());
            String stockSymbol = sellOrder.getStockSymbol();
            this.pendingSellOrder.add(sellOrder);
            if (!this.shareDebt.containsKey(sellOrder.getStockSymbol())) {
                this.shareDebt.put(stockSymbol, 0);
            }
            this.shareDebt.replace(stockSymbol, this.shareDebt.get(stockSymbol) + sellOrder.getQuantity());
        }
    }

    /**
     * Verifying an order.
     * @param message the order message.
     * @return verification.
     */
    public boolean verifyOrder(Message message) {
        log.info("Verifying Order");
        if (message.getHeader().equals("BuyOrder")) {
            BuyOrder buyOrder = BuyOrder.fromJson(message.getBody());
            double orderValue = buyOrder.getPrice() * buyOrder.getQuantity();
            return this.traderClient.getTraderData().getFunds() - this.fundDebt - orderValue > 0;
        } else {
            SellOrder sellOrder = SellOrder.fromJson(message.getBody());
            String stockSymbol = sellOrder.getStockSymbol();
            int shares = sellOrder.getQuantity();
            if (!this.shareDebt.containsKey(sellOrder.getStockSymbol())) {
                this.shareDebt.put(stockSymbol, 0);
            }
            int sharesDebt = this.shareDebt.get(stockSymbol);
            return this.traderClient.getTraderData().
                    getStockPortfolio().
                    getOwnedShares().
                    get(stockSymbol) - sharesDebt - shares > 0;
        }
    }
}
