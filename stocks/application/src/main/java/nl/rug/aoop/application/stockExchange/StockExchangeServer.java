package nl.rug.aoop.application.stockExchange;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.order.OrderHandler;
import nl.rug.aoop.application.stock.StockMap;
import nl.rug.aoop.initialization.SimpleViewFactory;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;
import nl.rug.aoop.networking.handler.MQServerMessageHandler;
import nl.rug.aoop.networking.server.Server;
import nl.rug.aoop.util.YamlLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@Slf4j
public class StockExchangeServer {
    private final int port;
    private Server server;
    private OrderHandler orderHandler;
    private ThreadSafeMessageQueue queue;
    private StockExchangeData stockExchange;
    private ExecutorService service;
    private Thread pollingThread;
    private STXManager stxManager;

    public StockExchangeServer(int port, Path stocksPath, Path traderPath) throws IOException {
        this.queue = new ThreadSafeMessageQueue();

        YamlLoader yamlLoader1 = new YamlLoader(stocksPath);
        YamlLoader yamlLoader2 = new YamlLoader(traderPath);
        this.stockExchange = new StockExchangeData(yamlLoader1.load(StockMap.class),
                yamlLoader2.load(new TypeReference<>() {}));

        this.stxManager = new STXManager(this.stockExchange);

        this.server = new Server(port, new STXServerMessageHandler(this.queue, this.stxManager));
        this.port = this.server.getPort();

        this.orderHandler = new OrderMessageHandler(this.stockExchange);
        this.service = Executors.newCachedThreadPool();
    }

    public void runStockExchangeServer() {
        log.info("Setting Up UI");
        this.SetupUI();

        log.info("Running Stock Exchange Server...");
        this.service.submit(this.server);
        await().until(this.server::isRunning);
        log.info("Stock Exchange Server is running...");

        log.info("Running Polling Thread");
        this.runPollingThread();;
        await().until(this.pollingThread::isAlive);
    };

    public void SetupUI() {
        SimpleViewFactory simpleViewFactory = new SimpleViewFactory();
        simpleViewFactory.createView(this.stockExchange);
    }
    public void poll() {
        log.info("Polling Order...");
        Message message = this.queue.dequeue();
        if (message != null) {
            log.info("Handling Order...");
            this.orderHandler.handleOrder(message);
            this.stxManager.updateAllTraderProfile();
            this.stxManager.updateAllTraderStockMap();
        }
    }

    public void runPollingThread() {
        this.pollingThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                this.poll();
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        log.info("Starting Polling Thread...");
        this.pollingThread.start();
        Runtime.getRuntime().addShutdownHook(new Thread(this::terminateStockExchange));
    }

    public void terminateStockExchange() {
        log.info("Shutting down Stock Exchange...");
        this.service.shutdown();
    }
}
