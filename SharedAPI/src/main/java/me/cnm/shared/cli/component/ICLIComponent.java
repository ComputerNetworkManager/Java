package me.cnm.shared.cli.component;

import me.cnm.shared.cli.log.LogLevel;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * The CLI can be modified by setting a custom CLI component<br>
 * Custom CLI components can modify outputs, autocompletion and inputs
 */
public interface ICLIComponent {

    /**
     * Method called when a message should be printed to the log
     *
     * @param logLevel  The log level of the message
     * @param message   The message wich should be printed
     * @param throwable A throwable, that should be logged with it
     */
    @ApiStatus.OverrideOnly
    void print(@NotNull LogLevel logLevel, @NotNull String message, @Nullable Throwable throwable);

    /**
     * Method called when the {@code tab} key is pressed<br>
     * On each candidate it is automatically check, if the current input starts with the argument
     *
     * @param args The current input, split by " "
     * @return All possible autocompletion
     */
    @ApiStatus.OverrideOnly
    @NotNull
    List<String> getAutoComplete(@NotNull String[] args);

    /**
     * Method called when the {@code enter} key is pressed
     *
     * @param args The current input, split by " "
     */
    @ApiStatus.OverrideOnly
    void handleInput(@NotNull String[] args);

    /**
     * Get the prompt to be shown in the console
     *
     * @return The prompt
     */
    String getPrompt();

}
