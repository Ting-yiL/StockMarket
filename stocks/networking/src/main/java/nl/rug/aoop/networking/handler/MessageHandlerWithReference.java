package nl.rug.aoop.networking.handler;

/**
 * MessageHandlerWithReference - A class that handle incoming message.
 * @author Ting-Yi Lin
 * @version 1.0
 */
public interface MessageHandlerWithReference {
    /**
     * Handling incoming message.
     * @param message The message.
     * @param reference The reference to refer to.
     */
    void handleMessage(String message, Object reference);

}
