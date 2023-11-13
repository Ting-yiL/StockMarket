package nl.rug.aoop.application;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.initialization.SimpleViewFactory;
import nl.rug.aoop.application.stockExchange.StockExchangeData;
import nl.rug.aoop.application.stock.StockMap;
import nl.rug.aoop.application.trader.Trader;
import nl.rug.aoop.util.YamlLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Slf4j
public class StockApplication {
    private final Path STOCKPATH = Path.of("stocks","data", "stocks.yaml");
    private final Path TRADERPATH = Path.of("stocks","data", "traders.yaml");
    private StockExchangeData stockExchange;
    public static void main(String[] args) {
        StockApplication app = new StockApplication();
        app.run();
    }

    private void setUpServer() {}

    private void setUpUI(SimpleViewFactory stockView) {
        stockView.createView(this.stockExchange);
    }

    private void loadStockExchangeData(Path stockPath, Path tradePath) throws IOException {
        YamlLoader yamlLoader1 = new YamlLoader(stockPath);
        YamlLoader yamlLoader2 = new YamlLoader(tradePath);

        List<Trader> tradersList;
        StockMap stocks;
        stocks = yamlLoader1.load(StockMap.class);
        tradersList = yamlLoader2.load(new TypeReference<>() {});
        this.stockExchange = new StockExchangeData(stocks, tradersList);
    }

    private void run() {
        try {
            this.loadStockExchangeData(this.STOCKPATH, this.TRADERPATH);
            this.setUpUI(new SimpleViewFactory());
        } catch (IOException e) {
            log.error("Cannot Create Stock Exchange View due to" + e, e);
        }
    }
}
