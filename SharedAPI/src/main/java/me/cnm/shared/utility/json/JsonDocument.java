package me.cnm.shared.utility.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Cleanup;
import lombok.NonNull;
import me.cnm.shared.utility.scope.Scopes;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * This class simplifies the handling, conversion and much mor of java objects and json
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public class JsonDocument extends JsonDocumentGet<JsonDocument> {

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
     * Writes the content of the class to an {@link OutputStream}
     *
     * @param outputStream The {@link OutputStream} to write the content to
     * @return This instance
     * @throws NullPointerException If {@code outputStream} is null
     */
    @Contract("_ -> this")
    public JsonDocument write(@NonNull OutputStream outputStream) {
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            return this.write(outputStreamWriter);
        } catch (IOException e) {
            throw new IllegalStateException(e);
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
        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            return this.read(inputStreamReader);
        } catch (IOException e) {
            throw new IllegalStateException(e);
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
        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            this.append(JsonParser.parseReader(bufferedReader).getAsJsonObject());

            return this;
        } catch (IOException e) {
            throw new IllegalStateException(e);
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

    @Override
    public JsonObject getJsonObject() {
        return this.jsonObject;
    }

}
