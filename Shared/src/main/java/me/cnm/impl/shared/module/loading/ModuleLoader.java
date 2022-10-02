package me.cnm.impl.shared.module.loading;

import lombok.RequiredArgsConstructor;
import me.cnm.impl.shared.module.ModuleHandler;
import me.cnm.shared.cli.log.ILogHandler;
import me.cnm.shared.module.loading.IModule;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ModuleLoader {

    private final ILogHandler logHandler;
    private final ModuleHandler moduleHandler;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void load() {
        File moduleDirectory = new File("modules");
        if (!moduleDirectory.exists()) moduleDirectory.mkdirs();

        String[] directories = moduleDirectory.list((dir, name) -> new File(dir, name).isDirectory());
        if (directories == null) return;

        List<IModule> toLoadModules = Arrays.stream(directories)
                .map(name -> new File(moduleDirectory, name))
                .map(file -> {
                    try {
                        IModule module = this.moduleHandler.createModule(file);
                        this.logHandler.info("Module " + module.getModuleDescription().getName() + " found.");
                        return module;
                    } catch (Exception e) {
                        this.logHandler.error("An error occurred while trying to read a module.json", e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList::new));

        int counter = 0;
        while (counter++ < 3 && toLoadModules.size() > 0) {
            List<IModule> loaded = new ArrayList<>();

            for (IModule toLoadModule : toLoadModules) {
                if (this.getModuleLoadIndex(toLoadModule) <= counter) {
                    try {
                        this.moduleHandler.loadWithInterpreter(toLoadModule);
                    } catch (Exception e) {
                        this.logHandler.error("An error occurred while trying to load the module " +
                                toLoadModule.getModuleDescription().getName(), e);
                    }
                    loaded.add(toLoadModule);
                    counter = 0;
                }
            }

            toLoadModules.removeAll(loaded);
            loaded.clear();
        }

        if (toLoadModules.size() > 0) {
            this.logHandler.error("Not all modules could be loaded.");
            this.logHandler.error("Maybe there dependencies are not installed?");
            this.logHandler.error("The following modules are affected:");
            for (IModule toLoadModule : toLoadModules)
                this.logHandler.error("- " + toLoadModule.getModuleDescription().getName());
        }

        List<IModule> toStartModules = new ArrayList<>(List.copyOf(this.moduleHandler.getAll()));
        counter = 0;
        while (counter++ < 3 && toStartModules.size() > 0) {
            List<IModule> started = new ArrayList<>();

            for (IModule toStartModule : toStartModules) {
                if (this.getModuleStartIndex(toStartModule) <= counter) {
                    try {
                        this.logHandler.info("Starting " + toStartModule.getModuleDescription().getName() + "...");
                        this.moduleHandler.startModule(toStartModule);
                        this.logHandler.info("Started " + toStartModule.getModuleDescription().getName() + ".");
                    } catch (Exception e) {
                        this.logHandler.error("An error occurred while trying to start the module " +
                                toStartModule.getModuleDescription().getName(), e);
                    }
                    started.add(toStartModule);
                    counter = 0;
                }
            }

            toStartModules.removeAll(started);
        }
    }

    private int getModuleLoadIndex(IModule module) {
        if (this.moduleHandler.getInterpreter(module.getModuleDescription().getLanguage()) == null) return 3;

        for (String dependency : module.getModuleDescription().getDependencies()) {
            if (this.moduleHandler.get(dependency) == null) return 3;
        }

        for (String dependency : module.getModuleDescription().getSoftDependencies()) {
            if (this.moduleHandler.get(dependency) == null) return 2;
        }

        return 1;
    }

    private int getModuleStartIndex(IModule module) {
        if (this.moduleHandler.getInterpreter(module.getModuleDescription().getLanguage()) == null) return 3;

        for (String dependency : module.getModuleDescription().getDependencies()) {
            if (!Objects.requireNonNull(this.moduleHandler.get(dependency)).isRunning()) return 3;
        }

        for (String dependency : module.getModuleDescription().getSoftDependencies()) {
            if (!Objects.requireNonNull(this.moduleHandler.get(dependency)).isRunning()) return 2;
        }

        return 1;
    }

    public void stop() {
        List<IModule> actionModules = new ArrayList<>(List.copyOf(this.moduleHandler.getAll()));
        int counter = 0;
        while (counter++ < 3 && actionModules.size() > 0) {
            List<IModule> stopped = new ArrayList<>();

            for (IModule actionModule : actionModules) {
                if (this.getModuleStopIndex(actionModule) <= counter) {
                    try {
                        this.logHandler.info("Stopping " + actionModule.getModuleDescription().getName() + "...");
                        this.moduleHandler.stopModule(actionModule);
                        this.logHandler.info("Stopping " + actionModule.getModuleDescription().getName() + ".");
                    } catch (Exception e) {
                        this.logHandler.error("An error occurred while trying to stop the module " +
                                actionModule.getModuleDescription().getName(), e);
                    }
                    stopped.add(actionModule);
                    counter = 0;
                }
            }

            actionModules.removeAll(stopped);
        }

        actionModules = new ArrayList<>(List.copyOf(this.moduleHandler.getAll()));
        counter = 0;
        while (counter++ < 3 && actionModules.size() > 0) {
            List<IModule> unloaded = new ArrayList<>();

            for (IModule actionModule : actionModules) {
                if (this.getModuleUnloadIndex(actionModule) <= counter) {
                    try {
                        this.moduleHandler.unloadModule(actionModule);
                    } catch (Exception e) {
                        this.logHandler.error("An error occurred while trying to unload the module " +
                                actionModule.getModuleDescription().getName(), e);
                    }
                    unloaded.add(actionModule);
                    counter = 0;
                }
            }

            actionModules.removeAll(unloaded);
        }
    }

    private int getModuleStopIndex(IModule module) {
        String name = module.getModuleDescription().getName();

        for (IModule targetModule : this.moduleHandler.getAll()) {
            if (targetModule.getModuleDescription().getDependencies().contains(name) && targetModule.isRunning()) return 3;
            if (targetModule.getModuleDescription().getSoftDependencies().contains(name) && targetModule.isRunning()) return 2;
        }

        return 1;
    }

    private int getModuleUnloadIndex(IModule module) {
        String name = module.getModuleDescription().getName();

        for (IModule targetModule : this.moduleHandler.getAll()) {
            if (targetModule.getModuleDescription().getDependencies().contains(name)) return 3;
            if (targetModule.getModuleDescription().getSoftDependencies().contains(name)) return 2;
        }

        return 1;
    }


}
