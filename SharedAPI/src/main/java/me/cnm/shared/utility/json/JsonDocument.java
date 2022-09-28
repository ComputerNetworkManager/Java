package me.cnm.shared.utility.json;

import com.google.gson.*;
import lombok.Cleanup;
import lombok.NonNull;
import me.cnm.shared.utility.scope.Scopes;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This class simplifies the handling, conversion and much mor of java objects and json
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public class JsonDocument {


    /**
     * The {@link GsonSupplier used}
     */
    private static final GsonSupplier GSON_SUPPLIER = new GsonSupplier();

    /**
     * Creates a {@link Gson} object
     *
     * @return the {@link Gson} object
     * @see GsonSupplier#getGson()
     */
    public static Gson getGson() {
        return GSON_SUPPLIER.getGson();
    }

    /**
     * Register a new custom type adapter
     *
     * @param clazz       The {@link Class} for witch the type adapter should trigger
     * @param typeAdapter The type adapter, witch should be triggered
     * @see GsonSupplier#registerTypeAdapter(Class, Object)
     */
    public static void registerTypeAdapter(Class<?> clazz, Object typeAdapter) {
        GSON_SUPPLIER.registerTypeAdapter(clazz, typeAdapter);
    }

    /**
     * The {@link JsonObject} in which all values are stored internally
     */
    protected JsonObject jsonObject;

    /**
     * Create a new {@code Document} and take over all entries of a {@link JsonObject}
     *
     * @param jsonObject The {@link JsonObject} from which all entries should be taken
     * @throws NullPointerException If {@code jsonObject} is null
     */
    public JsonDocument(@NonNull JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    /**
     * Create a new {@code Document} and take over all entries of a {@link JsonElement}
     *
     * @param jsonElement The {@link JsonElement} from which all entries should be taken
     * @throws NullPointerException If {@code jsonElement} is null
     */
    public JsonDocument(@NonNull JsonElement jsonElement) {
        if (jsonElement.isJsonObject()) this.jsonObject = jsonElement.getAsJsonObject();
        else this.jsonObject = new JsonObject();
    }

    /**
     * Create a new {@code Document} and take over all entries stored in a json {@link File}
     *
     * @param file The {@link File} from which all entries should be taken
     * @throws NullPointerException If {@code file} is null
     */
    public JsonDocument(@NonNull File file) throws FileNotFoundException {
        this();
        this.read(file);
    }


    /**
     * Create a new {@code Document} and take over all entries stored in a json {@link String}
     *
     * @param string The {@link String} from which all entries should be taken
     */
    public JsonDocument(@NonNull String string) {
        this();
        this.read(string);
    }

    /**
     * Create a new, empty {@code Document}
     */
    public JsonDocument() {
        this(new JsonObject());
    }

    /**
     * Get all key of the underlying {@link JsonObject}
     *
     * @return All keys of the {@link JsonObject}
     */
    @NotNull
    public List<String> keys() {
        return new ArrayList<>(this.jsonObject.keySet());
    }

    /**
     * Get the of the underlying {@link JsonObject}
     *
     * @return The size of the {@link JsonObject}
     */
    public int size() {
        return this.jsonObject.size();
    }

    /**
     * Removes all values from the {@code Document}
     *
     * @return This instance
     */
    @Contract("-> this")
    public JsonDocument clear() {
        this.jsonObject = new JsonObject();
        return this;
    }

    /**
     * Removes a specific entry from the {@code Document} by its key
     *
     * @param key the key of the entry to remove
     * @return This instance
     */
    @Contract("_ -> this")
    public JsonDocument remove(@NonNull String key) {
        this.jsonObject.remove(key);
        return this;
    }

    /**
     * Check if the {@code Document} has an entry with the specified key
     *
     * @param key The key to check for
     * @return Whether the {@code Document} has an entry or not
     */
    @Contract(pure = true)
    public boolean contains(@NonNull String key) {
        return this.jsonObject.has(key);
    }

    /**
     * Add an entry with a key and any value to the {@code Document}
     *
     * @param key   The key of the entry
     * @param value The value of the entry
     * @return This instance
     */
    @Contract("_, _ -> this")
    public JsonDocument append(@NonNull String key, @NonNull Object value) {
        this.jsonObject.add(key, getGson().toJsonTree(value));
        return this;
    }


    /**
     * Add an entry with a key and a string value to the {@code Document}
     *
     * @param key   The key of the entry
     * @param value The value of the entry
     * @return This instance
     */
    @Contract("_, _ -> this")
    public JsonDocument append(@NonNull String key, @NonNull String value) {
        this.jsonObject.addProperty(key, value);
        return this;
    }

    /**
     * Add an entry with a key and a boolean value to the {@code Document}
     *
     * @param key   The key of the entry
     * @param value The value of the entry
     * @return This instance
     */
    @Contract("_, _ -> this")
    public JsonDocument append(@NonNull String key, boolean value) {
        this.jsonObject.addProperty(key, value);
        return this;
    }

    /**
     * Add an entry with a key and a number value to the {@code Document}
     *
     * @param key   The key of the entry
     * @param value The value of the entry
     * @return This instance
     */
    @Contract("_, _ -> this")
    public JsonDocument append(@NonNull String key, @NonNull Number value) {
        this.jsonObject.addProperty(key, value);
        return this;
    }

    /**
     * Add an entry with a key and a character value to the {@code Document}
     *
     * @param key   The key of the entry
     * @param value The value of the entry
     * @return This instance
     */
    @Contract("_, _ -> this")
    public JsonDocument append(@NonNull String key, char value) {
        this.jsonObject.addProperty(key, value);
        return this;
    }

    /**
     * Add an entry with a key and a {@code Document} value to the {@code Document}
     *
     * @param key   The key of the entry
     * @param value The value of the entry
     * @return This instance
     */
    @Contract("_, _ -> this")
    public JsonDocument append(@NonNull String key, JsonDocument value) {
        this.jsonObject.add(key, value.jsonObject);
        return this;
    }

    /**
     * Add all entries of another {@code JsonObject} to the {@code Document}
     *
     * @param jsonObject The {@code JsonObject} with all values
     * @return This instance
     */
    @Contract("_ -> this")
    public JsonDocument append(JsonObject jsonObject) {
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet())
            this.jsonObject.add(entry.getKey(), entry.getValue());

        return this;
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
        return Scopes.ifNotNull(this.jsonObject.get(key), element ->
                element.isJsonObject() ? new JsonDocument(element) : null);
    }

    /**
     * Get a {@link JsonDocument} entry using the key that represents it<br>
     * If the value does not exist, a default value is returned and added to the document
     *
     * @param key The key that represents the entry
     * @param def The default value to be returned
     * @return The {@link JsonDocument} represented by the key, or the default value if the key does not exist
     * @throws NullPointerException     If {@code key} or {@code def} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @NotNull
    @Contract(pure = true)
    public JsonDocument getDocument(@NonNull String key, @NonNull JsonDocument def) {
        if (!this.contains(key)) this.append(key, def);
        return Objects.requireNonNull(this.getDocument(key));
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
        return Scopes.ifNotNull(this.jsonObject.get(key), element ->
                element.isJsonObject() ? element.getAsJsonObject() : null);
    }

    /**
     * Get a {@link JsonObject} entry using the key that represents it<br>
     * If the value does not exist, a default value is returned and added to the document
     *
     * @param key The key that represents the entry
     * @param def The default value to be returned
     * @return The {@link JsonObject} represented by the key, or the default value if the key does not exist
     * @throws NullPointerException     If {@code key} or {@code def} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @NotNull
    @Contract(pure = true)
    public JsonObject getObject(@NonNull String key, @NonNull JsonObject def) {
        if (!this.contains(key)) this.append(key, def);
        return Objects.requireNonNull(this.getObject(key));
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
        return Scopes.ifNotNull(this.jsonObject.get(key), element ->
                element.isJsonPrimitive() ? element.getAsJsonPrimitive() : null);
    }

    /**
     * Get a {@link JsonPrimitive} entry using the key that represents it<br>
     * If the value does not exist, a default value is returned and added to the document
     *
     * @param key The key that represents the entry
     * @param def The default value to be returned
     * @return The {@link JsonPrimitive} represented by the key, or the default value if the key does not exist
     * @throws NullPointerException     If {@code key} or {@code def} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @NotNull
    @Contract(pure = true)
    public JsonPrimitive getPrimitive(@NonNull String key, @NonNull JsonPrimitive def) {
        if (!this.contains(key)) this.append(key, def);
        return Objects.requireNonNull(this.getPrimitive(key));
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
     * Get a {@link Integer} entry using the key that represents it<br>
     * If the value does not exist, a default value is returned and added to the document
     *
     * @param key The key that represents the entry
     * @param def The default value to be returned
     * @return The {@link Integer} represented by the key, or the default value if the key does not exist
     * @throws NullPointerException     If {@code key} or {@code def} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @Contract(pure = true)
    public int getInt(@NonNull String key, int def) {
        if (!this.contains(key)) this.append(key, def);
        return Objects.requireNonNull(this.getInt(key));
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
     * Get a {@link Double} entry using the key that represents it<br>
     * If the value does not exist, a default value is returned and added to the document
     *
     * @param key The key that represents the entry
     * @param def The default value to be returned
     * @return The {@link Double} represented by the key, or the default value if the key does not exist
     * @throws NullPointerException     If {@code key} or {@code def} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @Contract(pure = true)
    public double getDouble(@NonNull String key, double def) {
        if (!this.contains(key)) this.append(key, def);
        return Objects.requireNonNull(this.getDouble(key));
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
     * Get a {@link Float} entry using the key that represents it<br>
     * If the value does not exist, a default value is returned and added to the document
     *
     * @param key The key that represents the entry
     * @param def The default value to be returned
     * @return The {@link Float} represented by the key, or the default value if the key does not exist
     * @throws NullPointerException     If {@code key} or {@code def} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @Contract(pure = true)
    public float getFloat(@NonNull String key, float def) {
        if (!this.contains(key)) this.append(key, def);
        return Objects.requireNonNull(this.getFloat(key));
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
     * Get a {@link Byte} entry using the key that represents it<br>
     * If the value does not exist, a default value is returned and added to the document
     *
     * @param key The key that represents the entry
     * @param def The default value to be returned
     * @return The {@link Byte} represented by the key, or the default value if the key does not exist
     * @throws NullPointerException     If {@code key} or {@code def} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @Contract(pure = true)
    public byte getByte(@NonNull String key, byte def) {
        if (!this.contains(key)) this.append(key, def);
        return Objects.requireNonNull(this.getByte(key));
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
     * Get a {@link Short} entry using the key that represents it<br>
     * If the value does not exist, a default value is returned and added to the document
     *
     * @param key The key that represents the entry
     * @param def The default value to be returned
     * @return The {@link Short} represented by the key, or the default value if the key does not exist
     * @throws NullPointerException     If {@code key} or {@code def} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @Contract(pure = true)
    public short getShort(@NonNull String key, short def) {
        if (!this.contains(key)) this.append(key, def);
        return Objects.requireNonNull(this.getShort(key));
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
     * Get a {@link Long} entry using the key that represents it<br>
     * If the value does not exist, a default value is returned and added to the document
     *
     * @param key The key that represents the entry
     * @param def The default value to be returned
     * @return The {@link Long} represented by the key, or the default value if the key does not exist
     * @throws NullPointerException     If {@code key} or {@code def} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @Contract(pure = true)
    public long getLong(@NonNull String key, short def) {
        if (!this.contains(key)) this.append(key, def);
        return Objects.requireNonNull(this.getLong(key));
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
     * Get a {@link Boolean} entry using the key that represents it<br>
     * If the value does not exist, a default value is returned and added to the document
     *
     * @param key The key that represents the entry
     * @param def The default value to be returned
     * @return The {@link Boolean} represented by the key, or the default value if the key does not exist
     * @throws NullPointerException     If {@code key} or {@code def} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @Contract(pure = true)
    public boolean getBoolean(@NonNull String key, short def) {
        if (!this.contains(key)) this.append(key, def);
        return Objects.requireNonNull(this.getBoolean(key));
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
                primitive.isNumber() ? primitive.getAsString() : null);
    }

    /**
     * Get a {@link String} entry using the key that represents it<br>
     * If the value does not exist, a default value is returned and added to the document
     *
     * @param key The key that represents the entry
     * @param def The default value to be returned
     * @return The {@link String} represented by the key, or the default value if the key does not exist
     * @throws NullPointerException     If {@code key} or {@code def} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @NotNull
    @Contract(pure = true)
    public String getString(@NonNull String key, String def) {
        if (!this.contains(key)) this.append(key, def);
        return Objects.requireNonNull(this.getString(key));
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
     * Get a {@link Character} entry using the key that represents it<br>
     * If the value does not exist, a default value is returned and added to the document
     *
     * @param key The key that represents the entry
     * @param def The default value to be returned
     * @return The {@link Character} represented by the key, or the default value if the key does not exist
     * @throws NullPointerException     If {@code key} or {@code def} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @Contract(pure = true)
    public char getChar(@NonNull String key, char def) {
        if (!this.contains(key)) this.append(key, def);
        return Objects.requireNonNull(this.getChar(key));
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
     * Get a {@link BigDecimal} entry using the key that represents it<br>
     * If the value does not exist, a default value is returned and added to the document
     *
     * @param key The key that represents the entry
     * @param def The default value to be returned
     * @return The {@link BigDecimal} represented by the key, or the default value if the key does not exist
     * @throws NullPointerException     If {@code key} or {@code def} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @NotNull
    @Contract(pure = true)
    public BigDecimal getBigDecimal(@NonNull String key, BigDecimal def) {
        if (!this.contains(key)) this.append(key, def);
        return Objects.requireNonNull(this.getBigDecimal(key));
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
     * Get a {@link BigInteger} entry using the key that represents it<br>
     * If the value does not exist, a default value is returned and added to the document
     *
     * @param key The key that represents the entry
     * @param def The default value to be returned
     * @return The {@link BigInteger} represented by the key, or the default value if the key does not exist
     * @throws NullPointerException     If {@code key} or {@code def} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @NotNull
    @Contract(pure = true)
    public BigInteger getBigInteger(@NonNull String key, BigInteger def) {
        if (!this.contains(key)) this.append(key, def);
        return Objects.requireNonNull(this.getBigInteger(key));
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
        return Scopes.ifNotNull(this.jsonObject.get(key), element ->
                element.isJsonArray() ? element.getAsJsonArray() : null);
    }

    /**
     * Get a {@link JsonArray} entry using the key that represents it<br>
     * If the value does not exist, a default value is returned and added to the document
     *
     * @param key The key that represents the entry
     * @param def The default value to be returned
     * @return The {@link JsonArray} represented by the key, or the default value if the key does not exist
     * @throws NullPointerException     If {@code key} or {@code def} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @NotNull
    @Contract(pure = true)
    public JsonArray getArray(@NonNull String key, @NonNull JsonArray def) {
        if (!this.contains(key)) this.append(key, def);
        return Objects.requireNonNull(this.getArray(key));
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
        return this.jsonObject.get(key);
    }

    /**
     * Get a {@link JsonElement} entry using the key that represents it<br>
     * If the value does not exist, a default value is returned and added to the document
     *
     * @param key The key that represents the entry
     * @param def The default value to be returned
     * @return The {@link JsonElement} represented by the key, or the default value if the key does not exist
     * @throws NullPointerException     If {@code key} or {@code def} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @NotNull
    @Contract(pure = true)
    public JsonElement get(@NonNull String key, JsonElement def) {
        if (!this.contains(key)) this.append(key, def);
        return this.jsonObject.get(key);
    }

    /**
     * Get an entry of unknown type using the key that represents it
     *
     * @param key   The key that represents the entry
     * @param clazz The {@link Class} of the type
     * @param <T>   The type of the entry
     * @return The entry represented by the key, or null if the key does not exist
     * @throws NullPointerException     If {@code key}, {@code gson} or {@code clazz} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @Nullable
    @Contract(pure = true)
    public <T> T get(@NonNull String key, @NonNull Class<T> clazz) {
        return Scopes.ifNotNull(this.jsonObject.get(key), element -> getGson().fromJson(element, clazz));
    }

    /**
     * Get an entry of unknown type using the key that represents it<br>
     * If the value does not exist, a default value is returned and added to the document
     *
     * @param key   The key that represents the entry
     * @param clazz The {@link Class} of the type
     * @param <T>   The type of the entry
     * @return The entry represented by the key, or the default value if the key does not exist
     * @throws NullPointerException     If {@code key}, {@code gson} or {@code clazz} is null
     * @throws IllegalArgumentException If {@code key} is blank
     */
    @NotNull
    @Contract(pure = true)
    public <T> T get(@NonNull String key, T def, @NonNull Class<T> clazz) {
        if (!this.contains(key)) this.append(key, def);
        return Objects.requireNonNull(this.get(key, clazz));
    }

    /**
     * Writes the content of the class to an {@link OutputStream}
     *
     * @param outputStream The {@link OutputStream} to write the content to
     * @return This instance
     * @throws NullPointerException If {@code outputStream} is null
     */
    @Contract("_ -> this")
    public JsonDocument write(@NonNull OutputStream outputStream) {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        try {
            return this.write(outputStreamWriter);
        } finally {
            Scopes.throwRuntime(outputStreamWriter::close);
        }
    }

    /**
     * Writes the content of the class to an {@link Writer}
     *
     * @param writer The {@link Writer} to write the content to
     * @return This instance
     * @throws NullPointerException If {@code writer} is null
     */
    @Contract("_ -> this")
    public JsonDocument write(@NonNull Writer writer) {
        getGson().toJson(this.jsonObject, writer);
        return this;
    }

    /**
     * Writes the content of the class to an {@link File}
     *
     * @param file The {@link File} to write the content to
     * @return This instance
     * @throws NullPointerException If {@code writer} is null
     * @throws IOException          If the something goes wrong with the writing of the {@link File}
     */
    @Contract("_ -> this")
    public JsonDocument write(@NonNull File file) throws IOException {
        @Cleanup FileWriter fileWriter = new FileWriter(file);
        return this.write(fileWriter);
    }

    /**
     * Read the content of the class from an {@link InputStream}
     *
     * @param inputStream The {@link InputStream} from where to read the content
     * @return This instance
     * @throws NullPointerException If {@code inputStream} is null
     */
    @Contract("_ -> this")
    public JsonDocument read(@NonNull InputStream inputStream) {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        try {
            return this.read(inputStreamReader);
        } finally {
            Scopes.throwRuntime(inputStreamReader::close);
        }
    }

    /**
     * Read the content of the class from an {@link Reader}
     *
     * @param reader The {@link Reader} from where to read the content
     * @return This instance
     * @throws NullPointerException If {@code reader} is null
     */
    @Contract("_ -> this")
    public JsonDocument read(@NonNull Reader reader) {
        BufferedReader bufferedReader = new BufferedReader(reader);
        try {
            this.append(JsonParser.parseReader(bufferedReader).getAsJsonObject());

            return this;
        } finally {
            Scopes.throwRuntime(bufferedReader::close);
        }
    }

    @Contract("_ -> this")
    public JsonDocument read(String input) {
        this.append(getGson().fromJson(input, JsonObject.class));
        return this;
    }

    /**
     * Read the content of the class from the specified {@code File}
     *
     * @param file The {@link File} to read the content from
     * @return The instance of this {@code Readable}
     * @throws NullPointerException  If {@code file} is null
     * @throws FileNotFoundException If the file doesn't exist
     */
    @Contract("_ -> this")
    public JsonDocument read(File file) throws FileNotFoundException {
        if (!Files.exists(file.toPath())) return this;

        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            return this.read(fileInputStream);
        } finally {
            Scopes.throwRuntime(fileInputStream::close);
        }
    }

    /**
     * Converts the content of the {@code Document} to a {@link JsonObject}
     *
     * @return The {@code Document} converted to a {@link JsonObject}
     */
    @NotNull
    @Contract(pure = true)
    public JsonObject toJsonObject() {
        return this.jsonObject;
    }

    /**
     * Converts the content of the {@code Document} to nicely rendered {@code Json} (with line breaks, indentation, etc.)
     *
     * @return The {@code Document} converted to {@code Json} syntax
     */
    @NotNull
    @Contract(pure = true)
    public String toPrettyJson() {
        return getGson().toJson(this.jsonObject);
    }

    /**
     * Converts the content of the {@code Document} to {@code Json} (without line breaks, indentation, etc.)
     *
     * @return The {@code Document} converted to {@code Json} syntax
     */
    @NotNull
    @Contract(pure = true)
    public String toJson() {
        return this.jsonObject.toString();
    }


}
