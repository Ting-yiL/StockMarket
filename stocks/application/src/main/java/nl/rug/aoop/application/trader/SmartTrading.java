package nl.rug.aoop.application.trader;

import nl.rug.aoop.application.order.BuyOrder;
import nl.rug.aoop.application.order.SellOrder;

public class SmartTrading implements Trading{
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
