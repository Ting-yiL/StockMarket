package nl.rug.aoop.application.stockExchange;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.order.BuyOrder;
import nl.rug.aoop.application.order.OrderStatus;
import nl.rug.aoop.application.order.SellOrder;
import nl.rug.aoop.application.order.comparator.BuyOrderComparator;
import nl.rug.aoop.application.order.comparator.SellOrderComparator;
import nl.rug.aoop.application.stock.Stock;
import nl.rug.aoop.application.stock.StockMap;
import nl.rug.aoop.application.trader.TraderData;
import nl.rug.aoop.model.StockDataModel;
import nl.rug.aoop.model.StockExchangeDataModel;
import nl.rug.aoop.model.TraderDataModel;

import java.util.*;

@Slf4j
public class StockExchangeData implements StockExchangeDataModel {
    @Getter
    private final StockMap stocks;
    private final Map<String, TraderData> traders = new HashMap<>();
    private final Map<Stock, PriorityQueue<BuyOrder>> bids = new HashMap<>();
    private final Map<Stock, PriorityQueue<SellOrder>> asks = new HashMap<>();


    public StockExchangeData(StockMap stocks, List<TraderData> traders) {
        this.stocks = stocks;
        for (TraderData trader : traders) {
            this.traders.put(trader.getId(), trader);
        }
        for (Stock stock : stocks.getStocks().values()) {
            bids.put(stock, new PriorityQueue<>(new BuyOrderComparator()));
            asks.put(stock, new PriorityQueue<>(new SellOrderComparator()));
        }
    }

    @Override
    public StockDataModel getStockByIndex(int index) {
        List<String> stockList = new ArrayList<>(this.stocks.getStocks().keySet());
        String stockName = stockList.get(index);
        return this.stocks.getStocks().get(stockName);
    }

    @Override
    public int getNumberOfStocks() {
        return this.stocks.getStocks().size();
    }

    @Override
    public TraderDataModel getTraderByIndex(int index) {
        List<String> traderList = new ArrayList<>(this.traders.keySet());
        String tradeID = traderList.get(index);
        return this.traders.get(tradeID);
    }

    @Override
    public int getNumberOfTraders() {
        return this.traders.size();
    }

    public Stock getStockBySymbol(String stockSymbol) {
        return this.stocks.getStocks().get(stockSymbol);
    }

    public TraderData getTraderById(String traderId) {
        return this.traders.get(traderId);
    }

    public void addBids(BuyOrder order) {
        Stock orderStock = this.getStockBySymbol(order.getStockSymbol());
        if(!this.bids.containsKey(orderStock)) {
            log.info("Invalid Stock Symbol");
            return;
        }
        this.bids.get(orderStock).add(order);
        log.info("Buy Order added to bids!");
    }

    public void addAsks(SellOrder order) {
        Stock orderStock = this.getStockBySymbol(order.getStockSymbol());
        if(!this.asks.containsKey(orderStock)) {
            log.info("Invalid Stock Symbol");
            return;
        }
        this.asks.get(orderStock).add(order);
        log.info("Sell Order added to asks!");
    }

    public SellOrder matchOrderFromAsks(BuyOrder buyOrder) {
        SellOrder matchedOrder = null;
        Stock orderStock = this.getStockBySymbol(buyOrder.getStockSymbol());
        if (this.asks.containsKey(orderStock)) {
            SellOrder headOrder = this.asks.get(orderStock).peek();
            if (headOrder != null && headOrder.getPrice()<=buyOrder.getPrice()) {
                matchedOrder = headOrder;
            }
        }
        return matchedOrder;
    }

    public BuyOrder matchOrderFromBids(SellOrder sellOrder) {
        BuyOrder matchedOrder = null;
        Stock orderStock = this.getStockBySymbol(sellOrder.getStockSymbol());
        if (this.bids.containsKey(orderStock)) {
            BuyOrder headOrder = this.bids.get(orderStock).peek();
            if (headOrder != null && headOrder.getPrice()>=sellOrder.getPrice()) {
                matchedOrder = headOrder;
            }
        }
        return matchedOrder;
    }

    public boolean validateBuyOrder(BuyOrder buyOrder) {
        TraderData trader = this.getTraderById(buyOrder.getTraderID());
        return !(trader.getFunds() < buyOrder.getPrice() * buyOrder.getQuantity());
    }

    public boolean validateSellOrder(SellOrder sellOrder) {
        TraderData trader = this.getTraderById(sellOrder.getTraderID());
        if (trader.getStockPortfolio().getOwnedShares().containsKey(sellOrder.getStockSymbol())){
            int ownedShares = trader.getStockPortfolio().getOwnedShares().get(sellOrder.getStockSymbol());
            return !(ownedShares<sellOrder.getQuantity());
        }
        return false;
    }

    public Map<String, Object> matchBuyOrder(BuyOrder buyOrder) {
        Map<String, Object> matchingInfo = null;
        if (validateBuyOrder(buyOrder)) {
            matchingInfo = new HashMap<>();
            log.info("Matching buy order");
            SellOrder sellOrder = this.matchOrderFromAsks(buyOrder);
            if (sellOrder == null || sellOrder.getTraderID().equals(buyOrder.getTraderID())) {
                log.info("Buy Order not matched!");
                this.addBids(buyOrder);
                matchingInfo.put("matching status", false);
            } else {
                log.info("Order matched");
                this.resolveTrades(buyOrder, sellOrder, OrderStatus.BUY);
                matchingInfo.put("matching status", true);
                matchingInfo.put("buyer Id", buyOrder.getTraderID());
                matchingInfo.put("seller Id", sellOrder.getTraderID());
            }
        } else {
            log.info("Invalid Order");
        }
        return matchingInfo;
    }

    public Map<String, Object> matchSellOrder(SellOrder sellOrder) {
        Map<String, Object> matchingInfo = null;
        if (validateSellOrder(sellOrder)) {
            matchingInfo = new HashMap<>();
            log.info("Matching sell order");
            BuyOrder buyOrder = this.matchOrderFromBids(sellOrder);
            if (buyOrder == null || sellOrder.getTraderID().equals(buyOrder.getTraderID())) {
                log.info("Sell Order not matched!");
                this.addAsks(sellOrder);
                matchingInfo.put("matching status", false);
            } else {
                log.info("Order matched");
                this.resolveTrades(buyOrder, sellOrder, OrderStatus.SELL);
                matchingInfo.put("matching status", true);
                matchingInfo.put("buyer Id", buyOrder.getTraderID());
                matchingInfo.put("seller Id", sellOrder.getTraderID());
            }
        } else {
            log.info("Invalid Order");
        }
        return matchingInfo;
    }

    public void updateStockPrice(Stock stock, double buyPrice){
        stock.setPrice(buyPrice);
        stocks.setStockPrice(stock);
        log.info(stock.getSymbol() + " price updated");
    }

    public void updateTraderPortfolio(TraderData trader, Stock stock, int shares, double funds) {
        String traderID = trader.getId();
        if (funds >= 0) {
            traders.get(traderID).setFunds(trader.getFunds() + funds);
            traders.get(traderID).getStockPortfolio().removeStock(stock, -1*shares);
        } else {
            traders.get(traderID).setFunds(trader.getFunds() + funds);
            traders.get(traderID).getStockPortfolio().addStock(stock,shares);
        }
        log.info("Trader " + traderID + "'s portfolio updated");
    }

    public void resolveTrades(BuyOrder buyOrder, SellOrder sellOrder, OrderStatus status) {
        log.info("resolving trades");
        double buyPrice = buyOrder.getPrice();
        Stock stock = this.getStockBySymbol(buyOrder.getStockSymbol());
        switch (status) {
            case SELL:
                this.resolveSellTrades(buyOrder, sellOrder);
                break;
            case BUY:
                this.resolveBuyTrades(buyOrder, sellOrder);
                break;
        }
        this.updateStockPrice(stock, buyPrice);
    }

    public void resolveBuyTrades(BuyOrder buyOrder, SellOrder sellOrder) {
        TraderData buyer = this.getTraderById(buyOrder.getTraderID());
        double buyPrice = buyOrder.getPrice();
        int buyQuantity = buyOrder.getQuantity();

        TraderData seller = this.getTraderById(sellOrder.getTraderID());
        double sellPrice = sellOrder.getPrice();
        int sellQuantity = sellOrder.getQuantity();

        int remainingQuantity = buyQuantity - sellQuantity;
        Stock stock = this.getStockBySymbol(buyOrder.getStockSymbol());

        if (remainingQuantity > 0) {
            this.asks.get(stock).poll();
            buyOrder.setQuantity(remainingQuantity);
            this.updateTraderPortfolio(buyer, stock, sellQuantity, -1*buyPrice*sellQuantity);
            this.updateTraderPortfolio(seller, stock, sellQuantity, sellPrice*sellQuantity);
            this.addBids(buyOrder);
        } else {
            sellOrder.setQuantity(-1*remainingQuantity);
            this.updateTraderPortfolio(buyer, stock, buyQuantity, -1*buyPrice*buyQuantity);
            this.updateTraderPortfolio(seller, stock, buyQuantity, sellPrice*buyQuantity);
        }
    }

    public void resolveSellTrades(BuyOrder buyOrder, SellOrder sellOrder) {
        TraderData buyer = this.getTraderById(buyOrder.getTraderID());
        double buyPrice = buyOrder.getPrice();
        int buyQuantity = buyOrder.getQuantity();

        TraderData seller = this.getTraderById(sellOrder.getTraderID());
        double sellPrice = sellOrder.getPrice();
        int sellQuantity = sellOrder.getQuantity();

        int remainingQuantity = buyQuantity - sellQuantity;
        Stock stock = this.getStockBySymbol(buyOrder.getStockSymbol());
        if (remainingQuantity > 0) {
            buyOrder.setQuantity(remainingQuantity);
            this.updateTraderPortfolio(buyer, stock, sellQuantity, -1*buyPrice*sellQuantity);
            this.updateTraderPortfolio(seller, stock, -1*sellQuantity, sellPrice*sellQuantity);
        } else {
            this.bids.get(stock).poll();
            sellOrder.setQuantity(-1*remainingQuantity);
            this.updateTraderPortfolio(buyer, stock, buyQuantity, -1*buyPrice*buyQuantity);
            this.updateTraderPortfolio(seller, stock, -1*buyQuantity, 1*sellPrice*buyQuantity);
            this.addAsks(sellOrder);
        }
    }
}
