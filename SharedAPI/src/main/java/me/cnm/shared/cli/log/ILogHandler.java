package me.cnm.shared.cli.log;

import org.jetbrains.annotations.NotNull;

/**
 * The log handler is used to log messages to the config and a file
 */
public interface ILogHandler {

    /**
     * Send a debug-message
     *
     * @param message The message
     * @see #log(LogLevel, String)
     * @see LogLevel#DEBUG
     */
    void debug(@NotNull String message);

    /**
     * Send a debug-message with a throwable
     *
     * @param message   The message
     * @param throwable The throwable
     * @see #log(LogLevel, String, Throwable)
     * @see LogLevel#DEBUG
     */
    void debug(@NotNull String message, @NotNull Throwable throwable);

    /**
     * Send an info-message
     *
     * @param message The message
     * @see #log(LogLevel, String)
     * @see LogLevel#INFO
     */
    void info(@NotNull String message);

    /**
     * Send an info-message with a throwable
     *
     * @param message   The message
     * @param throwable The throwable
     * @see #log(LogLevel, String, Throwable)
     * @see LogLevel#INFO
     */
    void info(@NotNull String message, @NotNull Throwable throwable);

    /**
     * Send a warn-message
     *
     * @param message The message
     * @see #log(LogLevel, String)
     * @see LogLevel#WARN
     */
    void warn(@NotNull String message);

    /**
     * Send a warn-message with a throwable
     *
     * @param message   The message
     * @param throwable The throwable
     * @see #log(LogLevel, String, Throwable)
     * @see LogLevel#WARN
     */
    void warn(@NotNull String message, @NotNull Throwable throwable);

    /**
     * Send an error-message
     *
     * @param message The message
     * @see #log(LogLevel, String)
     * @see LogLevel#ERROR
     */
    void error(@NotNull String message);

    /**
     * Send an error-message with a throwable
     *
     * @param message   The message
     * @param throwable The throwable
     * @see #log(LogLevel, String, Throwable)
     * @see LogLevel#ERROR
     */
    void error(@NotNull String message, @NotNull Throwable throwable);

    /**
     * Log a message to the console and the log file
     *
     * @param logLevel The level of the log
     * @param message  The message to be logged
     * @see LogLevel
     */
    void log(LogLevel logLevel, @NotNull String message);


    /**
     * Log a message to the console and the log file<br>
     * In addition also print a throwable with it
     *
     * @param logLevel  The level of the log
     * @param message   The message to be logged
     * @param throwable The throwable to be logged
     * @see LogLevel
     */
    void log(LogLevel logLevel, @NotNull String message, @NotNull Throwable throwable);

}
