package me.cnm.shared.utility;

import me.cnm.shared.utility.configuration.IConfigurationHandler;
import org.jetbrains.annotations.NotNull;

/**
 * Class to expose utility resources
 */
public interface IUtilityHandler {

    /**
     * Get the configuration handler, witch is used to read and write configuration entries from/to the disk
     *
     * @return The current instance of the configuration handler
     * @see IConfigurationHandler
     */
    @NotNull
    IConfigurationHandler getConfigurationHandler();

}
