package nl.rug.aoop.application.order;

public class SellOrder extends LimitOrder{
    /**
     * The constructor of limitOrder.
     *
     * @param traderId    The trader's ID.
     * @param stockSymbol The stock's symbol.
     * @param price       The price of the stock.
     * @param quantity    The quantity of the stock.
     */
    public SellOrder(String traderId, String stockSymbol, double price, int quantity) {
        super(traderId, stockSymbol, price, quantity);
    }
}
