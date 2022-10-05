package me.cnm.shared.module.exception;

public class ModuleDescriptionNotFoundException extends RuntimeException {

    public ModuleDescriptionNotFoundException(String module) {
        super("The module.json of " + module + " wan't found.");
    }

}
