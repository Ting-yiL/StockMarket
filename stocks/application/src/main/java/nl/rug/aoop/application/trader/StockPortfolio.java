package nl.rug.aoop.application.trader;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.stock.Stock;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Slf4j
public class StockPortfolio {
    private Map<String, Integer> ownedShares;

    /**
     * Adds stock to the portfolio.
     * @param stock Stock to add.
     * @param shares Amount of shares of the stock which we are adding.
     */

    public void addStock(Stock stock, int shares) {
        String stockSymbol = stock.getSymbol();
        int originalShares = 0;
        if (ownedShares.containsKey(stockSymbol)) {
            originalShares = ownedShares.get(stockSymbol);
        }
        ownedShares.replace(stockSymbol, originalShares + shares);
        log.info("Added " + shares + " shares of " + stock.getSymbol());
    }

    /**
     * Removes stock from the portfolio.
     * @param stock Stock to remove.
     * @param shares Amount of shares to remove.
     */
    public void removeStock(Stock stock, int shares) {
        String stockSymbol = stock.getSymbol();
        int originalShares = 0;
        if (ownedShares.containsKey(stockSymbol)) {
            originalShares = ownedShares.get(stockSymbol);
        }
        int remainingShares =  originalShares + shares;
        if (remainingShares <= 0) {
            ownedShares.remove(stockSymbol);
        } else {
            ownedShares.replace(stock.getSymbol(), remainingShares);
        }
        log.info("Removed " + -1*shares + " shares of " + stock.getSymbol());
    }
}
