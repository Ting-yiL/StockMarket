package nl.rug.aoop.messagequeue.command;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;

public class MQConsumerCommandHandlerFactory {
    private final ThreadSafeMessageQueue queue;

    /**
     * The constructor of the QueueCommandHandlerFactory.
     * @param queue The queue that the command handler will operate on.
     */
    public MQConsumerCommandHandlerFactory(ThreadSafeMessageQueue queue) {
        this.queue = queue;
    }

    /**
     * Creating the Message Queue Command Handler.
     * @return the command handler.
     */
    public CommandHandler createMQCommandHandler() {
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.registerCommand("MqPoll", new MQPollCommand(this.queue));
        return commandHandler;
    }
}
