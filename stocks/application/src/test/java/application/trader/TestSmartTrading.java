package application.trader;

import nl.rug.aoop.application.stock.StockMap;
import nl.rug.aoop.application.trader.StockPortfolio;
import nl.rug.aoop.application.trader.TraderData;
import nl.rug.aoop.application.trader.tradingStrategy.SmartTrading;
import nl.rug.aoop.util.YamlLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

public class TestSmartTrading {
    private SmartTrading smartTrading;
    private final Path STOCKPATH = Path.of("C:\\Users\\ting\\IdeaProjects\\rug\\2023_Team_083\\stocks\\data\\stocks.yaml");
    private StockMap stocks;
    private TraderData traderData;

    @BeforeEach
    void SetUp() throws IOException {

        YamlLoader yamlLoader = new YamlLoader(STOCKPATH);
        this.stocks = yamlLoader.load(StockMap.class);

        StockPortfolio stockPortfolio = new StockPortfolio();
        stockPortfolio.getOwnedShares().put("NVDA", 500);
        stockPortfolio.getOwnedShares().put("AMD", 500);
        stockPortfolio.getOwnedShares().put("AAPL", 500);

        this.traderData = new TraderData("bot1", "Julia", 5000, stockPortfolio);

        this.smartTrading = new SmartTrading(this.stocks, this.traderData);
    }

    @Test
    void TestGenerateSkewedBoundedDouble() {
        System.out.println(this.smartTrading.generateSkewedBoundedDouble(500, 5, 0.5, -1));
    }

    @Test
    void TestRandomSelectStock() {
        System.out.println(this.smartTrading.randomSelectStock(this.stocks));
    }

    @Test
    void TestGenerateQuantity() {
        System.out.println(this.smartTrading.generateBuyQuantity(500, 5000));
    }

    @Test
    void TestGenerateBuyOrder() {
        System.out.println(this.smartTrading.generateBuyOrder().toJson());
    }

    @Test
    void TestGenerateSellOrder() {
        System.out.println(this.smartTrading.generateSellOrder().toJson());
    }
}
