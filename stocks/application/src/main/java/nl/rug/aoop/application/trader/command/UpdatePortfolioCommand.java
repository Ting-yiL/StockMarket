package nl.rug.aoop.application.trader.command;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.trader.TraderClient;
import nl.rug.aoop.application.trader.TraderData;
import nl.rug.aoop.command.Command;

import java.util.Map;

@Slf4j
public class UpdatePortfolioCommand implements Command {
    private TraderClient traderClient;

    public UpdatePortfolioCommand(TraderClient traderClient) {
        this.traderClient = traderClient;
    }

    @Override
    public void execute(Map<String, Object> params) {
        log.info("Handling UpdatePortfolio command...");
        if (!params.isEmpty()) {
            if (params.containsKey("body")) {
                String traderDataJson = (String) params.get("body");
                TraderData traderData = TraderData.fromJson(traderDataJson);
                this.traderClient.setTraderData(traderData);
                log.info("Trader data updated");
            } else {
                log.info("No trader info");
            }
        } else {
            log.info("Command params is empty");
        }
    }
}
