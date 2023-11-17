package nl.rug.aoop.application.stockExchange;

import nl.rug.aoop.application.stockExchange.command.RegisterProfileCommand;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;
import nl.rug.aoop.networking.command.MQPutCommand;
import nl.rug.aoop.networking.server.ClientHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * The STXServerCommandHandlerFactory class.
 */
public class STXServerCommandHandlerFactory {
    private final ThreadSafeMessageQueue queue;
    private final STXManager stxManager;
    private final Map<Integer, ClientHandler> clientHandlers;

    /**
     * The constructor of STXServerCommandHandlerFactory.
     * @param queue The queue.
     * @param stxManager The STXManager.
     */
    public STXServerCommandHandlerFactory(ThreadSafeMessageQueue queue, STXManager stxManager) {
        this.queue = queue;
        this.stxManager = stxManager;
        this.clientHandlers = new HashMap<>();
    }

    /**
     * create a STXServerCommandHandler.
     * @return The commandHandler.
     */
    public CommandHandler createSTXServerCommandHandler() {
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.registerCommand("MqPut", new MQPutCommand(this.queue));
        commandHandler.registerCommand("RegisterProfile", new RegisterProfileCommand(this.stxManager));
        return commandHandler;
    }
}
