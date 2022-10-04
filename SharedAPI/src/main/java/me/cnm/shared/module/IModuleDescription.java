package me.cnm.shared.module;

import me.cnm.shared.utility.json.JsonDocument;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * All important information of an module.json
 */
public interface IModuleDescription {

    /**
     * @return The name of the module
     */
    @NotNull
    String getName();

    /**
     * @return The version of the module
     */
    @NotNull
    String getVersion();

    /**
     * @return The language of the module
     */
    @NotNull
    String getLanguage();

    /**
     * @return The description of the module
     */
    @Nullable
    String getDescription();

    /**
     * @return The dependencies of the module
     */
    @NotNull
    List<String> getDependencies();

    /**
     * Soft dependencies are dependencies, that are not required for running, but should exist<br>
     * Also the default module handler ensures, that all soft dependencies are loaded before a module if existing
     *
     * @return The soft dependencies of the module
     */
    @NotNull
    List<String> getSoftDependencies();

    /**
     * @return The authors of the module
     */
    @NotNull
    List<String> getAuthors();

    /**
     * The additional part is used to specify information required by a language<br>
     * For example the main class for java modules
     *
     * @return All information stored in the additional part of the module.json
     */
    @NotNull
    JsonDocument getAdditional();

    /**
     * @return The whole module.json as {@code JsonDocument}
     */
    @NotNull
    JsonDocument asJsonDocument();

}
