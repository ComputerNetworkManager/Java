package me.cnm.shared.cli.command;

import lombok.NonNull;
import me.cnm.shared.IHandlerLibrary;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * A command that can be executed via the CLI input
 */
public abstract class Command {

    /**
     * The name of the command
     */
    private final String name;

    /**
     * Aliases of the command
     */
    private final String[] aliases;

    /**
     * The handler library to be used by the command
     */
    private IHandlerLibrary handlerLibrary;

    /**
     * Create a command
     *
     * @param name The name of the command
     */
    protected Command(@NonNull String name) {
        this(name, new String[0]);
    }

    /**
     * Create a command
     *
     * @param name    The name of the command
     * @param aliases The aliases of the command
     */
    protected Command(@NonNull String name, @NonNull String... aliases) {
        this.name = name;
        this.aliases = aliases;
    }

    /**
     * Execute this command with the specific arguments
     *
     * @param args Arguments used to invoke the command
     */
    public abstract void execute(@NotNull String[] args);

    /**
     * Autocomplete this command<br>
     * On each candidate it is automatically check, if the current input starts with the argument
     *
     * @param args        Arguments used to invoke the autocomplete
     * @param suggestions List to add the suggestions
     */
    @SuppressWarnings({"unused", "EmptyMethod"})
    public void autocomplete(@NotNull String[] args, @NotNull List<String> suggestions) {

    }

    /**
     * Get the name of the command
     *
     * @return Name of the command
     */
    @NotNull
    public final String getName() {
        return name;
    }

    /**
     * Get all aliases of the command
     *
     * @return Aliases of the command
     */
    @NotNull
    public final String[] getAliases() {
        return aliases;
    }

    /**
     * Get the description of the command<br>
     * Displayed in the "help" command
     *
     * @return description of the command
     */
    @ApiStatus.OverrideOnly
    public String getDescription() {
        return null;
    }

    /**
     * Get the syntax of the command<br>
     * Displayed in the "help command"<br>
     * Should not contain the leading command, for example for the command "command <arg>" this should return "<arg>"
     *
     * @return syntax of the command
     */
    @ApiStatus.OverrideOnly
    public String getSyntax() {
        return null;
    }

    /**
     * Get the handler library
     *
     * @return The handler library
     */
    @NotNull
    protected final IHandlerLibrary getHandlerLibrary() {
        return Objects.requireNonNull(handlerLibrary);
    }

    /**
     * Set the handler library, automatically done when registering
     *
     * @param handlerLibrary The handler library
     */
    @ApiStatus.Internal
    public void setHandlerLibrary(@NonNull IHandlerLibrary handlerLibrary) {
        this.handlerLibrary = handlerLibrary;
    }
}
