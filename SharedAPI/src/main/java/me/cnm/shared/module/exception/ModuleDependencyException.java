package me.cnm.shared.module.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Exception thrown when a module should be loaded, started, stopped or unloaded and some dependencies are still not
 * loaded, started, stopped or unloaded
 */
public class ModuleDependencyException extends RuntimeException {

    /**
     * Create the exception
     *
     * @param module     The module witch requires a dependency to be loaded, started, stopped or unloaded
     * @param dependency The dependency required to be loaded, started, stopped or unloaded
     * @param soft       Whether the dependency is a soft-dependency
     * @param required   What the dependency state is required to be
     */
    public ModuleDependencyException(String module, String dependency, boolean soft, Type required) {
        super("The " + (soft ? "soft-" : "") + "dependency " + dependency + " (used by " + module + ") isn't "
                + required.getName());
    }

    /**
     * Enum with all required states of a dependency
     */
    @Getter
    @RequiredArgsConstructor
    public enum Type {

        /**
         * Dependency needs to be loaded
         */
        LOADED("loaded"),

        /**
         * Dependency needs to be started
         */
        STARTED("started"),

        /**
         * Dependency needs to be stopped
         */
        STOPPED("stopped"),

        /**
         * Dependency needs to be unloaded
         */
        UNLOADED("unloaded");

        /**
         * The named to be shown in the message
         */
        private final String name;

    }

}
