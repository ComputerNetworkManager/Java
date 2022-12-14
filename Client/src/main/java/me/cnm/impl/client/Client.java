package me.cnm.impl.client;

import me.cnm.impl.shared.bootstrap.Bootstrap;
import me.cnm.shared.cli.command.ICommandHandler;

public class Client {

    private final Bootstrap bootstrap;

    public Client() {
        this.bootstrap = new Bootstrap();
        this.bootstrap.registerHandlers();
    }

    public void start() {
        this.bootstrap.start();
        this.bootstrap.getHandlerLibrary().getHandler(ICommandHandler.class).register(new Test());
    }

    public void stop() {
        this.bootstrap.stop();
    }

}
