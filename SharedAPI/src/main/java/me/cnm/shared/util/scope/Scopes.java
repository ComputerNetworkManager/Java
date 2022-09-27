package me.cnm.shared.util.scope;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

@UtilityClass
public class Scopes {

    @Nullable
    public <T, R> R ifNotNull(T object, Function<T, R> notNull) {
        if (object == null) return null;
        return notNull.apply(object);
    }

}
