package me.cnm.impl.shared.module.java;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ModuleClassLoader extends URLClassLoader {

    private final JavaInterpreter javaInterpreter;
    private final Map<String, Class<?>> classes = new HashMap<>();

    public ModuleClassLoader(JavaInterpreter javaInterpreter, File jarFile, ClassLoader parent) throws MalformedURLException {
        super(new URL[]{ jarFile.toURI().toURL() }, parent);
        this.javaInterpreter = javaInterpreter;
    }

    @Override
    protected Class<?> findClass(String name) {
        return this.findClass(name, true);
    }

    protected Class<?> findClass(String name, boolean checkGlobal) {
        return this.classes.computeIfAbsent(name, key -> {
            Class<?> supply = null;
            if (checkGlobal) supply = this.javaInterpreter.getClassByName(name);

            if (supply == null) {
                try {
                    supply = super.findClass(name);
                } catch (ClassNotFoundException ignored) { }

                if (supply != null) this.javaInterpreter.setClass(name, supply);
            }

            return supply;
        });
    }

    public Map<String, Class<?>> getLoadedClasses() {
        return Collections.unmodifiableMap(this.classes);
    }

}
