package nl.rug.aoop.application.trader;

import lombok.Getter;
import nl.rug.aoop.model.TraderDataModel;

import java.util.ArrayList;
import java.util.List;

public class Trader implements TraderDataModel {
    private String id;
    private String name;
    private double funds;
    @Getter
    private StockPortfolio stockPortfolio;

    public Trader(String id, String name, double funds, StockPortfolio stockPortfolio){
        this.id = id;
        this.name = name;
        this.funds = funds;
        this.stockPortfolio = stockPortfolio;
    }

    public Trader() {}

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getFunds() {
        return this.funds;
    }

    @Override
    public List<String> getOwnedStocks() {
        return new ArrayList<>(this.stockPortfolio.getOwnedShares().keySet());
    }

    @Override
    public String toString() {
        return "\nId: " + id + "\nName: " + name + "\nFunds: " + funds;
    }
}
