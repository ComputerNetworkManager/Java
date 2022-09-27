package me.cnm.shared.utility.scope;

import lombok.NonNull;
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
     *
     * @param object  the object to check if its null
     * @param notNull the {@link Function} wich will be called if the object is not null
     * @param <T>     the type of the {@code object}
     * @param <R>     the type that the {@link Function} returns
     * @return null or the result of {@code notNull}
     */
    @Nullable
    public <T, R> R ifNotNull(T object, @NonNull Function<T, R> notNull) {
        if (object == null) return null;
        return notNull.apply(object);
    }

    /**
     * Checks if the code in the {@link ThrowRunnable} throws a checked exception, wrap it in a runtime exception and
     * throw it
     *
     * @param runnable The runnable to check if it throws an exception
     */
    public void throwRuntime(@NonNull ThrowRunnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            if (e instanceof RuntimeException ex) throw ex;
            else throw new RuntimeException(e);
        }
    }

    /**
     * Checks if the code in the {@link ThrowSupplier} throws a checked exception, wrap it in a runtime exception and
     * throw it, otherwise return the value returned from the supplier
     *
     * @param supplier The supplier to check if it throws an exception
     * @param <T>      The type of the value
     * @return The value from the supplier
     */
    public <T> T throwRuntime(@NonNull ThrowSupplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            if (e instanceof RuntimeException ex) throw ex;
            else throw new RuntimeException(e);
        }
    }

}
