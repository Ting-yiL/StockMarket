package nl.rug.aoop.application.stockExchange.command;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.Command;

import java.util.Map;

@Slf4j
public class RequestProfileCommand implements Command {
    public void execute(Map<String, Object> params) {
        if (!params.isEmpty() && (params != null)) {
            if ((params.get("header") instanceof String) && (params.get("body") instanceof String)) {
                log.info("Performing request Profile command");
            } else {
                log.info("Request Profile Unsuccessful");
            }
        }
    }
}
