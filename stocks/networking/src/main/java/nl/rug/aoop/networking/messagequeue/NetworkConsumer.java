package nl.rug.aoop.networking.messagequeue;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.messagequeue.consumer.MQConsumer;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.networking.client.Client;
import org.awaitility.core.ConditionTimeoutException;

import java.time.Duration;

import static org.awaitility.Awaitility.await;

@Slf4j
public class NetworkConsumer implements MQConsumer {
    @Getter
    private final Client client;
    private NetworkCommunicator communicator;
    private final int TIMEOUT = 5000;


    public NetworkConsumer(Client client, NetworkCommunicator communicator) {
        this.client = client;
        this.communicator = communicator;
    }

    public String createPollMessage() {
        return new NetworkMessage("MqPoll", null, null).toJson();
    }

    @Override
    public Message poll() {
        Message message = null;
        String jsonMessage = this.createPollMessage();
        log.info(jsonMessage);
        client.sendMessage(jsonMessage);
        try {
            await().atMost(Duration.ofMillis(TIMEOUT)).until(() -> communicator.getStatus());
            message = this.communicator.getMessage();
            this.communicator.resetStatus();
        } catch (ConditionTimeoutException e) {
            log.error("Empty queue", e);
        }
        return message;
    }
}
