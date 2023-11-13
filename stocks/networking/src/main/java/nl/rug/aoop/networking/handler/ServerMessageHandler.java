package nl.rug.aoop.networking.handler;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class ServerMessageHandler implements MessageHandler{
    @Override
    public void handleMessage(String message) {
        log.info(message);
    }
}
