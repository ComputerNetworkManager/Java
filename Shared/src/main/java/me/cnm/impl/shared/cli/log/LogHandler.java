package me.cnm.impl.shared.cli.log;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.cnm.shared.cli.component.ICLIComponent;
import me.cnm.shared.cli.log.ILogHandler;
import me.cnm.shared.cli.log.LogLevel;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class LogHandler implements ILogHandler {

    private final Supplier<ICLIComponent> componentSupplier;

    @Override
    public void debug(@NonNull String message) {
        this.log(LogLevel.DEBUG, message);
    }

    @Override
    public void debug(@NonNull String message, @NonNull Throwable throwable) {
        this.log(LogLevel.DEBUG, message, throwable);
    }

    @Override
    public void info(@NonNull String message) {
        this.log(LogLevel.INFO, message);
    }

    @Override
    public void info(@NonNull String message, @NonNull Throwable throwable) {
        this.log(LogLevel.INFO, message, throwable);
    }

    @Override
    public void warn(@NonNull String message) {
        this.log(LogLevel.WARN, message);
    }

    @Override
    public void warn(@NonNull String message, @NonNull Throwable throwable) {
        this.log(LogLevel.WARN, message, throwable);
    }

    @Override
    public void error(@NonNull String message) {
        this.log(LogLevel.ERROR, message);
    }

    @Override
    public void error(@NonNull String message, @NonNull Throwable throwable) {
        this.log(LogLevel.ERROR, message, throwable);
    }

    @Override
    public void log(LogLevel logLevel, @NonNull String message) {
        this.log(logLevel, message, null);
    }

    @Override
    public void log(LogLevel logLevel, @NonNull String message, @Nullable Throwable throwable) {
        this.componentSupplier.get().print(logLevel, message, throwable);
    }
}
