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
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return this.findClass(name, true);
    }

    protected Class<?> findClass(String name, boolean checkGlobal) throws ClassNotFoundException {
        Class<?> result = classes.get(name);

        if (result == null) {
            if (checkGlobal) result = this.javaInterpreter.getClassByName(name);

            if (result == null) {
                result = super.findClass(name);

                if (result != null) this.javaInterpreter.setClass(name, result);
            }

            this.classes.put(name, result);
        }

        if (result == null) throw new ClassNotFoundException(name);

        return result;
    }

    public Map<String, Class<?>> getLoadedClasses() {
        return Collections.unmodifiableMap(this.classes);
    }

}
