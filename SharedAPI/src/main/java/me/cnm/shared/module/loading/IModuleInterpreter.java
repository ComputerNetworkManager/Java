package me.cnm.shared.module.loading;

import me.cnm.shared.module.exception.ModuleInterpreterException;

public interface IModuleInterpreter {

    void loadModule(IModule module) throws ModuleInterpreterException;

    void startModule(IModule module) throws ModuleInterpreterException;

    void stopModule(IModule module) throws ModuleInterpreterException;

    void unloadModule(IModule module) throws ModuleInterpreterException;

}
