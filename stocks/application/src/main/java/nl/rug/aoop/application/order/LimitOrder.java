package nl.rug.aoop.application.order;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;

/**
 * The Limit Order - an abstract class for buy/sell order.
 */
@Getter
@Setter
public abstract class LimitOrder {
    private static final Gson GSON = new Gson();
    private String traderID;
    private String stockSymbol;
    private double price;
    private int quantity;

    /**
     * The constructor of limitOrder.
     * @param traderId The trader's ID.
     * @param stockSymbol The stock's symbol.
     * @param price The price of the stock.
     * @param quantity The quantity of the stock.
     */
    public LimitOrder(String traderId, String stockSymbol, double price, int quantity) {
        this.traderID = traderId;
        this.stockSymbol = stockSymbol;
        this.price = price;
        this.quantity = quantity;
    }
}
