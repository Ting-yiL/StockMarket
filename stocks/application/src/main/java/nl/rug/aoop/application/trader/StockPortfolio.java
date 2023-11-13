package nl.rug.aoop.application.trader;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class StockPortfolio {
    private Map<String, Integer> ownedShares;
}
