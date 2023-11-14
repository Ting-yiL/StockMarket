package application.trader;

import nl.rug.aoop.application.trader.StockPortfolio;
import nl.rug.aoop.application.trader.TraderData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTrader {
    private TraderData trader;

    @BeforeEach
    void setUp() {
        StockPortfolio portfolio = new StockPortfolio();
        trader = new TraderData("1", "Trader Joe", 1000.00, portfolio);
    }

    @Test
    void toJson_ShouldReturnValidJson() {
        // Serialize BuyOrder to JSON
        String json = trader.toJson();

        System.out.println(json);
    }

    @Test
    void fromJson_ShouldReturnEquivalentObject() {

        // Serialize the BuyOrder to JSON, then deserialize it back to an object
        String json = trader.toJson();
        TraderData deserializedTrader = TraderData.fromJson(json);

        // Check if deserialized object matches the original
        assertEquals(trader.getFunds(), deserializedTrader.getFunds());
        assertEquals(trader.getId(), deserializedTrader.getId());
        assertEquals(trader.getName(), deserializedTrader.getName());
    }

    @Test
    void toJsonAndFromJson_ShouldBeReversible() {
        // Serialize to JSON and then deserialize back to an object
        String json = trader.toJson();
        TraderData deserializedTraderData = TraderData.fromJson(json);

        // Check if deserialization is reversible
        assertEquals(json, deserializedTraderData.toJson());
    }
}
