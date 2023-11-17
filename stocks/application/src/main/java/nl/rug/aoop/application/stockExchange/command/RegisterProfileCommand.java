package nl.rug.aoop.application.stockExchange.command;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.stockExchange.STXManager;
import nl.rug.aoop.command.Command;
import nl.rug.aoop.networking.server.ClientHandler;

import java.util.Map;

/**
 * The register profile command.
 */
@Slf4j
public class RegisterProfileCommand implements Command {
    private STXManager stxManager;

    /**
     * The constructor of the Register Profile Command.
     * @param stxManager The STXManager.
     */
    public RegisterProfileCommand(STXManager stxManager) {
        this.stxManager = stxManager;
    }

    /**
     * Executing the command.
     * @param params is the command options.
     */
    public void execute(Map<String, Object> params) {
        if (!params.isEmpty() && (params != null)) {
            if ((params.get("header") instanceof String) && (
                    params.get("body") instanceof String) &&
                    (params.get("reference") != null)) {
                log.info("Performing request Profile command");
                String traderId = (String) params.get("body");
                ClientHandler clientHandler = (ClientHandler) params.get("reference");
                this.stxManager.registerTraderConnection(traderId, clientHandler);
                this.stxManager.updateTraderProfile(traderId, null);
                stxManager.updateTraderStockMap(traderId);
            } else {
                log.info("Request Profile Unsuccessful");
            }
        }
    }
}
