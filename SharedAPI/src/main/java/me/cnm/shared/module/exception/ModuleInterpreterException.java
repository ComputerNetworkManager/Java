package me.cnm.shared.module.exception;

public class ModuleInterpreterException extends Exception{

    public ModuleInterpreterException() {
    }

    public ModuleInterpreterException(String message) {
        super(message);
    }

    public ModuleInterpreterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModuleInterpreterException(Throwable cause) {
        super(cause);
    }

    public ModuleInterpreterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
