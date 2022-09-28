package me.cnm.shared;

import org.jetbrains.annotations.NotNull;

/**
 * The {@code HandlerLibrary} is a library, that contains all handlers that are required for the system<br>
 * Each module can add individual handlers
 */
public interface IHandlerLibrary {

    /**
     * Register a handler with its type and instance<br>
     * It's highly suggested, that the type of the handler is an interface containing all important methods<br>
     * If a handler with the same interface is already registered, the handler is overwritten
     *
     * @param type     The class of type (interface) of the handler
     * @param instance The instance of the handler/interface
     * @param <T>      The type of the handler
     */
    <T> void registerHandler(@NotNull Class<T> type, @NotNull T instance);

    /**
     * Get a handler by its type
     *
     * @param type The class of type of the handler
     * @param <T>  The type of the handler
     * @return The handler
     * @throws IllegalArgumentException If no handler is found
     */
    @NotNull
    <T> T getHandler(@NotNull Class<T> type);

}
