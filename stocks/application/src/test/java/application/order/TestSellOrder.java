package application.order;

import nl.rug.aoop.application.order.SellOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSellOrder {
    private SellOrder sellOrder;
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
        sellOrder = new SellOrder(traderId, stockSymbol, price, quantity);
    }

    @Test
    void toJson_ShouldReturnValidJson() {
        // Serialize BuyOrder to JSON
        String json = sellOrder.toJson();
        System.out.println(json);
    }

    @Test
    void fromJson_ShouldReturnEquivalentObject() {
        // Serialize the BuyOrder to JSON, then deserialize it back to an object
        String json = sellOrder.toJson();
        SellOrder deserializedSellOrder = SellOrder.fromJson(json);

        // Check if deserialized object matches the original
        assertEquals(sellOrder.getTraderID(), deserializedSellOrder.getTraderID());
        assertEquals(sellOrder.getStockSymbol(), deserializedSellOrder.getStockSymbol());
        assertEquals(sellOrder.getPrice(), deserializedSellOrder.getPrice());
        assertEquals(sellOrder.getQuantity(), deserializedSellOrder.getQuantity());
    }

    @Test
    void toJsonAndFromJson_ShouldBeReversible() {
        // Serialize to JSON and then deserialize back to an object
        String json = sellOrder.toJson();
        SellOrder deserializedSellOrder = SellOrder.fromJson(json);

        // Check if deserialization is reversible
        assertEquals(json, deserializedSellOrder.toJson());
    }
}
