package nl.rug.aoop.application.order;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The SellOrder.
 */
public class SellOrder extends LimitOrder {
    private static final Gson g = new Gson();

    /**
     * The constructor of the sellOrder.
     * @param traderId The trader's ID.
     * @param stockSymbol The stock's symbol.
     * @param price The price of the stock that the trader wants to sell.
     * @param quantity The quantity of the stock that the trader wants to sell.
     */
    public SellOrder(String traderId, String stockSymbol, double price, int quantity) {
        super(traderId, stockSymbol, price, quantity);
    }

    /**
     * Converts to a Json string.
     * @return Json string.
     */
    public String toJson() {
        return g.toJson(this);
    }

    /**
     * Converts Json string to a BuyOrder Object.
     * @param str order as Json string.
     * @return BuyOrder.
     */
    public static SellOrder fromJson(String str) {
        return g.fromJson(str, SellOrder.class);
    }

}
