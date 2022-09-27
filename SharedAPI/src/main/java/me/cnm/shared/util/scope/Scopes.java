package me.cnm.shared.util.scope;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

/**
 * This class adds some useful scope functions
 */
@UtilityClass
public class Scopes {

    /**
     * Check if {@code object} is null, if it is, null is returned. In any other case the {@code notNull} function is called
     * @param object the object to check if its null
     * @param notNull the {@link Function} wich will be called if the object is not null
     * @return null or the result of {@code notNull}
     * @param <T> the type of the {@code object}
     * @param <R> the type that the {@link Function} returns
     */
    @Nullable
    public <T, R> R ifNotNull(T object, Function<T, R> notNull) {
        if (object == null) return null;
        return notNull.apply(object);
    }

}
