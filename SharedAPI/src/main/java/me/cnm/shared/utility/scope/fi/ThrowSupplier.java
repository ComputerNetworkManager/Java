package me.cnm.shared.utility.scope.fi;

import java.util.function.Supplier;

/**
 * A {@link Supplier} that can throw any exception
 *
 * @param <T> The type of the supplier
 * @see Supplier
 */
@FunctionalInterface
public interface ThrowSupplier<T> {

    /**
     * Get method of the supplier
     *
     * @return The value of the supplier
     * @throws Exception Can be thrown
     * @see Supplier#get()
     */
    @SuppressWarnings("java:S112")
    T get() throws Exception;

}
