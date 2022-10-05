package me.cnm.shared.utility.scope.exception;

import me.cnm.shared.utility.scope.Scopes;

/**
 * Is thrown by the {@link Scopes} that throw exceptions as runtime exception
 */
public class ScopeRuntimeException extends RuntimeException {

    /**
     * @param cause The original exception, that was caught
     */
    public ScopeRuntimeException(Throwable cause) {
        super(cause);
    }
}
