package me.cnm.impl.shared.module;

import lombok.NonNull;
import me.cnm.impl.shared.module.java.JavaInterpreter;
import me.cnm.impl.shared.module.loading.Module;
import me.cnm.impl.shared.module.loading.ModuleInterpeterHandler;
import me.cnm.impl.shared.module.loading.ModuleLoader;
import me.cnm.shared.IHandlerLibrary;
import me.cnm.shared.cli.log.ILogHandler;
import me.cnm.shared.module.IModuleDescription;
import me.cnm.shared.module.IModuleHandler;
import me.cnm.shared.module.exception.ModuleDependencyException;
import me.cnm.shared.module.exception.ModuleDescriptionNotFoundException;
import me.cnm.shared.module.exception.ModuleInterpreterException;
import me.cnm.shared.module.loading.IModule;
import me.cnm.shared.module.loading.IModuleInterpreter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ModuleHandler extends ModuleInterpeterHandler implements IModuleHandler {

    private final Map<String, IModule> modules = new HashMap<>();

    private final ModuleLoader moduleLoader;

    private final IHandlerLibrary handlerLibrary;

    public ModuleHandler(IHandlerLibrary handlerLibrary) {
        this.handlerLibrary = handlerLibrary;

        this.registerInterpreter("java", new JavaInterpreter(this.handlerLibrary));

        this.moduleLoader = new ModuleLoader(this.handlerLibrary.getHandler(ILogHandler.class), this);
    }

    public void start() {
        this.moduleLoader.load();
    }

    public void stop() {
        this.moduleLoader.stop();
    }

    @Override
    @NotNull
    public IModule loadModule(@NonNull File file) throws ModuleInterpreterException {
        IModule module = this.createModule(file);
        this.loadWithInterpreter(module);
        return module;
    }

    public IModule createModule(File file) {
        if (!file.isDirectory()) throw new IllegalArgumentException(file.getAbsolutePath() + " is not a directory.");

        File moduleJson = new File(file, "module.json");
        if (!moduleJson.exists()) throw new ModuleDescriptionNotFoundException(moduleJson.getAbsolutePath());

        IModuleDescription moduleDescription = new ModuleDescription(moduleJson);
        return new Module(moduleDescription, file);
    }

    public void loadWithInterpreter(IModule module) throws ModuleInterpreterException {
        IModuleDescription description = module.getModuleDescription();
        String name = description.getName();

        for (String dependency : description.getDependencies()) {
            if (this.get(dependency) == null) throw new ModuleDependencyException(name, dependency, false,
                    ModuleDependencyException.Type.LOADED);
        }

        for (String dependency : description.getSoftDependencies()) {
            if (this.get(dependency) == null) {
                try {
                    throw new ModuleDependencyException(name, dependency, true, ModuleDependencyException.Type.LOADED);
                } catch (ModuleDependencyException e) {
                    this.handlerLibrary.getHandler(ILogHandler.class).warn(e.getMessage());
                }
            }
        }

        IModuleInterpreter interpreter = this.getInterpreter(description.getLanguage());
        if (interpreter == null)
            throw new ModuleDependencyException(name, description + " (interpreter)", true,
                    ModuleDependencyException.Type.LOADED);

        this.modules.put(description.getName(), module);
        interpreter.loadModule(module);
    }

    @Override
    public void startModule(@NonNull IModule module) throws ModuleInterpreterException {
        String name = module.getModuleDescription().getName();

        for (String dependency : module.getModuleDescription().getDependencies()) {
            if (!Objects.requireNonNull(this.get(dependency)).isRunning())
                throw new ModuleDependencyException(name, dependency, false, ModuleDependencyException.Type.STARTED);

        }

        for (String dependency : module.getModuleDescription().getSoftDependencies()) {
            if (!Objects.requireNonNull(this.get(dependency)).isRunning()) {
                try {
                    throw new ModuleDependencyException(name, dependency, true, ModuleDependencyException.Type.STARTED);
                } catch (ModuleDependencyException e) {
                    this.handlerLibrary.getHandler(ILogHandler.class).warn(e.getMessage());
                }
            }
        }

        IModuleInterpreter interpreter = this.getInterpreter(module.getModuleDescription().getLanguage());
        Objects.requireNonNull(interpreter).startModule(module);
    }

    @Override
    public void stopModule(@NonNull IModule module) throws ModuleInterpreterException {
        String name = module.getModuleDescription().getName();

        for (IModule targetModule : this.getAll()) {
            String dependency = targetModule.getModuleDescription().getName();

            if (targetModule.getModuleDescription().getDependencies().contains(name) && targetModule.isRunning())
                throw new ModuleDependencyException(name, dependency, false, ModuleDependencyException.Type.STOPPED);

            if (targetModule.getModuleDescription().getSoftDependencies().contains(name) && targetModule.isRunning()) {
                try {
                    throw new ModuleDependencyException(name, dependency, true, ModuleDependencyException.Type.STOPPED);
                } catch (ModuleDependencyException e) {
                    this.handlerLibrary.getHandler(ILogHandler.class).warn(e.getMessage());
                }
            }
        }

        IModuleInterpreter interpreter = this.getInterpreter(module.getModuleDescription().getLanguage());
        Objects.requireNonNull(interpreter).stopModule(module);
    }

    @Override
    public void unloadModule(@NonNull IModule module) throws ModuleInterpreterException {
        String name = module.getModuleDescription().getName();

        for (IModule targetModule : this.getAll()) {
            String dependency = targetModule.getModuleDescription().getName();

            if (targetModule.getModuleDescription().getDependencies().contains(name))
                throw new ModuleDependencyException(name, dependency, false, ModuleDependencyException.Type.UNLOADED);

            if (targetModule.getModuleDescription().getSoftDependencies().contains(name)) {
                try {
                    throw new ModuleDependencyException(name, dependency, true, ModuleDependencyException.Type.UNLOADED);
                } catch (ModuleDependencyException e) {
                    this.handlerLibrary.getHandler(ILogHandler.class).warn(e.getMessage());
                }
            }
        }

        IModuleInterpreter interpreter = this.getInterpreter(module.getModuleDescription().getLanguage());
        Objects.requireNonNull(interpreter).unloadModule(module);
    }

    @Override
    @Nullable
    public IModule get(@NonNull String name) {
        return this.modules.get(name);
    }

    @Override
    @NotNull
    public List<IModule> getAll() {
        return List.copyOf(this.modules.values());
    }

}
