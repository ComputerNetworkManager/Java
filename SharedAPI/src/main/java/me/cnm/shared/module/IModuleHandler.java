package me.cnm.shared.module;

import me.cnm.shared.module.loading.IModuleInterpreter;
import me.cnm.shared.module.loading.IModule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;

/**
 * The {@code ModuleHandler} ensures, that all important methods for handling modules are exposed<br>
 * It can be used to load, start, stop and unload modules.
 */
public interface IModuleHandler {

    /**
     * Loads a module by its directory<br>
     * If a module from this directory is already registered, it is returned<br>
     * <br>
     * For this, first the module description is read<br>
     * Then the interpreter for the language is determined<br>
     * After that the {@link IModule} class is created to store all information<br>
     * Finally the {@link IModuleInterpreter#loadModule(IModule)} is executed to load the module
     *
     * @param file The directory of the module
     * @return The module
     * @throws IllegalArgumentException If the file is not a directory
     * @throws IllegalStateException    If the module.json isn't correct or not all modules specified as
     *                                  dependencies are loaded
     * @see IModule
     */
    @NotNull
    IModule loadModule(@NotNull File file);

    /**
     * Start a module previously loaded by {@link #loadModule(File)}
     *
     * @param module The module to start
     * @throws IllegalStateException If the module is running or not all modules specified as
     *                               dependencies are started
     */
    void startModule(@NotNull IModule module);

    /**
     * Stop a module, witch means ensure, that all used resources are freed, e.g. command are unregistered etc.
     *
     * @param module The module to start
     * @throws IllegalStateException If the module is not running or some modules witch specified
     *                               this module as dependency are running
     */
    void stopModule(@NotNull IModule module);

    /**
     * Unload a module, witch mens free it from being used by the process and be allowed to delete, override, etc. module files
     *
     * @param file The directory of the module
     * @throws IllegalStateException If the module is running or some modules witch specified
     *                               this module as dependency are loaded
     */
    void unloadModule(@NotNull File file);

    /**
     * Get a module by its name<br>
     * If no module with that name is currently loaded, null is returned
     *
     * @param name The name of the module
     * @return The module or null
     */
    @Nullable
    IModule get(@NotNull String name);

    /**
     * Get all modules currently loaded
     *
     * @return List of modules loaded
     */
    @NotNull
    List<IModule> getAll();

    /**
     * Register an module interpreter to handle a language of modules
     *
     * @param language    The language to handle
     * @param interpreter The interpreter to handle the language
     * @param aliases     The aliases, also to listen for
     * @throws IllegalStateException If the language or an alias is already registered
     * @see IModuleInterpreter
     */
    void registerInterpreter(@NotNull String language, @NotNull IModuleInterpreter interpreter,
                             @NotNull String... aliases);

    /**
     * Get the interpreter for a language<br>
     * If no interpreter for this language is registered, null is returned
     *
     * @param language The language of the module
     * @return The interpreter or null
     */
    @Nullable
    IModuleInterpreter getInterpreter(@NotNull String language);

}
