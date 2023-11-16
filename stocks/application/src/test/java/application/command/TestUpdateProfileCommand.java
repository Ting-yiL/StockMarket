package application.command;

import nl.rug.aoop.application.trader.TraderClient;
import nl.rug.aoop.application.trader.TraderClientMessageHandler;
import nl.rug.aoop.application.trader.TraderData;
import nl.rug.aoop.application.trader.command.TraderClientCommandHandlerFactory;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

public class TestUpdateProfileCommand {
    private TraderClientMessageHandler traderClientMessageHandler;
    private final TraderClient traderClientMock = Mockito.mock(TraderClient.class);
    private NetworkMessage networkMessage;

    @BeforeEach
    void SetUp() {
        traderClientMessageHandler = new TraderClientMessageHandler(traderClientMock);
        Message message = new Message("bot1",
                "{\"id\":\"1\",\"name\":\"Trader Joe\",\"funds\":1000.0,\"stockPortfolio\":{\"ownedShares\":{}}}");
        networkMessage = new NetworkMessage("UpdatePortfolio", message);
    }

    @Test
    void TestUpdatePortfolio() {
        traderClientMessageHandler.handleMessage(networkMessage.toJson());
        verify(traderClientMock).setTraderData();
    }
}
