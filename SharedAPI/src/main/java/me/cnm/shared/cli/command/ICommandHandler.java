package me.cnm.shared.cli.command;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * The {@code CommandHandler} handels everything about CLI commands
 */
public interface ICommandHandler {

    /**
     * Register a command
     *
     * @param command The command to register
     * @throws CommandRegistrationException If a command with the same name or alias is already registered
     */
    void register(@NotNull Command command);

    /**
     * Remove a command<br>
     * If the command is not registered, the call is ignored
     *
     * @param command The command to be removed
     */
    void remove(@NotNull Command command);

    /**
     * Remove a command by its name<br>
     * If the command is not registered or an alias is provided, the call is ignored
     *
     * @param command The command to be removed
     */
    void remove(@NotNull String command);

    /**
     * Get a command by its name or alias, if no command with that name or alias exists, null is returned
     *
     * @param commandOrAlias The name or alias of the command
     * @return The command or null
     */
    @Nullable
    Command get(@NotNull String commandOrAlias);

    /**
     * Get all registered commands
     *
     * @return All registered commands
     */
    @NotNull
    List<Command> getAll();

}
