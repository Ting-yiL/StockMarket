package nl.rug.aoop.networking.handler;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;
import nl.rug.aoop.networking.command.MQPollCommand;
import nl.rug.aoop.networking.command.MQPutCommand;
import nl.rug.aoop.networking.server.ClientHandler;

import java.util.List;

public class MQServerCommandHandlerFactory {
    private final ThreadSafeMessageQueue queue;

    public MQServerCommandHandlerFactory(ThreadSafeMessageQueue queue) {
        this.queue = queue;
        //this.clientHandlers = clientHandlers;
    }

    /**
     * Creating the Message Queue Command Handler.
     * @return the command handler.
     */
    public CommandHandler createMQCommandHandler() {
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.registerCommand("MqPut", new MQPutCommand(this.queue));
        commandHandler.registerCommand("MqPoll", new MQPollCommand(this.queue));
        //commandHandler.registerCommand("UpdateProfile", );
        //commandHandler.registerCommand("UpdateStockMap");
        //commandHandler.registerCommand("RequestProfile");
        //commandHandler.registerCommand("RequestProfile");
        return commandHandler;
    }
}
