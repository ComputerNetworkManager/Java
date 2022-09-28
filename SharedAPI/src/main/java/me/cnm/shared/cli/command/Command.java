package me.cnm.shared.cli.command;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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
     * Autocomplete this command
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

}
