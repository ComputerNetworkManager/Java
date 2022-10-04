package me.cnm.impl.shared.bootstrap;

import me.cnm.impl.shared.HandlerLibrary;
import me.cnm.impl.shared.cli.CLIHandler;
import me.cnm.impl.shared.module.ModuleHandler;
import me.cnm.impl.shared.utility.UtilityHandler;
import me.cnm.shared.IHandlerLibrary;
import me.cnm.shared.cli.ICLIHandler;
import me.cnm.shared.cli.command.ICommandHandler;
import me.cnm.shared.cli.log.ILogHandler;
import me.cnm.shared.module.IModuleHandler;
import me.cnm.shared.utility.IUtilityHandler;
import me.cnm.shared.utility.configuration.IConfigurationHandler;
import me.cnm.shared.utility.format.IFormatHandler;

public class Bootstrap {

    public IHandlerLibrary createHandlerLibrary() {
        IHandlerLibrary handlerLibrary = new HandlerLibrary();

        // Register utility handlers
        handlerLibrary.registerHandler(IUtilityHandler.class, new UtilityHandler());
        IUtilityHandler utilityHandler = handlerLibrary.getHandler(IUtilityHandler.class);
        handlerLibrary.registerHandler(IConfigurationHandler.class, utilityHandler.getConfigurationHandler());
        handlerLibrary.registerHandler(IFormatHandler.class, utilityHandler.getFormatHandler());

        // Register CLI handlers
        ICLIHandler cliHandler = new CLIHandler(handlerLibrary);
        handlerLibrary.registerHandler(ICLIHandler.class, cliHandler);
        handlerLibrary.registerHandler(ICommandHandler.class, cliHandler.getCommandHandler());
        handlerLibrary.registerHandler(ILogHandler.class, cliHandler.getLogHandler());

        // Register module handler
        handlerLibrary.registerHandler(IModuleHandler.class, new ModuleHandler(handlerLibrary));

        return handlerLibrary;
    }

}
