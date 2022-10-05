package me.cnm.shared.module.java;

import me.cnm.shared.IHandlerLibrary;
import me.cnm.shared.module.IModuleDescription;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;

/**
 * The {@code java module} is used as base class for all modules coded in java
 */
public abstract class JavaModule {

    /**
     * The handler library, automatically set when loaded
     */
    @ApiStatus.Internal
    private final IHandlerLibrary handlerLibrary;

    /**
     * The module description, automatically set when loaded
     */
    @ApiStatus.Internal
    private final IModuleDescription moduleDescription;

    /**
     * The data folder of the module, automatically set when loaded
     */
    @ApiStatus.Internal
    private final File dataFolder;

    protected JavaModule(IHandlerLibrary handlerLibrary, IModuleDescription moduleDescription, File dataFolder) {
        this.handlerLibrary = handlerLibrary;
        this.moduleDescription = moduleDescription;
        this.dataFolder = dataFolder;
    }

    /**
     * @return The handler library
     */
    @NotNull
    @ApiStatus.NonExtendable
    public final IHandlerLibrary getHandlerLibrary() {
        return Objects.requireNonNull(this.handlerLibrary);
    }

    /**
     * @return The description of the module (module.json)
     */
    @NotNull
    @ApiStatus.NonExtendable
    public final IModuleDescription getModuleDescription() {
        return Objects.requireNonNull(this.moduleDescription);
    }

    /**
     * The data folder is the "root" folder of the module (same as the folder where the module.json is located)
     * @return The data folder of the module
     */
    @NotNull
    @ApiStatus.NonExtendable
    public final File getDataFolder() {
        return Objects.requireNonNull(this.dataFolder);
    }

    /**
     * Method called when the module should start
     */
    @ApiStatus.OverrideOnly
    @SuppressWarnings("EmptyMethod")
    public void start() {

    }

    /**
     * Method called when the module should stop<br>
     * All resources should be released in here
     */
    @ApiStatus.OverrideOnly
    @SuppressWarnings("EmptyMethod")
    public void stop() {

    }

}
