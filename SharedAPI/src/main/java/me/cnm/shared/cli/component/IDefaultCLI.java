package me.cnm.shared.cli.component;

import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;
import java.util.List;

/**
 * The {@code default CLI} is used by {@link AbstractCLIComponent} to access the system on a level deeper
 */
public interface IDefaultCLI {

    /**
     * Get the {@link PrintStream} witch prints to the console
     *
     * @return The console print stream
     */
    @NotNull
    PrintStream getConsoleStream();

    /**
     * Print a message to the console and the log file
     *
     * @param message The message to be printed
     */
    void print(@NotNull String message);

    /**
     * Get the default autocomplete option<br>
     * If there is only one input, all commands are returned, otherwise the autocomplete method of the command is called
     *
     * @param args The current input
     * @return The autocompletion provided by commands
     */
    @NotNull
    List<String> getAutoComplete(@NotNull String[] args);

    /**
     * Handle an input via commands<br>
     * Check if the first input is a command, if it is executed that command otherwise send a help message
     *
     * @param args The current input
     */
    void handleInput(@NotNull String[] args);

}
