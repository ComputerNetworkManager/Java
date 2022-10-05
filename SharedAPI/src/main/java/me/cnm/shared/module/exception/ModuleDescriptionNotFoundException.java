package me.cnm.shared.module.exception;

/**
 * Method thrown when the module.json of a module directory wasn't found
 */
public class ModuleDescriptionNotFoundException extends RuntimeException {

    /**
     * @param module The module of witch the module.json wasn't found
     */
    public ModuleDescriptionNotFoundException(String module) {
        super("The module.json of " + module + " wan't found.");
    }

}
