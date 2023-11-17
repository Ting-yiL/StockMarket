package nl.rug.aoop.application.stockExchange.command;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.order.SellOrder;
import nl.rug.aoop.application.stockExchange.STXManager;
import nl.rug.aoop.application.stockExchange.StockExchangeData;
import nl.rug.aoop.command.Command;

import java.util.List;
import java.util.Map;

@Slf4j
public class SellOrderCommand implements Command {
    private StockExchangeData stockExchangeData;
    private STXManager stxManager;

    public SellOrderCommand(StockExchangeData stockExchangeData, STXManager stxManager) {
        this.stockExchangeData = stockExchangeData;
        this.stxManager = stxManager;
    }

    @Override
    public void execute(Map<String, Object> params) {
        log.info("Handling SellOrder command...");
        if (!params.isEmpty()) {
            if (params.containsKey("info")) {
                String sellOrderJson = (String) params.get("info");
                SellOrder sellOrder = SellOrder.fromJson(sellOrderJson);
                Map<String, Object> matchingInfo = this.stockExchangeData.matchSellOrder(sellOrder);
                if (matchingInfo != null) {
                    Boolean matchingFound = (Boolean) matchingInfo.get("matching status");
                    if (matchingFound) {
                        this.stxManager.updateTraderProfile((String) matchingInfo.get("buyer Id"));
                        this.stxManager.updateTraderProfile((String) matchingInfo.get("seller Id"));
                        this.stxManager.updateAllTraderStockMap();
                    }
                }
            } else {
                log.info("No order info");
            }
        } else {
            log.info("Command params is empty");
        }
    }
}
