package nl.rug.aoop.networking.messagequeue;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.messagequeue.consumer.MQConsumer;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.networking.client.Client;
import nl.rug.aoop.networking.messagequeue.MQCommunicator;

import java.time.Duration;

import static org.awaitility.Awaitility.await;

@Slf4j
public class NetworkConsumer implements MQConsumer {
    private final Client client;
    private MQCommunicator mqCommunicator;
    private final int TIMEOUT = 5000;


    public NetworkConsumer(Client client) {
        this.client = client;
        this.mqCommunicator = new MQCommunicator();
    }

    public String createPollMessage() {
        return new NetworkMessage("MqPoll", null, null).toJson();
    }

    @Override
    public Message poll() {
        String jsonMessage = this.createPollMessage();
        log.info(jsonMessage);
        client.sendMessage(jsonMessage);
        await().atMost(Duration.ofMillis(TIMEOUT)).until(mqCommunicator::getStatus);
        Message message = mqCommunicator.getMessage();
        mqCommunicator.resetStatus();
        return message;
    }
}
