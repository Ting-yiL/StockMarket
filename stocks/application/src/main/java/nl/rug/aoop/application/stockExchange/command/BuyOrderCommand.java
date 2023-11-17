package nl.rug.aoop.application.stockExchange.command;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.order.BuyOrder;
import nl.rug.aoop.application.stockExchange.STXManager;
import nl.rug.aoop.application.stockExchange.StockExchangeData;
import nl.rug.aoop.application.trader.TraderData;
import nl.rug.aoop.command.Command;

import java.util.List;
import java.util.Map;

@Slf4j
public class BuyOrderCommand implements Command {
    private StockExchangeData stockExchangeData;
    private STXManager stxManager;

    public BuyOrderCommand(StockExchangeData stockExchangeData, STXManager stxManager) {
        this.stockExchangeData = stockExchangeData;
        this.stxManager = stxManager;
    }

    @Override
    public void execute(Map<String, Object> params) {
        log.info("Handling BuyOrder command...");
        if (!params.isEmpty()) {
            if (params.containsKey("info")) {
                String buyOrderJson = (String) params.get("info");
                BuyOrder buyOrder = BuyOrder.fromJson(buyOrderJson);
                Map<String, Object> matchingInfo = this.stockExchangeData.matchBuyOrder(buyOrder);
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
