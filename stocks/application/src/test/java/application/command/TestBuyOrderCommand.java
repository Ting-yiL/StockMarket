package application.command;

import com.fasterxml.jackson.core.type.TypeReference;
import nl.rug.aoop.application.stockExchange.STXManager;
import nl.rug.aoop.application.stockExchange.command.BuyOrderCommand;
import nl.rug.aoop.application.order.BuyOrder;
import nl.rug.aoop.application.stock.StockMap;
import nl.rug.aoop.application.stockExchange.StockExchangeData;
import nl.rug.aoop.application.trader.TraderData;
import nl.rug.aoop.util.YamlLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBuyOrderCommand {
    private final Path STOCKPATH = Path.of("C:\\Users\\ting\\IdeaProjects\\rug\\2023_Team_083\\stocks\\data\\stocks.yaml");
    private final Path TRADERPATH = Path.of("C:\\Users\\ting\\IdeaProjects\\rug\\2023_Team_083\\stocks\\data\\traders.yaml");
    private StockExchangeData stockExchange;
    private BuyOrderCommand command;
    private Map<String, Object> params;;
    private BuyOrder buyOrder;
    private String buyOrderJson;
    private STXManager stxManager = Mockito.mock(STXManager.class);

    @BeforeEach
    void SetUp() throws IOException {
        YamlLoader yamlLoader1 = new YamlLoader(STOCKPATH);
        YamlLoader yamlLoader2 = new YamlLoader(TRADERPATH);

        StockMap stocks = yamlLoader1.load(StockMap.class);
        List<TraderData> tradersList = yamlLoader2.load(new TypeReference<>() {});

        this.stockExchange = new StockExchangeData(stocks, tradersList);

        this.buyOrder = new BuyOrder("bot1", "NVDA", 10, 10);
        this.buyOrderJson = buyOrder.toJson();
        this.params = new HashMap<>();
        this.params.put("info", buyOrderJson);
        this.command = new BuyOrderCommand(this.stockExchange, this.stxManager);
    }

    @Test
    void TestNoMatchBuyOrder() {
        this.command.execute(this.params);
    }

    @Test
    void TestMatchBuyOrder(){
        this.command.execute(this.params);
    }
}
