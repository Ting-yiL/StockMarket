package nl.rug.aoop.application.trader;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.model.TraderDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * TraderData class.
 */
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

    /**
     * Constructor.
     * @param id ID of the trader.
     * @param name Name of the trader.
     * @param funds Funds of the trader.
     * @param stockPortfolio Portfolio of the stocks of the trader.
     */
    public TraderData(String id, String name, double funds, StockPortfolio stockPortfolio){
        this.id = id;
        this.name = name;
        this.funds = funds;
        this.stockPortfolio = stockPortfolio;
    }

    /**
     * Second constructor.
     */
    public TraderData() {}

    /**
     * Get Id.
     * @return The trader's id.
     */
    @Override
    public String getId() {
        return this.id;
    }

    /**
     * Get Name.
     * @return The trader's name.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Get the amount of funds.
     * @return The trader's amount of funds.
     */
    @Override
    public double getFunds() {
        return this.funds;
    }

    /**
     * Get ownedStocks.
     * @return The trader's ownedStocks.
     */
    @Override
    public List<String> getOwnedStocks() {
        return new ArrayList<>(this.stockPortfolio.getOwnedShares().keySet());
    }

    /**
     * Converts to a string.
     * @return the string.
     */
    @Override
    public String toString() {
        return "\nId: " + id + "\nName: " + name + "\nFunds: " + funds;
    }

    /**
     * Converts to a Json string.
     * @return Json string.
     */
    public String toJson() {
        return GSON.toJson(this);
    }

    /**
     * Converts Json string to a trader Object.
     * @param json trader as Json string.
     * @return Trader.
     */
    public static TraderData fromJson(String json) {
        return GSON.fromJson(json, TraderData.class);
    }

}
