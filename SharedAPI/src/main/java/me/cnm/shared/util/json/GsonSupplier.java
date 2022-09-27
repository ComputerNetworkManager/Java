package me.cnm.shared.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class GsonSupplier {

    private final Map<Class<?>, Object> typeAdapters = new HashMap<>();
    private Gson gson;

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

    public void registerTypeAdapter(Class<?> clazz, Object typeAdapter) {
        typeAdapters.put(clazz, typeAdapter);
        gson = null;
    }

}
