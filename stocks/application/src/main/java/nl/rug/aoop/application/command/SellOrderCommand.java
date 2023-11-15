package nl.rug.aoop.application.command;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.order.SellOrder;
import nl.rug.aoop.application.stockExchange.StockExchangeData;
import nl.rug.aoop.command.Command;

import java.util.Map;

@Slf4j
public class SellOrderCommand implements Command {
    private StockExchangeData stockExchangeData;

    public SellOrderCommand(StockExchangeData stockExchangeData) {
        this.stockExchangeData = stockExchangeData;
    }

    @Override
    public void execute(Map<String, Object> params) {
        log.info("Handling SellOrder command...");
        if (!params.isEmpty()) {
            if (params.containsKey("info")) {
                String sellOrderJson = (String) params.get("Info");
                SellOrder sellOrder = SellOrder.fromJson(sellOrderJson);
                this.stockExchangeData.matchSellOrder(sellOrder);
            } else {
                log.info("No order info");
            }
        } else {
            log.info("Command params is empty");
        }
    }
}
