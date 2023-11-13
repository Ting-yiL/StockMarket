package nl.rug.aoop.networking.messagequeue;

import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;
import nl.rug.aoop.networking.handler.MessageHandler;
import nl.rug.aoop.networking.handler.ServerMessageHandler;
import nl.rug.aoop.networking.messagequeue.handler.MQServerMessageHandler;
import nl.rug.aoop.networking.server.ClientHandler;
import nl.rug.aoop.networking.server.Server;

import java.io.IOException;
import java.net.Socket;

public class MQServer{
    private ThreadSafeMessageQueue queue;
    private Server server;
    private MQCommunicator mqCommunicator;

    public MQServer(Server server, )
}
