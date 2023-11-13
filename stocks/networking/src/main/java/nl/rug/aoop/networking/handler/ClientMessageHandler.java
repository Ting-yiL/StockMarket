package nl.rug.aoop.networking.handler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientMessageHandler implements MessageHandler{
    @Override
    public void handleMessage(String message) {
        log.info(message);
    }
}
