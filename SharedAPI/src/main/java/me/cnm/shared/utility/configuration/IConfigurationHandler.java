package me.cnm.shared.utility.configuration;

import com.google.gson.JsonSyntaxException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Interface for handling configurations, stored on the device (file)
 */
public interface IConfigurationHandler {

    /**
     * Get the entry of a configuration by its key and map it to the corresponding type<br>
     * If the value doesn't exist null is returned
     *
     * @param key   The key of the entry
     * @param clazz The class of the type to witch it should be mapped
     * @param <T>   The type to witch the value should be mapped
     * @return The mapped value or null
     * @throws JsonSyntaxException If the value is from another type
     */
    @Nullable <T> T getEntry(@NotNull String key, @NotNull Class<T> clazz);

    /**
     * Get the entry of a configuration by its key and map it to the corresponding type<br>
     * If the value doesn't exist the {@code def} value is stored to the configuration and returned
     *
     * @param key   The key of the entry
     * @param def   The default value
     * @param clazz The class of the type to witch it should be mapped
     * @param <T>   The type to witch the value should be mapped
     * @return The mapped value or the default value
     * @throws ClassCastException If the value is from another type
     * @throws RuntimeException If the file doesn't exist
     */
    @NotNull <T> T getEntry(@NotNull String key, @NotNull T def, @NotNull Class<T> clazz);

    /**
     * Saves an entry to the configuration and store it to the file system
     *
     * @param key   The key of the entry
     * @param value The value of the entry
     * @throws RuntimeException If the file doesn't exist
     */
    void saveEntry(@NotNull String key, @NotNull Object value);

}
