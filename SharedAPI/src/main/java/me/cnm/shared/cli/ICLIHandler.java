package me.cnm.shared.cli;

import me.cnm.shared.cli.command.ICommandHandler;
import me.cnm.shared.cli.component.AbstractCLIComponent;
import me.cnm.shared.cli.component.ICLIComponent;
import me.cnm.shared.cli.component.system.ILoadingBar;
import me.cnm.shared.cli.log.ILogHandler;
import org.jetbrains.annotations.NotNull;

/**
 * The {@code CLIHandler} contains a<br>
 * - command handler, for handling cli inputs,
 * - log handler, for handling cli outputs and
 * - component system, to modify the above
 */
public interface ICLIHandler {

    /**
     * Get the current instance of hte {@code CommandHandler}
     *
     * @return The current instance of hte {@code CommandHandler}
     * @see ICommandHandler
     */
    @NotNull
    ICommandHandler getCommandHandler();

    /**
     * Set the component to be used for CLI handling<br>
     * If an {@link AbstractCLIComponent} is provided, the {@code defaultCLI} will be set
     *
     * @param cliComponent The component to be used
     * @see ICLIComponent
     */
    void setComponent(@NotNull ICLIComponent cliComponent);

    /**
     * Remove the current component from handling and set the system to handle everything
     */
    void resetComponent();

    /**
     * Create a {@code LoadingBar} component
     *
     * @param name The name of the loading bar
     * @return The loading bar
     */
    @NotNull
    ILoadingBar createLoadingBar(@NotNull String name);

    /**
     * Get the current instance of hte {@code LogHandler}
     *
     * @return The current instance of hte {@code LogHandler}
     * @see ILogHandler
     */
    @NotNull
    ILogHandler getLogHandler();

    /**
     * Suggest an input
     * @param suggestion The suggestion
     */
    void suggest(String suggestion);

}
