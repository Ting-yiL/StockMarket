package nl.rug.aoop.application.stockExchange.command;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.stockExchange.STXManager;
import nl.rug.aoop.command.Command;
import nl.rug.aoop.networking.server.ClientHandler;

import java.util.Map;

@Slf4j
public class RegisterProfileCommand implements Command {
    private STXManager stxManager;

    public RegisterProfileCommand(STXManager stxManager) {
        this.stxManager = stxManager;
    }
    public void execute(Map<String, Object> params) {
        if (!params.isEmpty() && (params != null)) {
            if ((params.get("header") instanceof String) && (
                    params.get("body") instanceof String) &&
                    (params.get("reference") != null)) {
                log.info("Performing request Profile command");
                String traderId = (String) params.get("body");
                ClientHandler clientHandler = (ClientHandler) params.get("reference");
                this.stxManager.registerTraderConnection(traderId, clientHandler);
                this.stxManager.updateTraderProfile(traderId);
                stxManager.updateTraderStockMap(traderId);
            } else {
                log.info("Request Profile Unsuccessful");
            }
        }
    }
}
