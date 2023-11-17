package nl.rug.aoop.application.stock;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * The custom stock Deserializer.
 */
public class StockDeserializer extends StdDeserializer<Stock> {

    /**
     * The constructor of the Stock Deserializer.
     */
    public StockDeserializer() {
        this(null);
    }

    /**
     * The constructor of the Stock Deserializer.
     * @param vc the class that is deserialized.
     */
    public StockDeserializer(Class<?> vc) {
        super(vc);
    }

    /**
     * {@inheritDoc}
     * @param jp The JsonParser.
     * @param ctxt The deserialization Content.
     * @return The Stock.
     * @throws IOException The IOException.
     */
    @Override
    public Stock deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        String symbol = node.get("symbol").asText();
        String name = node.get("name").asText();
        long sharesOutstanding = node.get("sharesOutstanding").asLong();
        double initialPrice = node.get("initialPrice").asDouble();

        Stock stock = new Stock();
        stock.setSymbol(symbol);
        stock.setName(name);
        stock.setSharesOutstanding(sharesOutstanding);
        stock.setInitialPrice(initialPrice);
        stock.setPrice(initialPrice);

        return stock;
    }
}
