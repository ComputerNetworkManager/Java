package me.cnm.shared.module.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ModuleDependencyException extends RuntimeException {

    public ModuleDependencyException(String module, String dependency, boolean soft, Type required) {
        super("The " + (soft ? "soft-" : "") + "dependency " + dependency + " (used by " + module + ") isn't "
                + required.getName());
    }

    @Getter
    @RequiredArgsConstructor
    public enum Type {

        LOADED("loaded"),
        STARTED("started"),
        STOPPED("stopped"),
        UNLOADED("unloaded");

        private final String name;

    }

}
