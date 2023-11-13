package nl.rug.aoop.application.stockExchange;

import nl.rug.aoop.application.stock.StockMap;
import nl.rug.aoop.application.trader.Trader;
import nl.rug.aoop.model.StockDataModel;
import nl.rug.aoop.model.StockExchangeDataModel;
import nl.rug.aoop.model.TraderDataModel;

import java.util.ArrayList;
import java.util.List;

public class StockExchangeData implements StockExchangeDataModel {
    StockMap stocks;
    List<Trader> traders;


    public StockExchangeData(StockMap stocks, List<Trader> traders) {
        this.stocks = stocks;
        this.traders = traders;
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
}
