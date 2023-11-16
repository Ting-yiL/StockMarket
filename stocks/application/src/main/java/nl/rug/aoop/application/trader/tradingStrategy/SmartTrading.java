package nl.rug.aoop.application.trader.tradingStrategy;

import lombok.Getter;
import lombok.Setter;
import nl.rug.aoop.application.order.BuyOrder;
import nl.rug.aoop.application.order.SellOrder;
import nl.rug.aoop.application.stock.Stock;
import nl.rug.aoop.application.stock.StockMap;
import nl.rug.aoop.application.trader.StockPortfolio;
import nl.rug.aoop.application.trader.TraderData;

import java.util.List;
import java.util.Random;

@Setter
@Getter
public class SmartTrading implements Trading {
    private StockMap stockMap;
    private TraderData traderData;

    public SmartTrading(StockMap stockMap, TraderData traderData) {
        this.stockMap = stockMap;
        this.traderData = traderData;
    }

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

    public String randomSelectStock(StockMap stockMap) {
        Random generator = new Random();
        List<Object> values = List.of(stockMap.getStocks().keySet().toArray());
        return (String) values.get(generator.nextInt(values.size()));
    }

    public String randomSelectStock(StockPortfolio stockPortfolio) {
        if (!stockPortfolio.getOwnedShares().isEmpty()) {
            Random generator = new Random();
            List<Object> values = List.of(stockPortfolio.getOwnedShares().keySet().toArray());
            return (String) values.get(generator.nextInt(values.size()));
        } else {
            return null;
        }
    }

    public int generateBuyPrice(Stock stock) {
        return this.generateSkewedBoundedDouble(stock.getPrice(), stock.getPrice()/5, 0.5, -1);
    }

    public int generateSellPrice(Stock stock) {
        return this.generateSkewedBoundedDouble(stock.getPrice(), stock.getPrice()/5, 0.5, 1);
    }

    public int generateBuyQuantity(double buyPrice, double availFunds) {
        int maxQuantity = (int) (availFunds/buyPrice);
        int minQuantity = 1;
        return (int) (Math.random() * ( maxQuantity - minQuantity + 1)) + minQuantity;
    }

    public int generateSellQuantity(String stockSymbol, StockPortfolio stockPortfolio) {
        int maxQuantity = stockPortfolio.getOwnedShares().get(stockSymbol);
        int minQuantity = 1;
        return (int) (Math.random() * ( maxQuantity - minQuantity + 1)) + minQuantity;
    }

    @Override
    public BuyOrder generateBuyOrder() {
        String traderId = this.traderData.getId();
        String stockSymbol = this.randomSelectStock(this.stockMap);
        Stock stock = this.stockMap.getStocks().get(stockSymbol);
        double buyPrice = this.generateBuyPrice(stock);
        int quantity = this.generateBuyQuantity(buyPrice, this.traderData.getFunds());
        return new BuyOrder(traderId, stockSymbol, buyPrice, quantity);
    }

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
