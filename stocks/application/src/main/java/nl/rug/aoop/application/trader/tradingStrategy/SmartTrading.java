package nl.rug.aoop.application.trader.tradingStrategy;

import lombok.Getter;
import lombok.Setter;
import nl.rug.aoop.application.order.BuyOrder;
import nl.rug.aoop.application.order.SellOrder;
import nl.rug.aoop.application.stock.Stock;
import nl.rug.aoop.application.stock.StockMap;
import nl.rug.aoop.application.trader.DebtTracker;
import nl.rug.aoop.application.trader.StockPortfolio;
import nl.rug.aoop.application.trader.TraderData;

import java.util.List;
import java.util.Random;

/**
 * SmartTrading strategy.
 */
@Getter
public class SmartTrading implements Trading {
    @Setter
    private StockMap stockMap;
    @Setter
    private TraderData traderData;
    @Setter
    private DebtTracker debtTracker;

    /**
     * The constructor of SmartTrading.
     * @param stockMap The stockMap.
     * @param traderData The traderData.
     */
    public SmartTrading(StockMap stockMap, TraderData traderData, DebtTracker debtTracker) {
        this.stockMap = stockMap;
        this.traderData = traderData;
        this.debtTracker = debtTracker;
    }

    /**
     * Generates a random number from range with skewed-bias.
     * @param mean The mean.
     * @param margin The range from the mean.
     * @param skew The amount of skewness.
     * @param bias The direction of the skew.
     * @return A skewed-bounded double.
     */
    public int generateSkewedBoundedDouble(double mean, double margin, double skew, double bias) {
        Random random = new Random(System.currentTimeMillis());
        double max = mean + margin;
        double min = mean - margin;
        double range = max - min;
        double mid = min + range / 2.0;
        double unitGaussian = random.nextGaussian();
        double biasFactor = Math.exp(bias);
        return (int) ((int) mid+(range*(biasFactor/(biasFactor+Math.exp(-unitGaussian/skew))-0.5)));
    }

    /**
     * Randomly selecting a stock from stockMap.
     * @param stockMap The stockMap.
     * @return A stock.
     */
    public String randomSelectStock(StockMap stockMap) {
        Random generator = new Random();
        List<Object> values = List.of(stockMap.getStocks().keySet().toArray());
        return (String) values.get(generator.nextInt(values.size()));
    }

    /**
     * Randomly selecting a stock from stockPortfolio.
     * @param stockPortfolio The stockPortfolio.
     * @return A stock.
     */
    public String randomSelectStock(StockPortfolio stockPortfolio) {
        if (!stockPortfolio.getOwnedShares().isEmpty()) {
            Random generator = new Random();
            List<Object> values = List.of(stockPortfolio.getOwnedShares().keySet().toArray());
            return (String) values.get(generator.nextInt(values.size()));
        } else {
            return null;
        }
    }

    /**
     * Generating a buy price.
     * @param stock The stock.
     * @return The price.
     */
    public int generateBuyPrice(Stock stock) {
        return this.generateSkewedBoundedDouble(stock.getPrice(), stock.getPrice()/10, 0.25, -1);
    }

    /**
     * Generating a sell price.
     * @param stock The stock.
     * @return The price.
     */
    public int generateSellPrice(Stock stock) {
        return this.generateSkewedBoundedDouble(stock.getPrice(), stock.getPrice()/10, 0.25, 1);
    }

    /**
     * Generating a valid buy quantity.
     * @param buyPrice The buy price.
     * @param availFunds The available funds.
     * @return A valid buy quantity.
     */
    public int generateBuyQuantity(double buyPrice, double availFunds) {
        int maxQuantity = (int) (availFunds/buyPrice);
        int minQuantity = 1;
        return (int) (Math.random() * ( maxQuantity - minQuantity + 1)) + minQuantity;
    }

    /**
     * Generating a valid sell quantity of a stock from the stockPortfolio.
     * @param stockSymbol The stock symbol.
     * @param stockPortfolio The stockPortfolio.
     * @return A valid buy quantity.
     */
    public int generateSellQuantity(String stockSymbol, StockPortfolio stockPortfolio) {
        int maxQuantity = stockPortfolio.getOwnedShares().get(stockSymbol);
        int minQuantity = 1;
        return (int) (Math.random() * ( maxQuantity - minQuantity + 1)) + minQuantity;
    }

    /**
     * Generating a buyOrder.
     * @return The buyOrder.
     */
    @Override
    public BuyOrder generateBuyOrder() {
        String traderId = this.traderData.getId();
        String stockSymbol = this.randomSelectStock(this.stockMap);
        Stock stock = this.stockMap.getStocks().get(stockSymbol);
        double buyPrice = this.generateBuyPrice(stock);
        double availFunds = this.traderData.getFunds();
        if (this.debtTracker != null) {
            availFunds -= this.debtTracker.getFundDebt();
        }
        int quantity = this.generateBuyQuantity(buyPrice, availFunds);
        return new BuyOrder(traderId, stockSymbol, buyPrice, quantity);
    }

    /**
     * Generating a sellOrder.
     * @return The sellOrder.
     */
    @Override
    public SellOrder generateSellOrder() {
        if (this.traderData.getStockPortfolio() != null) {
            String traderId = this.traderData.getId();
            String stockSymbol = this.randomSelectStock(this.traderData.getStockPortfolio());
            Stock stock = this.stockMap.getStocks().get(stockSymbol);
            double sellPrice = this.generateSellPrice(stock);
            int quantity = this.generateSellQuantity(stockSymbol, this.traderData.getStockPortfolio());
            return new SellOrder(traderId, stockSymbol, sellPrice, quantity);
        }
        return null;
    }
}
