package nl.rug.aoop.command;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.HashMap;

/**
 * CommandHandler - A CommandHandler can validate an incoming command,
 * and either accept or reject it.
 * @author Dylan Bonatz, Ting-Yi Lin
 * @version 1.0
 */
@Slf4j
public class CommandHandler {
    private Map<String, Command> commandMap;

    /**
     * The constructor of the CommandHandler, the handler contains a commandMap.
     */
    public CommandHandler() {

        this.commandMap = new HashMap<>();
    }

    /**
     * Adding Commands to the CommandMap.
     * @param command key of the map, name of the command.
     * @param commandClass value of the map, the actual command class.
     */
    public void registerCommand(String command, Command commandClass) {
        this.commandMap.put(command, commandClass);
    }

    /**
     * Check whether the commandMap contains the key.
     * @param K the key.
     * @return true if the Map contains the key.
     */
    public boolean containsKey(String K) {
        return this.commandMap.containsKey(K);
    }

    /**
     * Make execution to the command that is passed into it, if it exists.
     * @param command the name of the command.
     * @param params parameter map is
     *               that it consists of key-value pairs similar to JSON.
     */
    public void execute(String command, Map<String, Object> params) {
        if (this.commandMap.containsKey(command)) {
            //log.info(command + "executed");
            this.commandMap.get(command).execute(params);
        }
    }
}
