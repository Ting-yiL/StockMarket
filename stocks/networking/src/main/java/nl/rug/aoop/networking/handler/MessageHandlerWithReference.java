package nl.rug.aoop.networking.handler;

public interface MessageHandlerWithReference {
    /**
     * Handling incoming message.
     * @param message The incoming message.
     */
    void handleMessage(String message, Object reference);

}
