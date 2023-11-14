package nl.rug.aoop.application.trader.tradingStrategy;

import nl.rug.aoop.application.order.BuyOrder;
import nl.rug.aoop.application.order.SellOrder;
import nl.rug.aoop.application.trader.StockPortfolio;

public interface Trading {
    BuyOrder generateBuyOrder(StockPortfolio stockPortfolio);

    SellOrder generateBellOrder(StockPortfolio stockPortfolio);
}
