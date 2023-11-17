package nl.rug.aoop.application.stock;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import nl.rug.aoop.model.StockDataModel;

/**
 * The Stock class.
 */
@Setter
@JsonDeserialize(using = StockDeserializer.class)
public class Stock implements StockDataModel {
    private String symbol;
    private String name;
    private long sharesOutstanding;
    @Getter
    private double initialPrice;
    @Setter
    private double price;

    /**
     * The constructor of the stock class.
     * @param symbol The symbol of the stock.
     * @param name The name of the stock.
     * @param sharesOutstanding The shares Outstanding of the stock.
     * @param initialPrice The initial Price if the stock.
     */
    public Stock(String symbol, String name, long sharesOutstanding, double initialPrice) {
        this.symbol = symbol;
        this.name = name;
        this.sharesOutstanding = sharesOutstanding;
        this.initialPrice = initialPrice;
        this.price = initialPrice;
    }

    /**
     * The second constructor of the Stock class.
     */
    public Stock() {}

    /**
     * {@inheritDoc}
     * @return The symbol.
     */
    @Override
    public String getSymbol() {
        return symbol;
    }

    /**
     * {@inheritDoc}
     * @return The name.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     * @return The shares Outstanding.
     */
    @Override
    public long getSharesOutstanding() {
        return sharesOutstanding;
    }

    /**
     * {@inheritDoc}
     * @return The market Capital.
     */
    @Override
    public double getMarketCap() {
        return sharesOutstanding*price;
    }

    /**
     * {@inheritDoc}
     * @return The price.
     */
    @Override
    public double getPrice() {
        return price;
    }

    /**
     * {@inheritDoc}
     * @return The string version of the stock.
     */
    @Override
    public String toString() {
        return "\nSymbol: " + symbol + "\nName: " + name + "\nShares Outstanding: " +
                sharesOutstanding + "\nPrice: " + initialPrice + "\nMarket Cap: " + this.getMarketCap();
    }
}
