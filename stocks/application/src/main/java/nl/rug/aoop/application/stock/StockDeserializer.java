package nl.rug.aoop.application.stock;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class StockDeserializer extends StdDeserializer<Stock> {
    public StockDeserializer() {
        this(null);
    }

    public StockDeserializer(Class<?> vc) {
        super(vc);
    }

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
