package me.cnm.shared.cli.command;

/**
 * Exception thrown when the registration of a command fails
 */
public class CommandRegistrationException extends RuntimeException {

    /**
     * Create the exception
     *
     * @param message The message containing what went wrong
     */
    public CommandRegistrationException(String message) {
        super(message);
    }

}
