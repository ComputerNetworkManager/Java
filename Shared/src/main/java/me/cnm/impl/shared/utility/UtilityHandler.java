package me.cnm.impl.shared.utility;

import me.cnm.impl.shared.utility.configuration.ConfigurationHandler;
import me.cnm.shared.utility.IUtilityHandler;
import me.cnm.shared.utility.configuration.IConfigurationHandler;
import org.jetbrains.annotations.NotNull;

public class UtilityHandler implements IUtilityHandler {

    private final ConfigurationHandler configurationHandler;

    public UtilityHandler() {
        configurationHandler = new ConfigurationHandler();
    }

    @Override
    @NotNull
    public IConfigurationHandler getConfigurationHandler() {
        return this.configurationHandler;
    }
}
