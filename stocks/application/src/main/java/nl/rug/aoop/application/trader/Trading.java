package nl.rug.aoop.application.trader;

import nl.rug.aoop.application.order.BuyOrder;
import nl.rug.aoop.application.order.SellOrder;

public interface Trading {
    BuyOrder generateBuyOrder(StockPortfolio stockPortfolio);

    SellOrder generateBellOrder(StockPortfolio stockPortfolio);
}
