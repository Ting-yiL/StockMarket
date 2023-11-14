package nl.rug.aoop.application.trader.command;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.trader.TraderData;
import nl.rug.aoop.command.Command;

import java.util.Map;

@Slf4j
public class UpdatePortfolioCommand implements Command {
    private TraderData traderData;

    public UpdatePortfolioCommand(TraderData traderData) {
        this.traderData = traderData;
    }

    @Override
    public void execute(Map<String, Object> params) {
        if (!params.isEmpty()) {
            if (params.containsKey("info")) {
                String traderDataJson = (String) params.get("Info");
                //...
            } else {
                log.info("No trader info");
            }
        } else {
            log.info("Command params is empty");
        }
    }
}
