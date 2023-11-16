package nl.rug.aoop.networking.handler;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;
import nl.rug.aoop.networking.command.MQPollCommand;
import nl.rug.aoop.networking.command.MQPutCommand;
import nl.rug.aoop.networking.server.ClientHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MQServerCommandHandlerFactory {
    private final ThreadSafeMessageQueue queue;
    private final Map<Integer, ClientHandler> clientHandlers;

    public MQServerCommandHandlerFactory(ThreadSafeMessageQueue queue, Map<Integer, ClientHandler> clientHandlers) {
        this.queue = queue;
        this.clientHandlers = clientHandlers;
    }

    public MQServerCommandHandlerFactory(ThreadSafeMessageQueue queue) {
        this.queue = queue;
        this.clientHandlers = new HashMap<>();
    }

    /**
     * Creating the Message Queue Command Handler.
     * @return the command handler.
     */
    public CommandHandler createMQCommandHandler() {
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.registerCommand("MqPut", new MQPutCommand(this.queue));
        commandHandler.registerCommand("MqPoll", new MQPollCommand(this.queue, this.clientHandlers));
        //commandHandler.registerCommand("UpdateProfile", );
        //commandHandler.registerCommand("UpdateStockMap");
        //commandHandler.registerCommand("RequestProfile");
        //commandHandler.registerCommand("RequestProfile");
        return commandHandler;
    }
}
