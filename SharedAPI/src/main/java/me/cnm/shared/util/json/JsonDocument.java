package me.cnm.shared.util.json;

import com.google.gson.*;
import lombok.Cleanup;
import lombok.NonNull;
import me.cnm.shared.util.scope.Scopes;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public class JsonDocument {

    protected JsonObject jsonObject;

    public JsonDocument(@NonNull JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JsonDocument(@NonNull JsonElement jsonElement) {
        if (jsonElement.isJsonObject()) this.jsonObject = jsonElement.getAsJsonObject();
        else this.jsonObject = new JsonObject();
    }

    public JsonDocument(@NonNull File file) throws FileNotFoundException {
        this();
        this.read(file);
    }

    public JsonDocument() {
        this(new JsonObject());
    }

    @NotNull
    public List<String> keys() {
        return new ArrayList<>(this.jsonObject.keySet());
    }

    public int size() {
        return this.jsonObject.size();
    }

    @Contract("-> this")
    public JsonDocument clear() {
        this.jsonObject = new JsonObject();
        return this;
    }

    @Contract("_ -> this")
    public JsonDocument remove(@NonNull String key) {
        this.jsonObject.remove(key);
        return this;
    }

    public boolean contains(@NonNull String key) {
        return this.jsonObject.has(key);
    }

    @NotNull
    public <T> T toInstanceOf(@NonNull Class<T> clazz) {
        return getGson().fromJson(this.jsonObject, clazz);
    }

    @NotNull
    public <T> T toInstanceOf(@NonNull Type type) {
        return getGson().fromJson(this.jsonObject, type);
    }

    @Contract("_, _ -> this")
    public JsonDocument append(@NonNull String key, @NonNull Object value) {
        this.jsonObject.add(key, getGson().toJsonTree(value));
        return this;
    }

    @Contract("_, _ -> this")
    public JsonDocument append(@NonNull String key, @NonNull String value) {
        this.jsonObject.addProperty(key, value);
        return this;
    }

    @Contract("_, _ -> this")
    public JsonDocument append(@NonNull String key, boolean value) {
        this.jsonObject.addProperty(key, value);
        return this;
    }

    @Contract("_, _ -> this")
    public JsonDocument append(@NonNull String key, @NonNull Number value) {
        this.jsonObject.addProperty(key, value);
        return this;
    }

    @Contract("_, _ -> this")
    public JsonDocument append(@NonNull String key, char value) {
        this.jsonObject.addProperty(key, value);
        return this;
    }

    @Contract("_, _ -> this")
    public JsonDocument append(@NonNull String key, JsonDocument value) {
        this.jsonObject.add(key, value.jsonObject);
        return this;
    }

    @Contract("_ -> this")
    public JsonDocument append(JsonObject jsonObject) {
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet())
            this.jsonObject.add(entry.getKey(), entry.getValue());

        return this;
    }

    @Nullable
    @Contract(pure = true)
    public JsonDocument getDocument(@NonNull String key) {
        return Scopes.ifNotNull(this.jsonObject.get(key), element ->
                element.isJsonObject() ? new JsonDocument(element) : null);
    }

    @NotNull
    @Contract(pure = true)
    public JsonDocument getDocument(@NonNull String key, @NonNull JsonDocument def) {
        if (!this.contains(key)) this.append(key, def);
        return Objects.requireNonNull(this.getDocument(key));
    }

    @Nullable
    @Contract(pure = true)
    public JsonObject getObject(@NonNull String key) {
        return Scopes.ifNotNull(this.jsonObject.get(key), element ->
                element.isJsonObject() ? element.getAsJsonObject() : null);
    }

    @NotNull
    @Contract(pure = true)
    public JsonObject getObject(@NonNull String key, @NonNull JsonObject def) {
        if (!this.contains(key)) this.append(key, def);
        return Objects.requireNonNull(this.getObject(key));
    }

    @Nullable
    @Contract(pure = true)
    public JsonPrimitive getPrimitive(@NonNull String key) {
        return Scopes.ifNotNull(this.jsonObject.get(key), element ->
                element.isJsonPrimitive() ? element.getAsJsonPrimitive() : null);
    }

    @NotNull
    @Contract(pure = true)
    public JsonPrimitive getPrimitive(@NonNull String key, @NonNull JsonPrimitive def) {
        if (!this.contains(key)) this.append(key, def);
        return Objects.requireNonNull(this.getPrimitive(key));
    }

    @Nullable
    @Contract(pure = true)
    public Integer getInt(@NonNull String key) {
        return Scopes.ifNotNull(this.getPrimitive(key), primitive ->
                primitive.isNumber() ? primitive.getAsInt() : null);
    }

    @Contract(pure = true)
    public int getInt(@NonNull String key, int def) {
        if (!this.contains(key)) this.append(key, def);
        return Objects.requireNonNull(this.getInt(key));
    }

    @Nullable
    @Contract(pure = true)
    public Double getDouble(@NonNull String key) {
        return Scopes.ifNotNull(this.getPrimitive(key), primitive ->
                primitive.isNumber() ? primitive.getAsDouble() : null);
    }

    @Contract(pure = true)
    public double getDouble(@NonNull String key, double def) {
        if (!this.contains(key)) this.append(key, def);
        return Objects.requireNonNull(this.getDouble(key));
    }

    @Nullable
    @Contract(pure = true)
    public Float getFloat(@NonNull String key) {
        return Scopes.ifNotNull(this.getPrimitive(key), primitive ->
                primitive.isNumber() ? primitive.getAsFloat() : null);
    }

    @Contract(pure = true)
    public float getFloat(@NonNull String key, float def) {
        if (!this.contains(key)) this.append(key, def);
        return Objects.requireNonNull(this.getFloat(key));
    }

    @Nullable
    @Contract(pure = true)
    public Byte getByte(@NonNull String key) {
        return Scopes.ifNotNull(this.getPrimitive(key), primitive ->
                primitive.isNumber() ? primitive.getAsByte() : null);
    }

    @Contract(pure = true)
    public byte getByte(@NonNull String key, byte def) {
        if (!this.contains(key)) this.append(key, def);
        return Objects.requireNonNull(this.getByte(key));
    }

    @Nullable
    @Contract(pure = true)
    public Short getShort(@NonNull String key) {
        return Scopes.ifNotNull(this.getPrimitive(key), primitive ->
                primitive.isNumber() ? primitive.getAsShort() : null);
    }

    @Contract(pure = true)
    public short getShort(@NonNull String key, short def) {
        if (!this.contains(key)) this.append(key, def);
        return Objects.requireNonNull(this.getShort(key));
    }

    @Nullable
    @Contract(pure = true)
    public Long getLong(@NonNull String key) {
        return Scopes.ifNotNull(this.getPrimitive(key), primitive ->
                primitive.isNumber() ? primitive.getAsLong() : null);
    }

    @Contract(pure = true)
    public long getLong(@NonNull String key, short def) {
        if (!this.contains(key)) this.append(key, def);
        return Objects.requireNonNull(this.getLong(key));
    }

    @Nullable
    @Contract(pure = true)
    public Boolean getBoolean(@NonNull String key) {
        return Scopes.ifNotNull(this.getPrimitive(key), primitive ->
                primitive.isNumber() ? primitive.getAsBoolean() : null);
    }

    @Contract(pure = true)
    public boolean getBoolean(@NonNull String key, short def) {
        if (!this.contains(key)) this.append(key, def);
        return Objects.requireNonNull(this.getBoolean(key));
    }

    @Nullable
    @Contract(pure = true)
    public String getString(@NonNull String key) {
        return Scopes.ifNotNull(this.getPrimitive(key), primitive ->
                primitive.isNumber() ? primitive.getAsString() : null);
    }

    @NotNull
    @Contract(pure = true)
    public String getString(@NonNull String key, String def) {
        if (!this.contains(key)) this.append(key, def);
        return Objects.requireNonNull(this.getString(key));
    }

    @Nullable
    @Contract(pure = true)
    public Character getChar(@NonNull String key) {
        return Scopes.ifNotNull(this.getPrimitive(key), primitive ->
                primitive.isNumber() ? primitive.getAsCharacter() : null);
    }

    @Contract(pure = true)
    public char getChar(@NonNull String key, char def) {
        if (!this.contains(key)) this.append(key, def);
        return Objects.requireNonNull(this.getChar(key));
    }

    @Nullable
    @Contract(pure = true)
    public BigDecimal getBigDecimal(@NonNull String key) {
        return Scopes.ifNotNull(this.getPrimitive(key), primitive ->
                primitive.isNumber() ? primitive.getAsBigDecimal() : null);
    }

    @NotNull
    @Contract(pure = true)
    public BigDecimal getBigDecimal(@NonNull String key, BigDecimal def) {
        if (!this.contains(key)) this.append(key, def);
        return Objects.requireNonNull(this.getBigDecimal(key));
    }

    @Nullable
    @Contract(pure = true)
    public BigInteger getBigInteger(@NonNull String key) {
        return Scopes.ifNotNull(this.getPrimitive(key), primitive ->
                primitive.isNumber() ? primitive.getAsBigInteger() : null);
    }

    @NotNull
    @Contract(pure = true)
    public BigInteger getBigInteger(@NonNull String key, BigInteger def) {
        if (!this.contains(key)) this.append(key, def);
        return Objects.requireNonNull(this.getBigInteger(key));
    }

    @Nullable
    @Contract(pure = true)
    public JsonArray getArray(@NonNull String key) {
        return Scopes.ifNotNull(this.jsonObject.get(key), element ->
                element.isJsonArray() ? element.getAsJsonArray() : null);
    }

    @NotNull
    @Contract(pure = true)
    public JsonArray getArray(@NonNull String key, @NonNull JsonArray def) {
        if (!this.contains(key)) this.append(key, def);
        return Objects.requireNonNull(this.getArray(key));
    }

    @Nullable
    @Contract(pure = true)
    public JsonElement get(@NonNull String key) {
        return this.jsonObject.get(key);
    }

    @NotNull
    @Contract(pure = true)
    public JsonElement get(@NonNull String key, JsonElement def) {
        if (!this.contains(key)) this.append(key, def);
        return this.jsonObject.get(key);
    }

    @Nullable
    @Contract(pure = true)
    public <T> T get(@NonNull String key, @NonNull Class<T> clazz) {
        return Scopes.ifNotNull(this.jsonObject.get(key), element -> getGson().fromJson(element, clazz));
    }

    @NotNull
    @Contract(pure = true)
    public <T> T get(@NonNull String key, T def, @NonNull Class<T> clazz) {
        if (!this.contains(key)) this.append(key, def);
        return Objects.requireNonNull(this.get(key, clazz));
    }

    @Contract("_ -> this")
    public JsonDocument write(@NonNull OutputStream outputStream) {
        @Cleanup OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        return this.write(outputStreamWriter);
    }

    @Contract("_ -> this")
    public JsonDocument write(@NonNull Writer writer) {
        getGson().toJson(this.jsonObject, writer);
        return this;
    }

    @Contract("_ -> this")
    public JsonDocument read(@NonNull InputStream inputStream) {
        @Cleanup InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        return this.read(inputStreamReader);
    }

    @Contract("_ -> this")
    public JsonDocument read(@NonNull Reader reader) {
        @Cleanup BufferedReader bufferedReader = new BufferedReader(reader);
        this.append(JsonParser.parseReader(bufferedReader).getAsJsonObject());

        return this;
    }

    @Contract("_ -> this")
    public JsonDocument read(String input) {
        this.append(getGson().fromJson(input, JsonObject.class));
        return this;
    }

    @Contract("_ -> this")
    public JsonDocument read(File file) throws FileNotFoundException {
        if (!Files.exists(file.toPath())) return this;

        @Cleanup FileInputStream fileInputStream = new FileInputStream(file);
        return this.read(fileInputStream);
    }

    @NotNull
    @Contract(pure = true)
    public JsonObject toJsonObject() {
        return this.jsonObject;
    }

    @NotNull
    @Contract(pure = true)
    public String toPrettyJson() {
        return getGson().toJson(this.jsonObject);
    }

    @NotNull
    @Contract(pure = true)
    public String toJson() {
        return this.jsonObject.toString();
    }

    // Static
    private static final GsonSupplier GSON_SUPPLIER = new GsonSupplier();

    public static Gson getGson() {
        return GSON_SUPPLIER.getGson();
    }

    public static void registerTypeAdapter(Class<?> clazz, Object typeAdapter) {
        GSON_SUPPLIER.registerTypeAdapter(clazz, typeAdapter);
    }


}
