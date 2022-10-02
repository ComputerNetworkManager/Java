package me.cnm.shared.module.loading;

public interface IModuleInterpreter {

    void loadModule(IModule module) throws Exception;

    void startModule(IModule module) throws Exception;

    void stopModule(IModule module) throws Exception;

    void unloadModule(IModule module) throws Exception;

}
