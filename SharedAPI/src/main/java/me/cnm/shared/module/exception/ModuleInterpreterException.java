package me.cnm.shared.module.exception;

import me.cnm.shared.module.loading.IModuleInterpreter;

/**
 * Execution to be thrown by an {@link IModuleInterpreter} when something goes wrong on load, start, stop or unload
 */
public class ModuleInterpreterException extends Exception {

    /**
     * Create the exception
     */
    public ModuleInterpreterException() {
    }

    /**
     * Create the exception
     *
     * @param message The message
     */
    public ModuleInterpreterException(String message) {
        super(message);
    }

    /**
     * Create the exception
     *
     * @param message The message
     * @param cause   The cause
     */
    public ModuleInterpreterException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create the exception
     *
     * @param cause The cause
     */
    public ModuleInterpreterException(Throwable cause) {
        super(cause);
    }

    /**
     * Create the exception
     *
     * @param message            The message
     * @param cause              The cause
     * @param enableSuppression  Whether to enable suppression
     * @param writableStackTrace Whether to be writeable to the stack trace
     */
    public ModuleInterpreterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
