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
    private final JsonDocument configuration;

    public ConfigurationHandler() {
        this.file = new File(".", "config.json");
        if (!file.exists()) {
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            Scopes.throwRuntime(file::createNewFile);
        }

        this.configuration = Scopes.throwRuntime(() -> new JsonDocument(this.file));
    }

    @Override
    @Nullable
    public <T> T getEntry(@NonNull String key, @NonNull Class<T> clazz) {
        return this.configuration.get(key, clazz);
    }

    @Override
    @NotNull
    public <T> T getEntry(@NonNull String key, @NonNull T def, @NonNull Class<T> clazz) {
        if (!this.configuration.contains(key)) {
            this.configuration.append(key, def);
            Scopes.throwRuntime(() -> this.configuration.write(this.file));
        }

        return Objects.requireNonNull(this.configuration.get(key, clazz));
    }

    @Override
    public void saveEntry(@NonNull String key, @NonNull Object value) {
        this.configuration.append(key, value);
        Scopes.throwRuntime(() -> this.configuration.write(this.file));
    }

}
