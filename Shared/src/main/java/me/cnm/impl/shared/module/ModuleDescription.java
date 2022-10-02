package me.cnm.impl.shared.module;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import lombok.Getter;
import me.cnm.shared.module.IModuleDescription;
import me.cnm.shared.utility.json.JsonDocument;
import me.cnm.shared.utility.scope.Scopes;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ModuleDescription implements IModuleDescription {

    private final JsonDocument jsonDocument;

    private final String name;
    private final String version;
    private final String language;
    private final String description;

    private final List<String> dependencies;
    private final List<String> softDependencies;
    private final List<String> authors;

    private final JsonDocument additional;

    public ModuleDescription(File moduleJson) {
        this.jsonDocument = Scopes.throwRuntime(() -> new JsonDocument(moduleJson));

        this.name = this.getString("name", true, moduleJson.getAbsolutePath());
        this.version = this.getString("version", true, this.name);
        this.language = this.getString("language", true, this.name);
        this.description = this.getString("description", false, this.name);

        this.dependencies = this.getArray("dependencies", this.name);
        this.softDependencies = this.getArray("softDependencies", this.name);
        this.authors = this.getArray("authors", this.name);

        this.additional = this.jsonDocument.getDocument("additional", new JsonDocument());
    }

    @Override
    public @NotNull JsonDocument asJsonDocument() {
        return this.jsonDocument;
    }

    @Contract("_, true, _ -> !null")
    private String getString(String name, boolean required, String moduleName) {
        if (!this.jsonDocument.contains(name) && required)
            throw new IllegalStateException("The module.json of " + moduleName + " doesn't contain " +
                    "required attribute " + name);

        JsonElement jsonElement = this.jsonDocument.get(name);
        if (!Boolean.TRUE.equals(Scopes.ifNotNull(jsonElement, JsonElement::isJsonPrimitive)))
            throw new IllegalStateException("The type of the attribute " + name + "of the module.json of " +
                    moduleName + " must be a string");

        JsonPrimitive jsonPrimitive = Scopes.ifNotNull(jsonElement, JsonElement::getAsJsonPrimitive);
        if (!Boolean.TRUE.equals(Scopes.ifNotNull(jsonPrimitive, JsonPrimitive::isString)))
            throw new IllegalStateException("The type of the attribute " + name + "of the module.json of " +
                    moduleName + " must be a string");

        return Scopes.ifNotNull(jsonPrimitive, JsonPrimitive::getAsString);
    }

    private List<String> getArray(String name, String moduleName) {
        JsonElement jsonElement = this.jsonDocument.get(name);
        if (jsonElement == null) return new ArrayList<>();

        if (jsonElement.isJsonArray())
            throw new IllegalStateException("The type of the attribute " + name + "of the module.json of " +
                    moduleName + " must be an array");

        JsonArray jsonArray = jsonElement.getAsJsonArray();
        List<String> list = new ArrayList<>();

        for (JsonElement element : jsonArray) {
            if (!element.isJsonPrimitive()) continue;
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (!primitive.isString()) continue;

            list.add(primitive.getAsString());
        }

        return list;
    }

}
