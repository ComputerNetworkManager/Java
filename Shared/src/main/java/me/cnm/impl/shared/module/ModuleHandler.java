package me.cnm.impl.shared.module;

import lombok.NonNull;
import me.cnm.impl.shared.module.loading.Module;
import me.cnm.impl.shared.module.loading.ModuleInterpreterHolder;
import me.cnm.impl.shared.module.loading.ModuleLoader;
import me.cnm.shared.IHandlerLibrary;
import me.cnm.shared.cli.log.ILogHandler;
import me.cnm.shared.module.IModuleDescription;
import me.cnm.shared.module.IModuleHandler;
import me.cnm.shared.module.loading.IModule;
import me.cnm.shared.module.loading.IModuleInterpreter;
import me.cnm.shared.utility.helper.ArrayHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.*;
import java.util.stream.Stream;

public class ModuleHandler implements IModuleHandler {

    private final List<ModuleInterpreterHolder> moduleInterpreters = new ArrayList<>();
    private final Map<String, IModule> modules = new HashMap<>();

    private final IHandlerLibrary handlerLibrary;

    public ModuleHandler(IHandlerLibrary handlerLibrary) {
        this.handlerLibrary = handlerLibrary;

        ModuleLoader moduleLoader = new ModuleLoader(this.handlerLibrary.getHandler(ILogHandler.class), this);
        moduleLoader.load();
        Runtime.getRuntime().addShutdownHook(new Thread(moduleLoader::stop));
    }

    @Override
    @NotNull
    public IModule loadModule(@NonNull File file) {
        IModule module = this.createModule(file);
        this.loadWithInterpreter(module);
        return module;
    }

    public IModule createModule(File file) {
        if (!file.isDirectory()) throw new IllegalArgumentException(file.getAbsolutePath() + " is not a directory.");

        File moduleJson = new File(file, "module.json");
        if (!moduleJson.exists()) throw new IllegalStateException(file.getName() + " doesn't has a module.json");

        IModuleDescription moduleDescription = new ModuleDescription(moduleJson);
        return new Module(moduleDescription, file);
    }

    public void loadWithInterpreter(IModule module) {
        IModuleDescription description = module.getModuleDescription();

        for (String dependency : description.getDependencies()) {
            if (this.get(dependency) == null) throw new IllegalStateException("The dependency " + dependency +
                    " (used by " + description.getName() + ") wasn't loaded.");
        }

        for (String dependency : description.getSoftDependencies()) {
            this.handlerLibrary.getHandler(ILogHandler.class).warn("The soft dependency " + dependency +
                    " (used by " + description.getName() + ") wasn't loaded.");
        }

        IModuleInterpreter interpreter = this.getInterpreter(description.getLanguage());
        if (interpreter == null)
            throw new IllegalStateException("No interpreter for " + description.getLanguage() +
                    " (used by " + description.getName() + ") was registered.");

        this.modules.put(description.getName(), module);
        interpreter.loadModule(module);
    }

    @Override
    public void startModule(@NonNull IModule module) {
        for (String dependency : module.getModuleDescription().getDependencies()) {
            if (Objects.requireNonNull(this.get(dependency)).isRunning())
                throw new IllegalStateException("The dependency " + dependency + " (used by " +
                        module.getModuleDescription().getName() + ") wasn't started.");
        }

        for (String dependency : module.getModuleDescription().getSoftDependencies()) {
            if (Objects.requireNonNull(this.get(dependency)).isRunning())
                this.handlerLibrary.getHandler(ILogHandler.class).warn("The soft dependency " + dependency +
                        " (used by " + module.getModuleDescription().getName() + ") wasn't started.");
        }

        IModuleInterpreter interpreter = this.getInterpreter(module.getModuleDescription().getLanguage());
        Objects.requireNonNull(interpreter).startModule(module);
    }

    @Override
    public void stopModule(@NonNull IModule module) {
        String name = module.getModuleDescription().getName();

        for (IModule targetModule : this.getAll()) {
            String dependency = targetModule.getModuleDescription().getName();

            if (targetModule.getModuleDescription().getDependencies().contains(name) && targetModule.isRunning())
                throw new IllegalStateException("The dependency " + dependency + " (used by " +
                        name + ") wasn't stopped.");

            if (targetModule.getModuleDescription().getSoftDependencies().contains(name) && targetModule.isRunning())
                this.handlerLibrary.getHandler(ILogHandler.class).warn("The soft dependency " + dependency +
                        " (used by " + name + ") wasn't stopped.");
        }

        IModuleInterpreter interpreter = this.getInterpreter(module.getModuleDescription().getLanguage());
        Objects.requireNonNull(interpreter).stopModule(module);
    }

    @Override
    public void unloadModule(@NonNull IModule module) {
        String name = module.getModuleDescription().getName();

        for (IModule targetModule : this.getAll()) {
            String dependency = targetModule.getModuleDescription().getName();

            if (targetModule.getModuleDescription().getDependencies().contains(name))
                throw new IllegalStateException("The dependency " + dependency + " (used by " +
                        name + ") wasn't unloaded.");

            if (targetModule.getModuleDescription().getSoftDependencies().contains(name))
                this.handlerLibrary.getHandler(ILogHandler.class).warn("The soft dependency " + dependency +
                        " (used by " + name + ") wasn't unloaded.");
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

    @Override
    public void registerInterpreter(@NonNull String language, @NonNull IModuleInterpreter interpreter, @NonNull String... aliases) {
        for (ModuleInterpreterHolder moduleInterpreter : this.moduleInterpreters) {
            if (ArrayHelper.containsIgnoreCase(moduleInterpreter.getLanguages().toArray(String[]::new), language) ||
                    ArrayHelper.containsIgnoreCase(moduleInterpreter.getLanguages().toArray(String[]::new), aliases))
                throw new IllegalStateException("An module interpreter for" + language + "/" +
                        String.join(", ", aliases) + "already exists.");
        }

        this.moduleInterpreters.add(new ModuleInterpreterHolder(interpreter,
                Stream.of(new String[] { language}, aliases)
                        .flatMap(Stream::of)
                        .toList()));
    }

    @Override
    @Nullable
    public IModuleInterpreter getInterpreter(@NonNull String language) {
        for (ModuleInterpreterHolder holder : this.moduleInterpreters) {
            if (ArrayHelper.containsIgnoreCase(holder.getLanguages().toArray(String[]::new), language))
                return holder.getModuleInterpreter();
        }

        return null;
    }

}
