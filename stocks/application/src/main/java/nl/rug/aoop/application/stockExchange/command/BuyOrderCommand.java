package nl.rug.aoop.application.stockExchange.command;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.order.BuyOrder;
import nl.rug.aoop.application.stockExchange.StockExchangeData;
import nl.rug.aoop.command.Command;

import java.util.Map;

@Slf4j
public class BuyOrderCommand implements Command {
    private StockExchangeData stockExchangeData;

    public BuyOrderCommand(StockExchangeData stockExchangeData) {
        this.stockExchangeData = stockExchangeData;
    }

    @Override
    public void execute(Map<String, Object> params) {
        log.info("Handling BuyOrder command...");
        if (!params.isEmpty()) {
            if (params.containsKey("info")) {
                String buyOrderJson = (String) params.get("info");
                BuyOrder buyOrder = BuyOrder.fromJson(buyOrderJson);
                this.stockExchangeData.matchBuyOrder(buyOrder);
            } else {
                log.info("No order info");
            }
        } else {
            log.info("Command params is empty");
        }
    }
}
