package nl.rug.aoop.networking.handler;

import lombok.extern.slf4j.Slf4j;

/**
 * MessageLogger - A class that log incoming message.
 * @author Dylan Bonatz, Ting-Yi Lin
 * @version 1.0
 */
@Slf4j
public class MessageLogger implements MessageHandler {

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleMessage(String message) {
        log.info(message);}
}