package nl.rug.aoop.networking.handler;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.networking.messagequeue.Medium;

@Slf4j
public class NetworkConsumerMessageHandler implements MessageHandler{
    private Medium medium;

    public NetworkConsumerMessageHandler(Medium medium) {
        this.medium = medium;
    }

    @Override
    public synchronized void  handleMessage(String jsonMessage) {
        try {
            log.info("Consumer Receiving " + jsonMessage);
            Message message = Message.fromJson(jsonMessage);
            log.info("Message Header " + message.getHeader());
            this.medium.receiveMessage(message);
        } catch (Exception e) {
            log.info("Consumer Receiving " + jsonMessage);
        }
    }
}
