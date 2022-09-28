package me.cnm.shared.cli.component;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * The CLI can be modified by setting a custom CLI component<br>
 * Custom CLI components can modify outputs, autocompletion and inputs
 */
public interface ICLIComponent {

    /**
     * Method called when a message should be printed to the log
     *
     * @param message The message wich should be printed
     */
    @ApiStatus.OverrideOnly
    void print(@NotNull String message);

    /**
     * Method called when the {@code tab} key is pressed
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

}
