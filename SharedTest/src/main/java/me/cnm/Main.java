package me.cnm;

import me.cnm.impl.shared.bootstrap.Bootstrap;
import me.cnm.impl.shared.cli.CLIHandler;
import me.cnm.shared.IHandlerLibrary;
import me.cnm.shared.cli.ICLIHandler;

public class Main {

    private final IHandlerLibrary handlerLibrary;

    public Main() {
        Bootstrap bootstrap = new Bootstrap();
        this.handlerLibrary = bootstrap.createHandlerLibrary();

        ICLIHandler cliHandler = this.handlerLibrary.getHandler(ICLIHandler.class);
        ((CLIHandler) cliHandler).startCLI();

        cliHandler.getCommandHandler().register(new TestCommand());
    }

    public static void main(String[] args) {
        new Main();
    }


}