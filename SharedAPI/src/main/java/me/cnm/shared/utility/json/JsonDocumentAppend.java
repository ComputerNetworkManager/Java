package me.cnm.shared.utility.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.NonNull;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

import java.util.Map;

/**
 * Class to provide all append methods to a json document
 *
 * @param <T> Should be the highest class witch extends this class, if not, a {@link ClassCastException} is thrown while
 *            creating an instance
 * @see JsonDocument
 */
public abstract class JsonDocumentAppend<T extends JsonDocumentAppend<?>> {

    /**
     * The instance of the json document
     */
    private final T instance;

    /**
     * Creat the class
     */
    @ApiStatus.Internal
    protected JsonDocumentAppend() {
        //noinspection unchecked
        this.instance = (T) this;
    }

    /**
     * Add an entry with a key and any value to the {@code Document}
     *
     * @param key   The key of the entry
     * @param value The value of the entry
     * @return This instance
     */
    @Contract("_, _ -> this")
    public T append(@NonNull String key, @NonNull Object value) {
        this.getJsonObject().add(key, JsonDocument.getGson().toJsonTree(value));
        return this.instance;
    }

    /**
     * Add an entry with a key and a string value to the {@code Document}
     *
     * @param key   The key of the entry
     * @param value The value of the entry
     * @return This instance
     */
    @Contract("_, _ -> this")
    public T append(@NonNull String key, @NonNull String value) {
        this.getJsonObject().addProperty(key, value);
        return this.instance;
    }

    /**
     * Add an entry with a key and a boolean value to the {@code Document}
     *
     * @param key   The key of the entry
     * @param value The value of the entry
     * @return This instance
     */
    @Contract("_, _ -> this")
    public T append(@NonNull String key, boolean value) {
        this.getJsonObject().addProperty(key, value);
        return this.instance;
    }

    /**
     * Add an entry with a key and a number value to the {@code Document}
     *
     * @param key   The key of the entry
     * @param value The value of the entry
     * @return This instance
     */
    @Contract("_, _ -> this")
    public T append(@NonNull String key, @NonNull Number value) {
        this.getJsonObject().addProperty(key, value);
        return this.instance;
    }

    /**
     * Add an entry with a key and a character value to the {@code Document}
     *
     * @param key   The key of the entry
     * @param value The value of the entry
     * @return This instance
     */
    @Contract("_, _ -> this")
    public T append(@NonNull String key, char value) {
        this.getJsonObject().addProperty(key, value);
        return this.instance;
    }

    /**
     * Add an entry with a key and a {@code Document} value to the {@code Document}
     *
     * @param key   The key of the entry
     * @param value The value of the entry
     * @return This instance
     */
    @Contract("_, _ -> this")
    public T append(@NonNull String key, JsonDocument value) {
        this.getJsonObject().add(key, value.jsonObject);
        return this.instance;
    }

    /**
     * Add all entries of another {@code JsonObject} to the {@code Document}
     *
     * @param jsonObject The {@code JsonObject} with all values
     * @return This instance
     */
    @Contract("_ -> this")
    public T append(JsonObject jsonObject) {
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet())
            this.getJsonObject().add(entry.getKey(), entry.getValue());

        return this.instance;
    }

    /**
     * Get the json object of the json document
     *
     * @return The json object of the json document
     */
    @ApiStatus.Internal
    protected abstract JsonObject getJsonObject();

}
