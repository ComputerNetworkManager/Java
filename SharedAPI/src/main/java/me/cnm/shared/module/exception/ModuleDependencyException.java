package me.cnm.shared.module.exception;

public class ModuleDependencyException extends RuntimeException {

    public ModuleDependencyException(String module, String dependency, boolean soft, String required) {
        super("The " + (soft ? "soft-" : "") + "dependency " + dependency + " (used by " + module + ") isn't "
                + required);
    }

}
