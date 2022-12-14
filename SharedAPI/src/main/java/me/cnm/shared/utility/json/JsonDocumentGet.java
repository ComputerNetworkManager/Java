package me.cnm.shared.utility.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import lombok.NonNull;
import me.cnm.shared.utility.scope.Scopes;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Class to provide all get methods to a json document
 *
 * @param <T> Should be the highest class witch extends this class, if not, a {@link ClassCastException} is thrown while
 *            creating an instance
 * @see JsonDocument
 */
public abstract class JsonDocumentGet<T extends JsonDocumentGet<?>> extends JsonDocumentAppend<T> {

    /**
     * Check if the {@code Document} has an entry with the specified key
     *
     * @param key The key to check for
     * @return Whether the {@code Document} has an entry or not
     */
    @Contract(pure = true)
    public boolean contains(@NonNull String key) {
        return this.getJsonObject().has(key);
    }

    /**
     * Get a {@link JsonDocument} entry using the key that represents it
     *
     * @param key The key that represents the entry
     * @return The {@link JsonDocument} represented by the key, or null if the key does not exist
     * @throws NullPointerException     If {@code key} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @Nullable
    @Contract(pure = true)
    public JsonDocument getDocument(@NonNull String key) {
        return Scopes.ifNotNull(this.getJsonObject().get(key), element ->
                element.isJsonObject() ? new JsonDocument(element) : null);
    }

    /**
     * Get a {@link JsonObject} entry using the key that represents it
     *
     * @param key The key that represents the entry
     * @return The {@link JsonObject} represented by the key, or null if the key does not exist
     * @throws NullPointerException     If {@code key} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @Nullable
    @Contract(pure = true)
    public JsonObject getObject(@NonNull String key) {
        return Scopes.ifNotNull(this.getJsonObject().get(key), element ->
                element.isJsonObject() ? element.getAsJsonObject() : null);
    }

    /**
     * Get a {@link JsonPrimitive} entry using the key that represents it
     *
     * @param key The key that represents the entry
     * @return The {@link JsonDocument} represented by the key, or null if the key does not exist
     * @throws NullPointerException     If {@code key} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @Nullable
    @Contract(pure = true)
    public JsonPrimitive getPrimitive(@NonNull String key) {
        return Scopes.ifNotNull(this.getJsonObject().get(key), element ->
                element.isJsonPrimitive() ? element.getAsJsonPrimitive() : null);
    }

    /**
     * Get a {@link Integer} entry using the key that represents it
     *
     * @param key The key that represents the entry
     * @return The {@link Integer} represented by the key, or null if the key does not exist
     * @throws NullPointerException     If {@code key} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @Nullable
    @Contract(pure = true)
    public Integer getInt(@NonNull String key) {
        return Scopes.ifNotNull(this.getPrimitive(key), primitive ->
                primitive.isNumber() ? primitive.getAsInt() : null);
    }

    /**
     * Get a {@link Double} entry using the key that represents it
     *
     * @param key The key that represents the entry
     * @return The {@link Double} represented by the key, or null if the key does not exist
     * @throws NullPointerException     If {@code key} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @Nullable
    @Contract(pure = true)
    public Double getDouble(@NonNull String key) {
        return Scopes.ifNotNull(this.getPrimitive(key), primitive ->
                primitive.isNumber() ? primitive.getAsDouble() : null);
    }

    /**
     * Get a {@link Float} entry using the key that represents it
     *
     * @param key The key that represents the entry
     * @return The {@link Float} represented by the key, or null if the key does not exist
     * @throws NullPointerException     If {@code key} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @Nullable
    @Contract(pure = true)
    public Float getFloat(@NonNull String key) {
        return Scopes.ifNotNull(this.getPrimitive(key), primitive ->
                primitive.isNumber() ? primitive.getAsFloat() : null);
    }

    /**
     * Get a {@link Byte} entry using the key that represents it
     *
     * @param key The key that represents the entry
     * @return The {@link Byte} represented by the key, or null if the key does not exist
     * @throws NullPointerException     If {@code key} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @Nullable
    @Contract(pure = true)
    public Byte getByte(@NonNull String key) {
        return Scopes.ifNotNull(this.getPrimitive(key), primitive ->
                primitive.isNumber() ? primitive.getAsByte() : null);
    }

    /**
     * Get a {@link Short} entry using the key that represents it
     *
     * @param key The key that represents the entry
     * @return The {@link Short} represented by the key, or null if the key does not exist
     * @throws NullPointerException     If {@code key} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @Nullable
    @Contract(pure = true)
    public Short getShort(@NonNull String key) {
        return Scopes.ifNotNull(this.getPrimitive(key), primitive ->
                primitive.isNumber() ? primitive.getAsShort() : null);
    }

    /**
     * Get a {@link Long} entry using the key that represents it
     *
     * @param key The key that represents the entry
     * @return The {@link Long} represented by the key, or null if the key does not exist
     * @throws NullPointerException     If {@code key} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @Nullable
    @Contract(pure = true)
    public Long getLong(@NonNull String key) {
        return Scopes.ifNotNull(this.getPrimitive(key), primitive ->
                primitive.isNumber() ? primitive.getAsLong() : null);
    }

    /**
     * Get a {@link Boolean} entry using the key that represents it
     *
     * @param key The key that represents the entry
     * @return The {@link Boolean} represented by the key, or null if the key does not exist
     * @throws NullPointerException     If {@code key} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @Nullable
    @Contract(pure = true)
    public Boolean getBoolean(@NonNull String key) {
        return Scopes.ifNotNull(this.getPrimitive(key), primitive ->
                primitive.isNumber() ? primitive.getAsBoolean() : null);
    }

    /**
     * Get a {@link String} entry using the key that represents it
     *
     * @param key The key that represents the entry
     * @return The {@link String} represented by the key, or null if the key does not exist
     * @throws NullPointerException     If {@code key} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @Nullable
    @Contract(pure = true)
    public String getString(@NonNull String key) {
        return Scopes.ifNotNull(this.getPrimitive(key), primitive ->
                primitive.isString() ? primitive.getAsString() : null);
    }

    /**
     * Get a {@link Character} entry using the key that represents it
     *
     * @param key The key that represents the entry
     * @return The {@link Character} represented by the key, or null if the key does not exist
     * @throws NullPointerException     If {@code key} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @Nullable
    @Contract(pure = true)
    public Character getChar(@NonNull String key) {
        return Scopes.ifNotNull(this.getPrimitive(key), primitive ->
                primitive.isNumber() ? primitive.getAsCharacter() : null);
    }

    /**
     * Get a {@link BigDecimal} entry using the key that represents it
     *
     * @param key The key that represents the entry
     * @return The {@link BigDecimal} represented by the key, or null if the key does not exist
     * @throws NullPointerException     If {@code key} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @Nullable
    @Contract(pure = true)
    public BigDecimal getBigDecimal(@NonNull String key) {
        return Scopes.ifNotNull(this.getPrimitive(key), primitive ->
                primitive.isNumber() ? primitive.getAsBigDecimal() : null);
    }

    /**
     * Get a {@link BigInteger} entry using the key that represents it
     *
     * @param key The key that represents the entry
     * @return The {@link BigInteger} represented by the key, or null if the key does not exist
     * @throws NullPointerException     If {@code key} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @Nullable
    @Contract(pure = true)
    public BigInteger getBigInteger(@NonNull String key) {
        return Scopes.ifNotNull(this.getPrimitive(key), primitive ->
                primitive.isNumber() ? primitive.getAsBigInteger() : null);
    }

    /**
     * Get a {@link JsonArray} entry using the key that represents it
     *
     * @param key The key that represents the entry
     * @return The {@link JsonArray} represented by the key, or null if the key does not exist
     * @throws NullPointerException     If {@code key} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @Nullable
    @Contract(pure = true)
    public JsonArray getArray(@NonNull String key) {
        return Scopes.ifNotNull(this.getJsonObject().get(key), element ->
                element.isJsonArray() ? element.getAsJsonArray() : null);
    }

    /**
     * Get a {@link JsonElement} entry using the key that represents it
     *
     * @param key The key that represents the entry
     * @return The {@link JsonElement} represented by the key, or null if the key does not exist
     * @throws NullPointerException     If {@code key} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @Nullable
    @Contract(pure = true)
    public JsonElement get(@NonNull String key) {
        return this.getJsonObject().get(key);
    }

    /**
     * Get an entry of unknown type using the key that represents it
     *
     * @param key   The key that represents the entry
     * @param clazz The {@link Class} of the type
     * @param <U>   The type of the entry
     * @return The entry represented by the key, or null if the key does not exist
     * @throws NullPointerException     If {@code key}, {@code gson} or {@code clazz} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @Nullable
    @Contract(pure = true)
    public <U> U get(@NonNull String key, @NonNull Class<U> clazz) {
        return Scopes.ifNotNull(this.getJsonObject().get(key), element -> JsonDocument.getGson().fromJson(element, clazz));
    }

    /**
     * Get the json object of the json document
     *
     * @return The json object of the json document
     */
    @ApiStatus.Internal
    protected abstract JsonObject getJsonObject();


}
