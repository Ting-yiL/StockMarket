package application;

import com.fasterxml.jackson.core.type.TypeReference;

import nl.rug.aoop.application.order.BuyOrder;
import nl.rug.aoop.application.order.OrderStatus;
import nl.rug.aoop.application.order.SellOrder;
import nl.rug.aoop.application.stock.Stock;
import nl.rug.aoop.application.stock.StockMap;
import nl.rug.aoop.application.stockExchange.StockExchangeData;
import nl.rug.aoop.model.StockDataModel;
import nl.rug.aoop.application.trader.StockPortfolio;
import nl.rug.aoop.application.trader.TraderData;
import nl.rug.aoop.util.YamlLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestStockExchange {
    private final Path STOCKPATH = Path.of("C:\\Users\\ting\\IdeaProjects\\rug\\2023_Team_083\\stocks\\data\\stocks.yaml");
    private final Path TRADERPATH = Path.of("C:\\Users\\ting\\IdeaProjects\\rug\\2023_Team_083\\stocks\\data\\traders.yaml");
    private StockExchangeData stockExchange;

    @BeforeEach
    void SetUp() throws IOException {
        YamlLoader yamlLoader1 = new YamlLoader(STOCKPATH);
        YamlLoader yamlLoader2 = new YamlLoader(TRADERPATH);

        StockMap stocks = yamlLoader1.load(StockMap.class);
        List<TraderData> tradersList = yamlLoader2.load(new TypeReference<>() {});

        this.stockExchange = new StockExchangeData(stocks, tradersList);
    }

    @Test
    void TestStockExchangeConstructor() {
        assertNotNull(this.stockExchange);
    }

    @Test
    void TestGetStockByIndex() {
        StockDataModel nvda = new Stock(
                "NVDA",
                "NVIDIA Corporation",
                2496000000L,
                222.42
        );

        assertEquals(nvda.toString(), this.stockExchange.getStockByIndex(0).toString());
    }

    @Test
    void TestGetNumberOfStocks() {
        assertEquals(11, this.stockExchange.getNumberOfStocks());
    }

    @Test
    void TestGetTraderById() {
        StockPortfolio bobPort = new StockPortfolio();
        bobPort.setOwnedShares(new HashMap<>());
        bobPort.getOwnedShares().put("NVDA", 3);
        bobPort.getOwnedShares().put("AMD", 23);
        bobPort.getOwnedShares().put("AAPL", 15);
        bobPort.getOwnedShares().put("ADBE", 1);
        bobPort.getOwnedShares().put("FB", 3);
        TraderData bob = new TraderData("bot1", "Just Bob", 10450, bobPort);

        assertEquals(bob.toString(), this.stockExchange.getTraderById("bot1").toString());
    }

    @Test
    void TestAddBids() {
        BuyOrder buyOrder = new BuyOrder("bot1", "ABC", 10, 10);
        this.stockExchange.addBids(buyOrder);
    }

    @Test
    void TestAddAsks() {
        SellOrder sellOrder = new SellOrder("bot1", "ABC", 10, 10);
        this.stockExchange.addAsks(sellOrder);
    }

    @Test
    void TestMatchBuyOrderWhenNotMatched() {
        BuyOrder buyOrder = new BuyOrder("bot1", "ABC", 10, 10);
        this.stockExchange.matchBuyOrder(buyOrder);
    }

    @Test
    void TestMatchSellOrderWhenNotMatched() {
        SellOrder sellOrder = new SellOrder("bot1", "ABC", 10, 10);
        this.stockExchange.matchSellOrder(sellOrder);
    }

    @Test
    void TestMatchBuyOrderAndSellOrder() {
        BuyOrder buyOrder1 = new BuyOrder("bot1", "FB", 10, 10);
        BuyOrder buyOrder2 = new BuyOrder("bot1", "FB", 9, 10);
        BuyOrder buyOrder3 = new BuyOrder("bot1", "FB", 8, 10);
        SellOrder sellOrder1 = new SellOrder("bot2", "FB", 10, 10);
        SellOrder sellOrder2 = new SellOrder("bot2", "FB", 9, 10);
        SellOrder sellOrder3 = new SellOrder("bot2", "FB", 8, 10);
        this.stockExchange.matchBuyOrder(buyOrder1);
        this.stockExchange.matchBuyOrder(buyOrder2);
        this.stockExchange.matchSellOrder(sellOrder1);
        this.stockExchange.matchSellOrder(sellOrder3);
        this.stockExchange.matchBuyOrder(buyOrder3);
        this.stockExchange.matchSellOrder(sellOrder2);
    }

    @Test
    void TestResolveTrades() {
        BuyOrder buyOrder= new BuyOrder("bot7", "FB", 500, 100);
        SellOrder sellOrder = new SellOrder("bot2", "FB", 500, 100);
        int bot2InitialShares = this.stockExchange.getTraderById("bot2").getStockPortfolio().getOwnedShares().get("FB");
        System.out.println("bot2 initial FB shares: " + bot2InitialShares);
        double bot2InitialFunds = this.stockExchange.getTraderById("bot2").getFunds();
        System.out.println("bot2 initial funds: " + bot2InitialFunds);
        int bot7InitialShares = this.stockExchange.getTraderById("bot7").getStockPortfolio().getOwnedShares().get("FB");
        System.out.println("bot7 initial FB shares: " + bot7InitialShares);
        double bot7InitialFunds = this.stockExchange.getTraderById("bot7").getFunds();
        System.out.println("bot7 initial funds: " + bot7InitialFunds);

        this.stockExchange.resolveTrades(buyOrder, sellOrder, OrderStatus.BUY);
        int bot2FinalShares = this.stockExchange.getTraderById("bot2").getStockPortfolio().getOwnedShares().get("FB");
        System.out.println("bot2 final FB shares: " + bot2FinalShares);
        double bot2FinalFunds = this.stockExchange.getTraderById("bot2").getFunds();
        System.out.println("bot2 final funds: " + bot2FinalFunds);
        int bot7FinalShares = this.stockExchange.getTraderById("bot7").getStockPortfolio().getOwnedShares().get("FB");
        System.out.println("bot7 final FB shares: " + bot7FinalShares);
        double bot7FinalFunds = this.stockExchange.getTraderById("bot7").getFunds();
        System.out.println("bot7 final funds: " + bot7FinalFunds);

    }
}
