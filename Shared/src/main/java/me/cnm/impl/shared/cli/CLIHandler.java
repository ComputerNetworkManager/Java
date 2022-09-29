package me.cnm.impl.shared.cli;

import lombok.NonNull;
import me.cnm.impl.shared.cli.command.CommandHandler;
import me.cnm.impl.shared.cli.command.ConsoleHandler;
import me.cnm.impl.shared.cli.component.DefaultCLI;
import me.cnm.impl.shared.cli.component.DefaultCLIComponent;
import me.cnm.impl.shared.cli.log.LogHandler;
import me.cnm.impl.shared.cli.log.SystemLogger;
import me.cnm.shared.IHandlerLibrary;
import me.cnm.shared.cli.ICLIHandler;
import me.cnm.shared.cli.command.ICommandHandler;
import me.cnm.shared.cli.component.AbstractCLIComponent;
import me.cnm.shared.cli.component.ICLIComponent;
import me.cnm.shared.cli.component.IDefaultCLI;
import me.cnm.shared.cli.log.ILogHandler;
import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;

public class CLIHandler implements ICLIHandler {

    // Command
    private final ICommandHandler commandHandler;
    private final ConsoleHandler consoleHandler;

    // Component
    private final ICLIComponent defaultCLIComponent;
    private final IDefaultCLI defaultCLI;
    private ICLIComponent currentComponent;

    // Log
    private final ILogHandler logHandler;

    public CLIHandler(IHandlerLibrary handlerLibrary) {

        PrintStream consoleStream = System.out;

        // Command
        this.commandHandler = new CommandHandler(handlerLibrary);
        this.consoleHandler = new ConsoleHandler(() -> currentComponent);

        // Log
        SystemLogger systemLogger = new SystemLogger(handlerLibrary);
        this.logHandler = new LogHandler(() -> currentComponent);

        // Component
        this.defaultCLIComponent = new DefaultCLIComponent();
        this.defaultCLI = new DefaultCLI(this.commandHandler, this.logHandler, this.consoleHandler, consoleStream, systemLogger);

        // Register handler
        handlerLibrary.registerHandler(ICommandHandler.class, this.commandHandler);
        handlerLibrary.registerHandler(ILogHandler.class, this.logHandler);
    }

    public void startCLI() {
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
    }

    @Override
    public void resetComponent() {
        this.setComponent(this.defaultCLIComponent);
    }

    @Override
    @NotNull
    public ILogHandler getLogHandler() {
        return this.logHandler;
    }

}
