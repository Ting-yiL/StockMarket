package nl.rug.aoop.command;

import java.util.Map;

/**
 * Command - An interface of Command.
 * @author Dylan Bonatz, Ting-Yi Lin
 * @version 1.0
 */
public interface Command {
    /**
     * Execute the command.
     * @param params parameter map that consists of key-value pairs similar to JSON.
     */
    default void execute(Map<String, Object> params) {
    }
}
