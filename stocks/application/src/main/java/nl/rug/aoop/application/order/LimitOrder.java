package nl.rug.aoop.application.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class LimitOrder {
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
