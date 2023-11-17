package nl.rug.aoop.application.trader.command;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.stock.StockMap;
import nl.rug.aoop.application.trader.TraderClient;
import nl.rug.aoop.command.Command;

import java.util.Map;

/**
 * The UpdateStockMapCommand.
 */
@Slf4j
public class UpdateStockMapCommand implements Command {
    private TraderClient traderClient;

    /**
     * The constructor of UpdateStockMapCommand.
     * @param traderClient The traderClient.
     */
    public UpdateStockMapCommand(TraderClient traderClient) {
        this.traderClient = traderClient;
    }

    /**
     * Execute the command.
     * @param params parameter map that consists of key-value pairs similar to JSON.
     */
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
