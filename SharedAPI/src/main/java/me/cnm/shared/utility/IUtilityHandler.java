package me.cnm.shared.utility;

import me.cnm.shared.utility.configuration.IConfigurationHandler;
import me.cnm.shared.utility.format.IFormatHandler;
import org.jetbrains.annotations.NotNull;

/**
 * Class to expose utility resources
 */
public interface IUtilityHandler {

    /**
     * Get the {@code ConfigurationHandler} witch is used to read and write configuration entries from/to the disk
     *
     * @return The current instance of the {@code ConfigurationHandler}
     * @see IConfigurationHandler
     */
    @NotNull
    IConfigurationHandler getConfigurationHandler();

    /**
     * Get the {@code FormatHandler} witch is used to format values to be human-readable
     *
     * @return The current instance of the {@code FormatHandler}
     * @see IFormatHandler
     */
    @NotNull
    IFormatHandler getFormatHandler();

}
