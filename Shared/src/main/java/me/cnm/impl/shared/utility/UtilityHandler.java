package me.cnm.impl.shared.utility;

import lombok.Getter;
import me.cnm.impl.shared.utility.configuration.ConfigurationHandler;
import me.cnm.impl.shared.utility.format.FormatHandler;
import me.cnm.shared.utility.IUtilityHandler;
import me.cnm.shared.utility.configuration.IConfigurationHandler;
import me.cnm.shared.utility.format.IFormatHandler;

@Getter
public class UtilityHandler implements IUtilityHandler {

    private final IConfigurationHandler configurationHandler;
    private final IFormatHandler formatHandler;

    public UtilityHandler() {
        this.configurationHandler = new ConfigurationHandler();
        this.formatHandler = new FormatHandler();
    }

}
