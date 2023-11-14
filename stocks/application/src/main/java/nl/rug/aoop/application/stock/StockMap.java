package nl.rug.aoop.application.stock;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class StockMap {
    private Map<String, Stock> stocks;

    public void setStockPrice(Stock stock) {
        if (stocks.containsValue(stock)) {
            stocks.put(stock.getSymbol(), stock);
        }
    }
}
