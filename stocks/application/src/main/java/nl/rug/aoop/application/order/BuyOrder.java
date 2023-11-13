package nl.rug.aoop.application.order;

public class BuyOrder extends LimitOrder {
    /**
     * The constructor of limitOrder.
     *
     * @param traderId    The trader's ID.
     * @param stockSymbol The stock's symbol.
     * @param price       The price of the stock.
     * @param quantity    The quantity of the stock.
     */
    public BuyOrder(String traderId, String stockSymbol, double price, int quantity) {
        super(traderId, stockSymbol, price, quantity);
    }
}
