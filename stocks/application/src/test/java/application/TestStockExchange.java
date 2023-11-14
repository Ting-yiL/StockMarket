package application;

import com.fasterxml.jackson.core.type.TypeReference;

import nl.rug.aoop.application.stock.Stock;
import nl.rug.aoop.application.stock.StockMap;
import nl.rug.aoop.application.stockExchange.StockExchangeData;
import nl.rug.aoop.model.StockDataModel;
import nl.rug.aoop.application.trader.StockPortfolio;
import nl.rug.aoop.application.trader.Trader;
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
        List<Trader> tradersList = yamlLoader2.load(new TypeReference<>() {});

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
    void TestGetTraderByIndex() {
        StockPortfolio bobPort = new StockPortfolio();
        bobPort.setOwnedShares(new HashMap<>());
        bobPort.getOwnedShares().put("NVDA", 3);
        bobPort.getOwnedShares().put("AMD", 23);
        bobPort.getOwnedShares().put("AAPL", 15);
        bobPort.getOwnedShares().put("ADBE", 1);
        bobPort.getOwnedShares().put("FB", 3);
        Trader bob = new Trader("bot1", "Just Bob", 10450, bobPort);

        assertEquals(bob.toString(), this.stockExchange.getTraderByIndex(0).toString());
    }
}
