package me.cnm.shared.module.loading;

public interface IModuleInterpreter {

    void loadModule(IModule module);

    void startModule(IModule module);

    void stopModule(IModule module);

    void unloadModule(IModule module);

}
