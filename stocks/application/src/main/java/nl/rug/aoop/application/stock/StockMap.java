package nl.rug.aoop.application.stock;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * The Stock Map, mapping the stock using their symbol.
 */
@Getter
@Setter
public class StockMap {
    private Map<String, Stock> stocks;
    private static final Gson GSON = new Gson();

    /**
     * Setting the stock price in the stock map.
     * @param stock the stock with the new price.
     */
    public void setStockPrice(Stock stock) {
        if (stocks.containsValue(stock)) {
            stocks.put(stock.getSymbol(), stock);
        }
    }

    /**
     * Converts to a Json string.
     * @return Json string.
     */
    public String toJson() {
        return GSON.toJson(this);
    }

    /**
     * Converts Json string to a StockMap Object.
     * @param json The Json string.
     * @return The StockMap Object.
     */
    public static StockMap fromJson(String json) {
        return GSON.fromJson(json, StockMap.class);
    }
}
