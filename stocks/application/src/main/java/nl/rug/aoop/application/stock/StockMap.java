package nl.rug.aoop.application.stock;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import nl.rug.aoop.application.trader.StockPortfolio;

import java.util.Map;

@Getter
@Setter
public class StockMap {
    private Map<String, Stock> stocks;
    private static final Gson GSON = new Gson();

    public void setStockPrice(Stock stock) {
        if (stocks.containsValue(stock)) {
            stocks.put(stock.getSymbol(), stock);
        }
    }

    public String toJson() {
        return GSON.toJson(this);
    }

    public static StockMap fromJson(String json) {
        return GSON.fromJson(json, StockMap.class);
    }
}
