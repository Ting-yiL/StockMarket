package nl.rug.aoop.application.stockExchange.command;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.order.BuyOrder;
import nl.rug.aoop.application.order.SellOrder;
import nl.rug.aoop.application.stockExchange.STXManager;
import nl.rug.aoop.application.stockExchange.StockExchangeData;
import nl.rug.aoop.command.Command;
import nl.rug.aoop.messagequeue.message.Message;

import java.util.Map;

/**
 * The sell order command.
 */
@Slf4j
public class SellOrderCommand implements Command {
    private StockExchangeData stockExchangeData;
    private STXManager stxManager;

    /**
     * The constructor of the Sell Order Command.
     * @param stockExchangeData The StockExchangeData.
     * @param stxManager The STXManager.
     */
    public SellOrderCommand(StockExchangeData stockExchangeData, STXManager stxManager) {
        this.stockExchangeData = stockExchangeData;
        this.stxManager = stxManager;
    }

    /**
     * {@inheritDoc}
     * @param params is the command options.
     */
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
                        BuyOrder buyOrder = (BuyOrder) matchingInfo.get("BuyOrder");
                        Message buyOrderMessage = new Message("BuyOrder", buyOrder.toJson());
                        Message sellOrderMessage = new Message("SellOrder", sellOrderJson);
                        this.stxManager.updateTraderProfile(buyOrder.getTraderID(), buyOrderMessage.toJson());
                        this.stxManager.updateTraderProfile(sellOrder.getTraderID(), sellOrderMessage.toJson());
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
