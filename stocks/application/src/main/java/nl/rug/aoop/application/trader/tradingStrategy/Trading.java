package nl.rug.aoop.application.trader.tradingStrategy;

import lombok.Getter;
import lombok.Setter;
import nl.rug.aoop.application.order.BuyOrder;
import nl.rug.aoop.application.order.SellOrder;
import nl.rug.aoop.application.stock.StockMap;
import nl.rug.aoop.application.trader.StockPortfolio;
import nl.rug.aoop.application.trader.TraderData;

public interface Trading {
    BuyOrder generateBuyOrder();

    SellOrder generateSellOrder();

}
