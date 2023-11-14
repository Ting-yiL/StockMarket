package application.order;

import nl.rug.aoop.application.order.BuyOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestBuyOrder {
    private BuyOrder buyOrder;
    private String traderId;
    private String stockSymbol;
    private double price;
    private int quantity;

    @BeforeEach
    void setUp() {
        // Setup data for the tests
        traderId = "Trader123";
        stockSymbol = "AAPL";
        price = 150.0;
        quantity = 10;

        // Initialize BuyOrder object
        buyOrder = new BuyOrder(traderId, stockSymbol, price, quantity);
    }

    @Test
    void toJson_ShouldReturnValidJson() {
        // Serialize BuyOrder to JSON
        String json = buyOrder.toJson();

        // Check if JSON string contains the necessary fields with the correct keys
        assertTrue(json.contains("\"Trader\":\"" + traderId + "\""));
        assertTrue(json.contains("\"Stock\":\"" + stockSymbol + "\""));
        assertTrue(json.contains("\"Price\":" + price));
        assertTrue(json.contains("\"Quantity\":" + quantity));
    }

    @Test
    void fromJson_ShouldReturnEquivalentObject() {
        // Serialize the BuyOrder to JSON, then deserialize it back to an object
        String json = buyOrder.toJson();
        BuyOrder deserializedBuyOrder = BuyOrder.fromJson(json);

        // Check if deserialized object matches the original
        assertEquals(buyOrder.getTraderID(), deserializedBuyOrder.getTraderID());
        assertEquals(buyOrder.getStockSymbol(), deserializedBuyOrder.getStockSymbol());
        assertEquals(buyOrder.getPrice(), deserializedBuyOrder.getPrice());
        assertEquals(buyOrder.getQuantity(), deserializedBuyOrder.getQuantity());
    }

    @Test
    void toJsonAndFromJson_ShouldBeReversible() {
        // Serialize to JSON and then deserialize back to an object
        String json = buyOrder.toJson();
        BuyOrder deserializedBuyOrder = BuyOrder.fromJson(json);

        // Check if deserialization is reversible
        assertEquals(json, deserializedBuyOrder.toJson());
    }
}
