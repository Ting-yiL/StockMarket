package nl.rug.aoop.application.order;

import com.google.gson.Gson;

/**
 * The BuyOrder.
 */
public class BuyOrder extends LimitOrder {
    private static final Gson GSON = new Gson();

    /**
     * The constructor of the buyOrder.
     * @param traderId The trader's ID.
     * @param stockSymbol The stock's symbol.
     * @param price The price of the stock that the trader wants to buy.
     * @param quantity The quantity of the stock that the trader wants to buy.
     */
    public BuyOrder(String traderId, String stockSymbol, double price, int quantity) {
        super(traderId, stockSymbol, price, quantity);
    }

    /**
     * Converts to a Json string.
     * @return Json string.
     */
    public String toJson() {
        return GSON.toJson(this);
    }

    /**
     * Converts Json string to a BuyOrder Object.
     * @param str order as Json string.
     * @return BuyOrder.
     */
    public static BuyOrder fromJson(String str) {
        return GSON.fromJson(str, BuyOrder.class);
    }
}
