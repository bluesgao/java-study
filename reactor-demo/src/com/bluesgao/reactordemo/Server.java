package com.bluesgao.reactordemo;

public class Server {
    private Selector selector = new Selector();
    private Dispatcher dispatcher = new Dispatcher(selector);
    private Acceptor acceptor;

    public Server(int port) {
        acceptor = new Acceptor(port, selector);
    }

    public void start() {
        dispatcher.registEventHandler(EventType.ACCEPT, new AcceptEventHandler(selector));
        dispatcher.registEventHandler(EventType.READ, new ReadEventHandler(selector));
        new Thread(acceptor, "Accetpor-" + acceptor.getPort()).start();
        dispatcher.handleEvents();
    }

    public static void main(String[] args) {
        Server server = new Server(8080);
        server.start();
    }
}
