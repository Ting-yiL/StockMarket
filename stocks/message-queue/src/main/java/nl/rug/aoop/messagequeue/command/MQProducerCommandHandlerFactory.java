package nl.rug.aoop.messagequeue.command;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;

/**
 * QueueCommandHandlerFactory - A factory that you can customize command handler.
 * @author Dylan Bonatz, Ting-Yi Lin
 * @version 1.0
 */
public class MQProducerCommandHandlerFactory {
    private final ThreadSafeMessageQueue queue;

    /**
     * The constructor of the QueueCommandHandlerFactory.
     * @param queue The queue that the command handler will operate on.
     */
    public MQProducerCommandHandlerFactory(ThreadSafeMessageQueue queue) {
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
