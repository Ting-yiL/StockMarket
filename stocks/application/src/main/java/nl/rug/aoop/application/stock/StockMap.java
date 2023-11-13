package nl.rug.aoop.application.stock;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class StockMap {
    private Map<String, Stock> stocks;
}
