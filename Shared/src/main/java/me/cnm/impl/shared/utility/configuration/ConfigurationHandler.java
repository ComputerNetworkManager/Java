package me.cnm.impl.shared.utility.configuration;

import lombok.NonNull;
import me.cnm.shared.utility.configuration.IConfigurationHandler;
import me.cnm.shared.utility.json.JsonDocument;
import me.cnm.shared.utility.scope.Scopes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Objects;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class ConfigurationHandler implements IConfigurationHandler {

    private final File file;
    public final JsonDocument configuration;

    public ConfigurationHandler() {
        this.file = new File(".", "config.json");
        if (!file.exists()) {
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            Scopes.throwRuntime(file::createNewFile);
            Scopes.throwRuntime(() -> new JsonDocument().write(file));
        }

        this.configuration = Scopes.throwRuntime(() -> new JsonDocument(this.file));
    }

    @Override
    @Nullable
    public <T> T getEntry(@NonNull String key, @NonNull Class<T> clazz) {
        if (clazz == JsonDocument.class) {
            //noinspection unchecked
            return (T) this.configuration.getDocument(key);
        }

        return this.configuration.get(key, clazz);
    }

    @Override
    @NotNull
    public <T> T getEntry(@NonNull String key, @NonNull T def, @NonNull Class<T> clazz) {
        if (!this.configuration.contains(key)) this.saveEntry(key, def);
        return Objects.requireNonNull(this.getEntry(key, clazz));
    }

    @Override
    public void saveEntry(@NonNull String key, @NonNull Object value) {
        if (value instanceof JsonDocument jsonDocument) this.configuration.append(key, jsonDocument);
        else this.configuration.append(key, value);
        Scopes.throwRuntime(() -> this.configuration.write(this.file));
    }

}
