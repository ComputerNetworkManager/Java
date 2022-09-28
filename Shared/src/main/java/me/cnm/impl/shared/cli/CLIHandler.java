package me.cnm.impl.shared.cli;

import lombok.NonNull;
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
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;

public class CLIHandler implements ICLIHandler {

    // Command

    // Component
    private final ICLIComponent defaultCLIComponent;
    private final IDefaultCLI defaultCLI;
    private ICLIComponent currentComponent;

    // Log
    private final ILogHandler logHandler;

    public CLIHandler(IHandlerLibrary handlerLibrary) {

        PrintStream consoleStream = System.out;

        // Command

        // Log
        SystemLogger systemLogger = new SystemLogger(handlerLibrary);
        this.logHandler = new LogHandler(() -> currentComponent);

        // Component
        this.defaultCLIComponent = new DefaultCLIComponent();
        this.defaultCLI = new DefaultCLI(consoleStream, systemLogger);
    }

    @Override
    @NotNull
    public ICommandHandler getCommandHandler() {
        throw new NotImplementedException();
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
