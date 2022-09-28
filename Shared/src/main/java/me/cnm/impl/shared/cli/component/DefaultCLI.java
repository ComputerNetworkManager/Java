package me.cnm.impl.shared.cli.component;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.cnm.impl.shared.cli.command.ConsoleHandler;
import me.cnm.impl.shared.cli.log.SystemLogger;
import me.cnm.shared.cli.command.Command;
import me.cnm.shared.cli.command.ICommandHandler;
import me.cnm.shared.cli.component.IDefaultCLI;
import me.cnm.shared.cli.log.LogLevel;
import org.fusesource.jansi.Ansi;
import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class DefaultCLI implements IDefaultCLI {

    private final ICommandHandler commandHandler;
    private final ConsoleHandler consoleHandler;

    private final PrintStream consoleStream;
    private final SystemLogger systemLogger;

    @Override
    public void printToConsole(String object) {
        this.consoleStream.print(Ansi.ansi().eraseLine());
        this.consoleStream.print(object);
        this.consoleHandler.redrawLine();
    }

    @Override
    public void print(@NonNull LogLevel logLevel, @NonNull String message, Throwable throwable) {
        this.consoleStream.print(Ansi.ansi().eraseLine());
        this.systemLogger.log(logLevel, message, throwable);
        this.consoleHandler.redrawLine();
    }

    @Override
    public @NotNull List<String> getAutoComplete(@NotNull String[] args) {
        List<String> autoComplete = new ArrayList<>();

        if (args.length == 1) {
            for (Command command : this.commandHandler.getAll()) {
                autoComplete.add(command.getName());
                autoComplete.addAll(Arrays.asList(command.getAliases()));
            }

            return autoComplete;
        }

        Command command = this.commandHandler.get(args[0]);
        String[] commandArgs = Arrays.copyOfRange(args, 1, args.length);

        if (command == null) return autoComplete;

        command.autocomplete(commandArgs, autoComplete);

        return autoComplete;
    }

    @Override
    public void handleInput(@NotNull String[] args) {
        if (args.length == 0) return;

        Command command = this.commandHandler.get(args[0]);
        String[] commandArgs = Arrays.copyOfRange(args, 1, args.length);

        if (command == null) return;

        command.execute(commandArgs);
    }
}
