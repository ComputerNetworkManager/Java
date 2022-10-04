package me.cnm.impl.shared;

import lombok.NonNull;
import me.cnm.shared.IHandlerLibrary;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class HandlerLibrary implements IHandlerLibrary {

    private final Map<Class<?>, Object> handlers = new HashMap<>();

    @Override
    public <T> void registerHandler(@NonNull Class<T> type, @NonNull T instance) {
        handlers.put(type, instance);
    }

    @Override
    @NotNull
    public <T> T getHandler(@NonNull Class<T> type) {
        if (!handlers.containsKey(type)) throw new IllegalArgumentException("No handler with this type is registered");

        //noinspection unchecked
        return (T) handlers.get(type);
    }
}
