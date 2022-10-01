package me.cnm.impl.shared.cli.command;

import lombok.NonNull;
import me.cnm.impl.shared.cli.command.system.HelpCommand;
import me.cnm.impl.shared.cli.command.system.ShutdownCommand;
import me.cnm.shared.IHandlerLibrary;
import me.cnm.shared.cli.command.Command;
import me.cnm.shared.cli.command.CommandRegistrationException;
import me.cnm.shared.cli.command.ICommandHandler;
import me.cnm.shared.utility.helper.ArrayHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandHandler implements ICommandHandler {

    private final IHandlerLibrary handlerLibrary;

    private final List<Command> commands = new ArrayList<>();

    public CommandHandler(IHandlerLibrary handlerLibrary) {
        this.handlerLibrary = handlerLibrary;

        // Register default commands
        this.register(new HelpCommand());
        this.register(new ShutdownCommand());
    }

    @Override
    public void register(@NonNull Command command) {
        for (Command target : commands) {
            if (target.getName().equals(command.getName())
                    || ArrayHelper.containsIgnoreCase(target.getAliases(), command.getName())
                    || ArrayHelper.containsIgnoreCase(command.getAliases(), target.getName())
                    || ArrayHelper.containsIgnoreCase(command.getAliases(), target.getAliases()))
                throw new CommandRegistrationException("A command with the same name or alias is already registered");
        }

        command.setHandlerLibrary(this.handlerLibrary);
        this.commands.add(command);
    }

    @Override
    public void remove(@NonNull Command command) {
        this.commands.remove(command);
    }

    @Override
    public void remove(@NonNull String command) {
        Command found = null;

        for (Command target : commands) {
            if (target.getName().equals(command)) found = target;
        }

        if (found != null) this.commands.remove(found);
    }

    @Override
    @Nullable
    public Command get(@NonNull String commandOrAlias) {
        for (Command command : this.commands) {
            if (command.getName().equalsIgnoreCase(commandOrAlias) ||
                    ArrayHelper.containsIgnoreCase(command.getAliases(), commandOrAlias))
                return command;
        }

        return null;
    }

    @Override
    @NotNull
    public List<Command> getAll() {
        return Collections.unmodifiableList(this.commands);
    }

}
