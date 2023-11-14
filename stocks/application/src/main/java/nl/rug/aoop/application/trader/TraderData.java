package nl.rug.aoop.application.trader;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.model.TraderDataModel;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Setter
@Getter
public class TraderData implements TraderDataModel {
    private static final Gson GSON = new Gson();
    private String id;
    private String name;
    private double funds;
    @Getter
    private StockPortfolio stockPortfolio;

    public TraderData(String id, String name, double funds, StockPortfolio stockPortfolio){
        this.id = id;
        this.name = name;
        this.funds = funds;
        this.stockPortfolio = stockPortfolio;
    }

    public TraderData() {}

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

    public String toJson() {
        return GSON.toJson(this);
    }

    public static TraderData fromJson(String json) {
        return GSON.fromJson(json, TraderData.class);
    }

}
