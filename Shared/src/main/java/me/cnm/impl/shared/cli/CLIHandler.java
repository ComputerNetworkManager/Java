package me.cnm.impl.shared.cli;

import lombok.Getter;
import lombok.NonNull;
import me.cnm.impl.shared.cli.command.CommandHandler;
import me.cnm.impl.shared.cli.command.ConsoleHandler;
import me.cnm.impl.shared.cli.component.DefaultCLI;
import me.cnm.impl.shared.cli.component.DefaultCLIComponent;
import me.cnm.impl.shared.cli.component.system.LoadingBarComponent;
import me.cnm.impl.shared.cli.log.LogHandler;
import me.cnm.impl.shared.cli.log.SystemLogger;
import me.cnm.shared.IHandlerLibrary;
import me.cnm.shared.cli.ICLIHandler;
import me.cnm.shared.cli.command.ICommandHandler;
import me.cnm.shared.cli.component.AbstractCLIComponent;
import me.cnm.shared.cli.component.ICLIComponent;
import me.cnm.shared.cli.component.IDefaultCLI;
import me.cnm.shared.cli.component.system.ILoadingBar;
import me.cnm.shared.cli.log.ILogHandler;
import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;

public class CLIHandler implements ICLIHandler {

    private final IHandlerLibrary handlerLibrary;

    // Command
    private final ICommandHandler commandHandler;

    @Getter
    private final ConsoleHandler consoleHandler;

    // Component
    private final ICLIComponent defaultCLIComponent;
    private final IDefaultCLI defaultCLI;
    private ICLIComponent currentComponent;

    // Log
    private final ILogHandler logHandler;

    public CLIHandler(IHandlerLibrary handlerLibrary) {
        @SuppressWarnings("java:S106")
        PrintStream consoleStream = System.out;

        this.handlerLibrary = handlerLibrary;

        // Command
        this.commandHandler = new CommandHandler(this.handlerLibrary);
        this.consoleHandler = new ConsoleHandler(() -> currentComponent);

        // Log
        SystemLogger systemLogger = new SystemLogger(this.handlerLibrary);
        this.logHandler = new LogHandler(() -> currentComponent);

        // Component
        this.defaultCLIComponent = new DefaultCLIComponent();
        this.defaultCLI = new DefaultCLI(this.commandHandler, this.logHandler, this.consoleHandler, consoleStream, systemLogger);
    }

    public void start() {
        this.resetComponent();
        this.consoleHandler.startListen();
    }

    @Override
    @NotNull
    public ICommandHandler getCommandHandler() {
        return this.commandHandler;
    }

    @Override
    public void setComponent(@NonNull ICLIComponent cliComponent) {
        if (cliComponent instanceof AbstractCLIComponent abstractComponent) abstractComponent.setDefaultCLI(this.defaultCLI);
        this.currentComponent = cliComponent;
        this.getConsoleHandler().interrupt();
    }

    @Override
    public void resetComponent() {
        this.setComponent(this.defaultCLIComponent);
    }

    @Override
    public @NotNull ILoadingBar createLoadingBar(@NotNull String name) {
        return new LoadingBarComponent(this.handlerLibrary, name);
    }

    @Override
    @NotNull
    public ILogHandler getLogHandler() {
        return this.logHandler;
    }

}
