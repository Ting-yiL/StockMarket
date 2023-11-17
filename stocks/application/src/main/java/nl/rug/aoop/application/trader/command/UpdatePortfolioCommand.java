package nl.rug.aoop.application.trader.command;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.trader.TraderClient;
import nl.rug.aoop.application.trader.TraderData;
import nl.rug.aoop.command.Command;
import nl.rug.aoop.messagequeue.message.Message;

import java.util.Map;

/**
 * The UpdatePortfolioCommand.
 */
@Slf4j
public class UpdatePortfolioCommand implements Command {
    private TraderClient traderClient;

    /**
     * The constructor of UpdatePortfolioCommand.
     * @param traderClient The traderClient.
     */
    public UpdatePortfolioCommand(TraderClient traderClient) {
        this.traderClient = traderClient;
    }

    /**
     * Execute the command.
     * @param params parameter map that consists of key-value pairs similar to JSON.
     */
    @Override
    public void execute(Map<String, Object> params) {
        log.info("Handling UpdatePortfolio command...");
        if (!params.isEmpty()) {
            if (params.containsKey("body")) {
                String traderDataJson = (String) params.get("body");
                TraderData traderData = TraderData.fromJson(traderDataJson);
                this.traderClient.setTraderData(traderData);
                log.info("Trader data updated");
            }
            if (params.containsKey("header") && params.get("header") != null) {
                String orderJson = (String) params.get("header");
                this.traderClient.receiveResolvedOrder(Message.fromJson(orderJson));
                log.info("Trader data updated");
            }
        } else {
            log.info("Command params is empty");
        }
    }
}
