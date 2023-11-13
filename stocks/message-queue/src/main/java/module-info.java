module messagequeue {
    requires static lombok;
    requires com.google.gson;
    requires org.slf4j;
    requires org.mockito;
    requires awaitility;
    opens nl.rug.aoop.messagequeue.message to com.google.gson;
    exports nl.rug.aoop.messagequeue.producer;
    exports nl.rug.aoop.messagequeue.consumer;
    exports nl.rug.aoop.messagequeue.message;
    exports nl.rug.aoop.messagequeue.queue;
}