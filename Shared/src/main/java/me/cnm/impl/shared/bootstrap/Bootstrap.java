package me.cnm.impl.shared.bootstrap;

import me.cnm.impl.shared.HandlerLibrary;
import me.cnm.impl.shared.cli.CLIHandler;
import me.cnm.impl.shared.utility.UtilityHandler;
import me.cnm.impl.shared.utility.configuration.ConfigurationHandler;
import me.cnm.shared.IHandlerLibrary;
import me.cnm.shared.cli.ICLIHandler;
import me.cnm.shared.utility.IUtilityHandler;
import me.cnm.shared.utility.configuration.IConfigurationHandler;

public class Bootstrap {

    public IHandlerLibrary createHandlerLibrary() {
        IHandlerLibrary handlerLibrary = new HandlerLibrary();

        // Register shared handlers
        handlerLibrary.registerHandler(IConfigurationHandler.class, new ConfigurationHandler());
        handlerLibrary.registerHandler(IUtilityHandler.class, new UtilityHandler());

        handlerLibrary.registerHandler(ICLIHandler.class, new CLIHandler(handlerLibrary));

        return handlerLibrary;
    }

}
