package nl.rug.aoop.application.trader.command;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.stock.StockMap;
import nl.rug.aoop.application.trader.TraderClient;
import nl.rug.aoop.application.trader.TraderData;
import nl.rug.aoop.command.Command;

import java.util.Map;

@Slf4j
public class UpdateStockMapCommand implements Command {
    private TraderClient traderClient;

    public UpdateStockMapCommand(TraderClient traderClient) {
        this.traderClient = traderClient;
    }

    @Override
    public void execute(Map<String, Object> params) {
        log.info("Handling UpdateStockMap command...");
        if (!params.isEmpty()) {
            if (params.containsKey("body")) {
                String stockMapJson = (String) params.get("body");
                StockMap stockMap = StockMap.fromJson(stockMapJson);
                this.traderClient.setStockMap(stockMap);
                log.info("Trader data updated");
            } else {
                log.info("No trader info");
            }
        } else {
            log.info("Command params is empty");
        }
    }
}
