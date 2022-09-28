package me.cnm.shared.cli.component;

import me.cnm.shared.cli.log.LogLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
     * @param logLevel  The log level of the message
     * @param message   The message wich should be printed
     * @param throwable A throwable, that should be logged with it
     */
    void print(@NotNull LogLevel logLevel, @NotNull String message, @Nullable Throwable throwable);

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
