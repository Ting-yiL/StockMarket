package nl.rug.aoop.application.trader.tradingStrategy;

import nl.rug.aoop.application.order.BuyOrder;
import nl.rug.aoop.application.order.SellOrder;
import nl.rug.aoop.application.trader.StockPortfolio;
import nl.rug.aoop.application.trader.tradingStrategy.Trading;

public class SmartTrading implements Trading {
    //know current market price

    @Override
    public BuyOrder generateBuyOrder(StockPortfolio stockPortfolio) {
        return null;
    }

    @Override
    public SellOrder generateBellOrder(StockPortfolio stockPortfolio) {
        return null;
    }
}
