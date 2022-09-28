package me.cnm.impl.shared.cli.component;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.cnm.impl.shared.cli.log.SystemLogger;
import me.cnm.shared.cli.component.IDefaultCLI;
import me.cnm.shared.cli.log.LogLevel;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class DefaultCLI implements IDefaultCLI {

    private final PrintStream consoleStream;
    private final SystemLogger systemLogger;

    @Override
    public void print(@NonNull LogLevel logLevel, @NonNull String message, Throwable throwable) {
        systemLogger.log(logLevel, message, throwable);
    }

    @Override
    public @NotNull List<String> getAutoComplete(@NotNull String[] args) {
        throw new NotImplementedException();
    }

    @Override
    public void handleInput(@NotNull String[] args) {
        throw new NotImplementedException();
    }
}
