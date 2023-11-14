package nl.rug.aoop.application.stockExchange;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.order.BuyOrder;
import nl.rug.aoop.application.order.SellOrder;
import nl.rug.aoop.application.order.comparator.BuyOrderComparator;
import nl.rug.aoop.application.order.comparator.SellOrderComparator;
import nl.rug.aoop.application.stock.Stock;
import nl.rug.aoop.application.stock.StockMap;
import nl.rug.aoop.application.trader.Trader;
import nl.rug.aoop.model.StockDataModel;
import nl.rug.aoop.model.StockExchangeDataModel;
import nl.rug.aoop.model.TraderDataModel;

import java.util.*;

@Slf4j
public class StockExchangeData implements StockExchangeDataModel {
    StockMap stocks;
    private final Map<String, Trader> traders = new HashMap<>();
    private final Map<Stock, PriorityQueue<BuyOrder>> bids = new HashMap<>();
    private final Map<Stock, PriorityQueue<SellOrder>> asks = new HashMap<>();


    public StockExchangeData(StockMap stocks, List<Trader> traders) {
        this.stocks = stocks;
        for (Trader trader : traders) {
            this.traders.put(trader.getId(), trader);
        }
        for (Stock stock : stocks.getStocks().values()) {
            bids.put(stock, new PriorityQueue<>(new BuyOrderComparator()));
            asks.put(stock, new PriorityQueue<>(new SellOrderComparator()));
        }
    }

    @Override
    public StockDataModel getStockByIndex(int index) {
        List<String> stockList = new ArrayList<>(this.stocks.getStocks().keySet());
        String stockName = stockList.get(index);
        return this.stocks.getStocks().get(stockName);
    }

    @Override
    public int getNumberOfStocks() {
        return this.stocks.getStocks().size();
    }

    @Override
    public TraderDataModel getTraderByIndex(int index) {
        return this.traders.get(index);
    }

    @Override
    public int getNumberOfTraders() {
        return this.traders.size();
    }

    public void matchBuyOrder(BuyOrder buyOrder) {
        log.info("Matching buy order");
    }

    public void matchSellOrder(SellOrder sellOrder) {
        log.info("Matching sell order");
    }
}
