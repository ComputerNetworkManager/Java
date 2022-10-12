package me.cnm.impl.shared.cli.component;

import me.cnm.shared.cli.component.AbstractCLIComponent;
import me.cnm.shared.cli.log.LogLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DefaultCLIComponent extends AbstractCLIComponent {

    @Override
    public void print(@NotNull LogLevel logLevel, @NotNull String message, @Nullable Throwable throwable) {
        this.getDefaultCLI().print(logLevel, message, throwable);
    }

    @Override
    public @NotNull List<String> getAutoComplete(@NotNull String[] args) {
        return this.getDefaultCLI().getAutoComplete(args);
    }

    @Override
    public void handleInput(@NotNull String[] args) {
        this.getDefaultCLI().handleInput(args);
    }

    @Override
    public String getPrompt() {
        return this.getDefaultCLI().getPrompt();
    }

}
