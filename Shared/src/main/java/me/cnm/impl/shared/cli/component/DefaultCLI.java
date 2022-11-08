package me.cnm.impl.shared.cli.component;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.cnm.impl.shared.cli.command.ConsoleHandler;
import me.cnm.impl.shared.cli.log.SystemLogger;
import me.cnm.shared.cli.command.Command;
import me.cnm.shared.cli.command.ICommandHandler;
import me.cnm.shared.cli.component.IDefaultCLI;
import me.cnm.shared.cli.log.ILogHandler;
import me.cnm.shared.cli.log.LogLevel;
import me.cnm.shared.cli.message.create.CLIMessageBuilder;
import me.cnm.shared.cli.message.option.Color;
import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class DefaultCLI implements IDefaultCLI {

    private final ICommandHandler commandHandler;
    private final ILogHandler logHandler;
    public final ConsoleHandler consoleHandler;

    private final PrintStream consoleStream;
    private final SystemLogger systemLogger;

    @Override
    public void printPlainToConsole(Object object) {
        this.consoleStream.print(object);
    }

    @Override
    public void printToConsole(Object object) {
        this.consoleHandler.printAbove(object);
    }

    @Override
    public void printlnToConsole(Object object) {
        this.printToConsole(object + "\n");
    }

    @Override
    public void print(@NonNull LogLevel logLevel, @NonNull String message, Throwable throwable) {
        this.systemLogger.log(logLevel, message, throwable);
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
        this.logHandler.debug(">" + String.join(" ", args));

        Command command = this.commandHandler.get(args[0]);
        String[] commandArgs = Arrays.copyOfRange(args, 1, args.length);

        if (command == null) {
            this.logHandler.warn("This command doesn't exist. Use help to get a list of all commands.");
            return;
        }

        try {
            command.execute(commandArgs);
        } catch (Exception e) {
            this.print(LogLevel.ERROR, "An error occurred while execute command " + command.getName(), null);
        }
    }

    @Override
    public String getPrompt() {
        return CLIMessageBuilder.create()
                .textFg("CNM", Color.LIGHT_RED)
                .text(">")
                .build();
    }
}
