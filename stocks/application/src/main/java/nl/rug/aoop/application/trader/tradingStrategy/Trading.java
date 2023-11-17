package nl.rug.aoop.application.trader.tradingStrategy;

import nl.rug.aoop.application.order.BuyOrder;
import nl.rug.aoop.application.order.SellOrder;

/**
 * Trading Strategy.
 */
public interface Trading {
    /**
     * Generates BuyOrder.
     * @return A BuyOrder.
     */
    BuyOrder generateBuyOrder();

    /**
     * Generates SellOrder.
     * @return A SellOrder.
     */
    SellOrder generateSellOrder();

}
