package me.cnm.shared.cli.log;

/**
 * The log level provide, of witch kind a message is
 */
public enum LogLevel {

    /**
     * A debug-message is used to send messages, witch are not important for the daily business
     */
    DEBUG,

    /**
     * An info-message should be read by the user, but is not very important
     */
    INFO,

    /**
     * A warn-message is used to signal the user that some did go wrong, but the programm can run along
     */
    WARN,

    /**
     * An error-message is used to signal the user that a major bug occurred that should be reported
     */
    ERROR

}
