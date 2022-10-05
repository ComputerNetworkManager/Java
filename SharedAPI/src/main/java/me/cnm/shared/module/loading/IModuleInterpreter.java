package me.cnm.shared.module.loading;

import me.cnm.shared.module.exception.ModuleInterpreterException;

/**
 * The {@code ModuleInterpreter} handles modules that have a specific langauge specified
 */
public interface IModuleInterpreter {

    /**
     * Loads a module<br>
     * In this method, all necessary attributes in the additional part of the module.json should be checked<br>
     * Afterwards, all required files should be loaded into the java project
     *
     * @param module The module, witch should be loaded
     * @throws ModuleInterpreterException If something went wrong, the exception should be wrapped in
     *                                    an {@link ModuleInterpreterException}
     */
    void loadModule(IModule module) throws ModuleInterpreterException;

    /**
     * Starts a module<br>
     * In this method, the in the {@link #loadModule(IModule)} method loaded files should be executed<br>
     * The system ensures, that the module was loaded before
     *
     * @param module The module, witch should be started
     * @throws ModuleInterpreterException If something went wrong, the exception should be wrapped in
     *                                    an {@link ModuleInterpreterException}
     */
    void startModule(IModule module) throws ModuleInterpreterException;

    /**
     * Stops a module<br>
     * In this method, the code should be cleaned up (unregister commands, etc.)
     *
     * @param module The module, witch should be stopped
     * @throws ModuleInterpreterException If something went wrong, the exception should be wrapped in
     *                                    an {@link ModuleInterpreterException}
     */
    void stopModule(IModule module) throws ModuleInterpreterException;


    /**
     * Unloads a module<br>
     * In this method, all classed loaded in the {@link #loadModule(IModule)} method should be released
     *
     * @param module The module, witch should be unloaded
     * @throws ModuleInterpreterException If something went wrong, the exception should be wrapped in
     *                                    an {@link ModuleInterpreterException}
     */
    void unloadModule(IModule module) throws ModuleInterpreterException;

}
