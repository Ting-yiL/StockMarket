package nl.rug.aoop.networking.handler;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.networking.messagequeue.Communicator;

@Slf4j
public class NetworkConsumerMessageHandler implements MessageHandler{
    private Communicator communicator;

    public NetworkConsumerMessageHandler(Communicator communicator) {
        this.communicator = communicator;
    }

    @Override
    public synchronized void  handleMessage(String jsonMessage) {
        try {
            Message message = Message.fromJson(jsonMessage);
            this.communicator.receiveMessage(message);
        } catch (Exception e) {
            log.info("Consumer Receiving " + jsonMessage);
        }
    }
}
