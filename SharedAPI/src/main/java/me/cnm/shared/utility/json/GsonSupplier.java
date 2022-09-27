package me.cnm.shared.utility.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to supply the {@link JsonDocument} with a {@link Gson} object.
 * It is also capable of registering custom type adapters
 */
public class GsonSupplier {

    /**
     * A map with all custom registered type adapters
     */
    private final Map<Class<?>, Object> typeAdapters = new HashMap<>();

    /**
     * The current instance of the {@link Gson} object
     */
    private Gson gson;

    /**
     * Creates a {@link Gson} object with all registered type adapters if the object isn't created yet
     * or a new type adapter was registered since the last call
     * <br>
     * If none of the above is the case, then the gson object will be cached
     *
     * @return the {@link Gson} object with all registered type adapters
     * @see #registerTypeAdapter(Class, Object)
     */
    public Gson getGson() {
        if (gson == null) {
            GsonBuilder gsonBuilder = new GsonBuilder()
                    .serializeNulls()
                    .disableHtmlEscaping()
                    .setPrettyPrinting();

            typeAdapters.forEach(gsonBuilder::registerTypeAdapter);

            gson = gsonBuilder.create();
        }

        return gson;
    }

    /**
     * Register a new custom type adapter
     * @param clazz the {@link Class} for witch the type adapter should trigger
     * @param typeAdapter the type adapter, witch should be triggered
     */
    public void registerTypeAdapter(Class<?> clazz, Object typeAdapter) {
        typeAdapters.put(clazz, typeAdapter);
        gson = null;
    }

}
