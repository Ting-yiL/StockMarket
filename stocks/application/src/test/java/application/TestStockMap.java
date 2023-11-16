package application;

import nl.rug.aoop.application.stock.Stock;
import nl.rug.aoop.application.stock.StockMap;
import nl.rug.aoop.util.YamlLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestStockMap {
    private final Path STOCKPATH = Path.of("C:\\Users\\ting\\IdeaProjects\\rug\\2023_Team_083\\stocks\\data\\stocks.yaml");
    private StockMap stocks;


    @BeforeEach
    void SetUp() throws IOException {
        YamlLoader yamlLoader = new YamlLoader(STOCKPATH);
        this.stocks = yamlLoader.load(StockMap.class);
    }

    @Test
    void TestStocksConstructor() {
        assertNotNull(this.stocks);
    }

    @Test
    void TestGetStocksSize() {
        assertEquals(11, this.stocks.getStocks().size());
    }

    @Test
    void TestGetStockInfo() {
        Stock nvda = this.stocks.getStocks().get("NVDA");
        assertEquals("NVDA", nvda.getSymbol());
        assertEquals("NVIDIA Corporation", nvda.getName());
        assertEquals(2496000000L, nvda.getSharesOutstanding());
        assertEquals(222.42, nvda.getInitialPrice());
    }

    @Test
    void TestToJson() {
        System.out.println(this.stocks.toJson());
    }
}
