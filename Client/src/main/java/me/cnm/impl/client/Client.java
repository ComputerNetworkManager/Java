package me.cnm.impl.client;

import me.cnm.impl.shared.bootstrap.Bootstrap;

public class Client {

    private final Bootstrap bootstrap;

    public Client() {
        this.bootstrap = new Bootstrap();
        this.bootstrap.registerHandlers();
    }

    public void start() {
        this.bootstrap.start();
    }

    public void stop() {
        this.bootstrap.stop();
    }

}
