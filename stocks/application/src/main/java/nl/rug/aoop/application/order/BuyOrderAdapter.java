package nl.rug.aoop.application.order;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * The BuyOrder Adapter.
 */
public class BuyOrderAdapter extends TypeAdapter<BuyOrder> {
    /**
     * The BuyOrder Adapter.
     */
    public static final String TRADER_FIELD = "Trader";
    private static final String STOCK_FIELD = "Stock";
    private static final String PRICE_FIELD = "Price";
    private static final String QUANTITY_FIELD = "Quantity";

    /**
     * {@inheritDoc}
     * @param writer The JsonWriter.
     * @param buyOrder The BuyOder.
     * @throws IOException The IOException.
     */
    @Override
    public void write(JsonWriter writer, BuyOrder buyOrder) throws IOException {
        writer.beginObject();
        writer.name(TRADER_FIELD);
        writer.value(buyOrder.getTraderID());
        writer.name(STOCK_FIELD);
        writer.value(buyOrder.getStockSymbol());
        writer.name(PRICE_FIELD);
        writer.value(buyOrder.getPrice());
        writer.name(QUANTITY_FIELD);
        writer.value(buyOrder.getQuantity());
        writer.endObject();
    }

    /**
     * {@inheritDoc}
     * @param reader JsonReader.
     * @return The BuyOrder.
     * @throws IOException IOException.
     */
    @Override
    public BuyOrder read(JsonReader reader) throws IOException {
        String traderID = "";
        String stockSymbol = "";
        double price = 0;
        int quantity = 0;

        reader.beginObject();
        while (reader.hasNext()) {
            String fieldName = reader.nextName();
            switch (fieldName) {
                case TRADER_FIELD -> {
                    traderID = reader.nextString();
                }
                case STOCK_FIELD -> {
                    stockSymbol = reader.nextString();
                }
                case PRICE_FIELD -> {
                    price = Double.parseDouble(reader.nextString());
                }
                case QUANTITY_FIELD -> {
                    quantity = Integer.parseInt(reader.nextString());
                }
                default -> reader.skipValue();
            }
        }
        reader.endObject();

        return new BuyOrder(traderID, stockSymbol, price, quantity);
    }
}

