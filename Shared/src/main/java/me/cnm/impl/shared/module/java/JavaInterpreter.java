package me.cnm.impl.shared.module.java;

import me.cnm.shared.IHandlerLibrary;
import me.cnm.shared.module.exception.ModuleInterpreterException;
import me.cnm.shared.module.java.JavaModule;
import me.cnm.shared.module.loading.IModule;
import me.cnm.shared.module.loading.IModuleInterpreter;
import me.cnm.shared.utility.json.JsonDocument;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JavaInterpreter implements IModuleInterpreter {

    private final IHandlerLibrary handlerLibrary;
    private final Map<IModule, ModuleInformation> modules = new HashMap<>();
    private final Map<String, Class<?>> classes = new HashMap<>();

    public JavaInterpreter(IHandlerLibrary handlerLibrary) {
        this.handlerLibrary = handlerLibrary;
    }

    @Override
    public void loadModule(IModule module) throws ModuleInterpreterException {
        JsonDocument additional = module.getModuleDescription().getAdditional();
        if (!additional.contains("main"))
            throw new IllegalStateException("The module.json of " + module.getModuleDescription().getName() + " doesn't" +
                    "contain required attribute additional->main");

        String main = additional.getString("main");
        String location = additional.getString("location", "java.jar");

        File jarFile = new File(module.getDataFolder(), location);
        if (!jarFile.exists()) {
            throw new IllegalStateException("The jar file of " + module.getModuleDescription().getName() + " doesn't " +
                    "exist.");
        }

        ModuleClassLoader classLoader;
        try {
            classLoader = new ModuleClassLoader(this, jarFile, this.getClass().getClassLoader());
        } catch (MalformedURLException e) {
            throw new ModuleInterpreterException(e);
        }

        Class<?> mainClass = classLoader.findClass(main, false);

        if (!JavaModule.class.isAssignableFrom(mainClass)) {
            throw new IllegalStateException("The main class of " + module.getModuleDescription().getName() + " doesn't " +
                    "extend from JavaModule.");
        }

        this.modules.put(module, new ModuleInformation(classLoader, mainClass.asSubclass(JavaModule.class)));
    }

    @Override
    public void startModule(IModule module) throws ModuleInterpreterException {
        try {
            ModuleInformation moduleInformation = Objects.requireNonNull(this.modules.get(module));

            JavaModule javaModule = moduleInformation.getMainClass().getDeclaredConstructor().newInstance();

            Field handlerLibraryField = JavaModule.class.getDeclaredField("handlerLibrary");
            Field moduleDescriptionField = JavaModule.class.getDeclaredField("moduleDescription");
            Field dataFolderField = JavaModule.class.getDeclaredField("dataFolder");

            handlerLibraryField.setAccessible(true);
            moduleDescriptionField.setAccessible(true);
            dataFolderField.setAccessible(true);

            handlerLibraryField.set(javaModule, this.handlerLibrary);
            moduleDescriptionField.set(javaModule, module.getModuleDescription());
            dataFolderField.set(javaModule, module.getDataFolder());

            moduleInformation.setMainInstance(javaModule);
            module.setRunning(true);
            javaModule.start();
        } catch (NoSuchMethodException | InvocationTargetException |
                InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            throw new ModuleInterpreterException(e);
        }
    }

    @Override
    public void stopModule(IModule module) {
        ModuleInformation moduleInformation = Objects.requireNonNull(this.modules.get(module));

        JavaModule javaModule = moduleInformation.getMainInstance();

        javaModule.stop();
        module.setRunning(false);
        moduleInformation.setMainInstance(null);
    }

    @Override
    public void unloadModule(IModule module) throws ModuleInterpreterException {
        ModuleInformation moduleInformation = Objects.requireNonNull(this.modules.get(module));

        ModuleClassLoader classLoader = moduleInformation.getClassLoader();
        for (String name : classLoader.getLoadedClasses().keySet()) this.classes.remove(name);

        try {
            classLoader.close();
        } catch (IOException e) {
            throw new ModuleInterpreterException(e);
        }

        this.modules.remove(module);
    }

    public Class<?> getClassByName(String name) {
        Class<?> target = this.classes.get(name);

        if (target != null) return target;

        for (ModuleInformation value : this.modules.values()) {
            target = value.getClassLoader().findClass(name, false);

            if (target != null) return target;
        }

        return null;
    }

    public void setClass(String name, Class<?> clazz) {
        this.classes.computeIfAbsent(name, key -> clazz);
    }

}
