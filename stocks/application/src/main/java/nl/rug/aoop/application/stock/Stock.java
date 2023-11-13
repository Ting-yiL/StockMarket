package nl.rug.aoop.application.stock;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import nl.rug.aoop.model.StockDataModel;

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

    public Stock(String symbol, String name, long sharesOutstanding, double initialPrice) {
        this.symbol = symbol;
        this.name = name;
        this.sharesOutstanding = sharesOutstanding;
        this.initialPrice = initialPrice;
        this.price = initialPrice;
    }

    public Stock() {}

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getSharesOutstanding() {
        return sharesOutstanding;
    }

    @Override
    public double getMarketCap() {
        return sharesOutstanding*price;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "\nSymbol: " + symbol + "\nName: " + name + "\nShares Outstanding: " +
                sharesOutstanding + "\nPrice: " + initialPrice + "\nMarket Cap: " + this.getSharesOutstanding();
    }
}
