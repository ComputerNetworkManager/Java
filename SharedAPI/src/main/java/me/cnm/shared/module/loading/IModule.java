package me.cnm.shared.module.loading;

import me.cnm.shared.module.IModuleDescription;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * The {@code Module} class stores information about a loaded module
 */
public interface IModule {

    /**
     * Get the {@code module.json} of the module
     * @return The {@code module description of the module}
     * @see IModuleDescription
     */
    @NotNull
    IModuleDescription getModuleDescription();

    /**
     * Get the data folder of the module (The "root" of the module)
     * @return The data folder of the module
     */
    @NotNull
    File getDataFolder();

    /**
     * @return Whether the module is running
     */
    boolean isRunning();

    /**
     * Set the state of the module
     * @param running Whether the module is running
     */
    void setRunning(boolean running);
}
