package nl.rug.aoop.networking.messagequeue.handler;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;
import nl.rug.aoop.networking.command.MQPollCommand;
import nl.rug.aoop.networking.command.MQPutCommand;

public class MQServerCommandHandlerFactory {
    private final ThreadSafeMessageQueue queue;

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
        commandHandler.registerCommand("MqPoll", new MQPollCommand(this.queue));
        return commandHandler;
    }
}
