package me.cnm.impl.client;

import me.cnm.shared.cli.command.Command;
import me.cnm.shared.cli.log.ILogHandler;
import org.jetbrains.annotations.NotNull;

public class Test extends Command {

    public Test() {
        super("test");
    }

    @Override
    public void execute(@NotNull String[] args) {
        this.getHandlerLibrary().getHandler(ILogHandler.class).info("test");
    }
}
