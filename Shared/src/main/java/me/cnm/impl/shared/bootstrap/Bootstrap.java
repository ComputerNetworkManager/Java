package me.cnm.impl.shared.bootstrap;

import me.cnm.impl.shared.HandlerLibrary;
import me.cnm.impl.shared.utility.UtilityHandler;
import me.cnm.shared.IHandlerLibrary;
import me.cnm.shared.utility.IUtilityHandler;

public class Bootstrap {

    public IHandlerLibrary createHandlerLibrary() {
        IHandlerLibrary handlerLibrary = new HandlerLibrary();

        // Register shared handlers
        handlerLibrary.registerHandler(IUtilityHandler.class, new UtilityHandler());

        return handlerLibrary;
    }

}
