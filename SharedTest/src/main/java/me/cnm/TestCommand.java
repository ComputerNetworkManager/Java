package me.cnm;

import lombok.NonNull;
import me.cnm.shared.cli.command.Command;
import me.cnm.shared.cli.log.ILogHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TestCommand extends Command {

    protected TestCommand() {
        super("test", "t", "bsp");
    }

    @Override
    public void execute(@NotNull String[] args) {
        this.getHandlerLibrary().getHandler(ILogHandler.class).info("Test command");
        this.getHandlerLibrary().getHandler(ILogHandler.class).warn("BBC");
    }

    @Override
    public void autocomplete(@NotNull String[] args, @NotNull List<String> suggestions) {
        suggestions.add("XYZ");
        suggestions.add("BBC");
    }
}
