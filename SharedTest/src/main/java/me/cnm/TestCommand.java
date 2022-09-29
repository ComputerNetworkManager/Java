package me.cnm;

import me.cnm.shared.cli.ICLIHandler;
import me.cnm.shared.cli.command.Command;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TestCommand extends Command {

    protected TestCommand() {
        super("test", "t", "bsp");
    }

    @Override
    public void execute(@NotNull String[] args) {
        this.getHandlerLibrary().getHandler(ICLIHandler.class).getLogHandler().info("Test command");
        this.getHandlerLibrary().getHandler(ICLIHandler.class).getLogHandler().warn("BBC");
    }

    @Override
    public void autocomplete(@NotNull String[] args, @NotNull List<String> suggestions) {
        suggestions.add("XYZ");
        suggestions.add("BBC");
    }
}
