package nl.rug.aoop.networking.handler;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;
import nl.rug.aoop.networking.command.MQPutCommand;

/**
 * MQServerCommandHandlerFactory - A class that produces MQServerCommandHandler.
 * @author Dylan Bonatz, Ting-Yi Lin
 * @version 1.0
 */
public class MQServerCommandHandlerFactory {
    private final ThreadSafeMessageQueue queue;

    /**
     * The constructor of MQServerCommandHandlerFactory.
     * @param queue A threadSafeQueue.
     */
    public MQServerCommandHandlerFactory(ThreadSafeMessageQueue queue) {
        this.queue = queue;
    }

    /**
     * Creating the Message Queue Command Handler.
     * @return the command handler.
     */
    public CommandHandler createMQCommandHandler() {
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.registerCommand("MqPut", new MQPutCommand(this.queue));
        return commandHandler;
    }
}
