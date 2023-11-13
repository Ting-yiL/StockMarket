package nl.rug.aoop.networking.handler;

/**
 * MessageHandler - A class that handle incoming message.
 * @author Dylan Bonatz, Ting-Yi Lin
 * @version 1.0
 */
public interface MessageHandler {

    /**
     * Handling incoming message.
     * @param message The incoming message.
     */
    void handleMessage(String message);
}