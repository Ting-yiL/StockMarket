module application {
    requires static lombok;
    requires org.slf4j;
    requires command;
    requires messagequeue;
    requires networking;
    requires stock.market.ui;
    requires util;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.google.gson;
    requires awaitility;
    exports nl.rug.aoop.application.stockExchange;
    opens nl.rug.aoop.application.stockExchange to com.fasterxml.jackson.databind, com.google.gson;
    exports nl.rug.aoop.application.stock;
    opens nl.rug.aoop.application.stock to com.fasterxml.jackson.databind, com.google.gson;
    exports nl.rug.aoop.application.trader;
    opens nl.rug.aoop.application.trader to com.fasterxml.jackson.databind, com.google.gson;
    exports nl.rug.aoop.application.order;
    exports nl.rug.aoop.application.order.comparator;
    opens nl.rug.aoop.application.order to com.google.gson;
    exports nl.rug.aoop.application.trader.tradingStrategy;
    opens nl.rug.aoop.application.trader.tradingStrategy to com.fasterxml.jackson.databind;
}