package me.cnm.impl.shared.bootstrap;

import me.cnm.impl.shared.HandlerLibrary;
import me.cnm.impl.shared.cli.CLIHandler;
import me.cnm.impl.shared.utility.UtilityHandler;
import me.cnm.shared.IHandlerLibrary;
import me.cnm.shared.cli.ICLIHandler;
import me.cnm.shared.cli.command.ICommandHandler;
import me.cnm.shared.cli.log.ILogHandler;
import me.cnm.shared.utility.IUtilityHandler;
import me.cnm.shared.utility.configuration.IConfigurationHandler;
import me.cnm.shared.utility.format.IFormatHandler;

public class Bootstrap {

    public IHandlerLibrary createHandlerLibrary() {
        IHandlerLibrary handlerLibrary = new HandlerLibrary();

        // Register general handlers
        handlerLibrary.registerHandler(IUtilityHandler.class, new UtilityHandler());
        handlerLibrary.registerHandler(ICLIHandler.class, new CLIHandler(handlerLibrary));

        // Register utility handlers
        IUtilityHandler utilityHandler = handlerLibrary.getHandler(IUtilityHandler.class);
        handlerLibrary.registerHandler(IConfigurationHandler.class, utilityHandler.getConfigurationHandler());
        handlerLibrary.registerHandler(IFormatHandler.class, utilityHandler.getFormatHandler());

        // Register CLI handlers
        ICLIHandler cliHandler = handlerLibrary.getHandler(ICLIHandler.class);
        handlerLibrary.registerHandler(ICommandHandler.class, cliHandler.getCommandHandler());
        handlerLibrary.registerHandler(ILogHandler.class, cliHandler.getLogHandler());

        return handlerLibrary;
    }

}
