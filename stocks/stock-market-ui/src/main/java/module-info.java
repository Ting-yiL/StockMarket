module stock.market.ui {
    requires static lombok;
    exports nl.rug.aoop.initialization;
    requires com.fasterxml.jackson.core;
    requires org.slf4j;
    requires java.desktop;
    requires com.formdev.flatlaf;
    requires util;
    requires com.fasterxml.jackson.databind;
    exports nl.rug.aoop.model;
    opens nl.rug.aoop.model to com.fasterxml.jackson.databind;
}