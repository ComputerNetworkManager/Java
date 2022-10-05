package me.cnm.impl.shared.bootstrap;

import lombok.Getter;
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

    @Getter
    private final IHandlerLibrary handlerLibrary = new HandlerLibrary();

    public void registerHandlers() {
        // Register utility handlers
        IUtilityHandler utilityHandler = new UtilityHandler();
        this.handlerLibrary.registerHandler(IUtilityHandler.class, utilityHandler);
        this.handlerLibrary.registerHandler(IConfigurationHandler.class, utilityHandler.getConfigurationHandler());
        this.handlerLibrary.registerHandler(IFormatHandler.class, utilityHandler.getFormatHandler());

        // Register CLI handlers
        ICLIHandler cliHandler = new CLIHandler(this.handlerLibrary);
        this.handlerLibrary.registerHandler(ICLIHandler.class, cliHandler);
        this.handlerLibrary.registerHandler(ICommandHandler.class, cliHandler.getCommandHandler());
        this.handlerLibrary.registerHandler(ILogHandler.class, cliHandler.getLogHandler());

        // Register module handler
        this.handlerLibrary.registerHandler(IModuleHandler.class, new ModuleHandler(this.handlerLibrary));
    }

    public void start() {
        ((CLIHandler) this.handlerLibrary.getHandler(ICLIHandler.class)).start();
        ((ModuleHandler) this.handlerLibrary.getHandler(IModuleHandler.class)).start();
    }

    public void stop() {
        ((ModuleHandler) this.handlerLibrary.getHandler(IModuleHandler.class)).stop();
    }

}
