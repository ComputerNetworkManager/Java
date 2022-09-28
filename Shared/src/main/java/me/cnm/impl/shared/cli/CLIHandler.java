package me.cnm.impl.shared.cli;

import lombok.NonNull;
import me.cnm.shared.cli.ICLIHandler;
import me.cnm.shared.cli.command.ICommandHandler;
import me.cnm.shared.cli.component.ICLIComponent;
import me.cnm.shared.cli.log.ILogHandler;
import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;

public class CLIHandler implements ICLIHandler {


    public CLIHandler() {
        PrintStream consoleStream = System.out;


    }

    @Override
    @NotNull
    public ICommandHandler getCommandHandler() {
        return null;
    }

    @Override
    public void setComponent(@NonNull ICLIComponent cliComponent) {

    }

    @Override
    public void resetComponent() {

    }

    @Override
    @NotNull
    public ILogHandler getLogHandler() {
        return null;
    }

}
