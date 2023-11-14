package application;

import com.fasterxml.jackson.core.type.TypeReference;
import nl.rug.aoop.application.trader.TraderData;
import nl.rug.aoop.util.YamlLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestTraderList {
    private final Path TRADERPATH = Path.of("C:\\Users\\ting\\IdeaProjects\\rug\\2023_Team_083\\stocks\\data\\traders.yaml");
    private YamlLoader yamlLoader;
    private List<TraderData> tradersList;

    @BeforeEach
    void SetUp() throws IOException {
        this.yamlLoader = new YamlLoader(TRADERPATH);
        this.tradersList = this.yamlLoader.load(new TypeReference<List<TraderData>>() {});
    }

    @Test
    void TestTraderConstructor() {
        assertNotNull(this.yamlLoader);
    }

    @Test
    void TestGetStocksSize() {
        assertEquals(9, this.tradersList.size());
    }

    @Test
    void TestGetTraderInfo() {
        List<String> expectedList = new ArrayList<>(Arrays.asList("NVDA", "AMD", "AAPL", "ADBE", "FB"));

        TraderData trader1 = this.tradersList.get(0);
        assertEquals("bot1", trader1.getId());
        assertEquals("Just Bob", trader1.getName());
        assertEquals(expectedList, trader1.getOwnedStocks());
    }
}
