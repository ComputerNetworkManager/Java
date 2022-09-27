package me.cnm.shared.util.mapping;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Exception thrown when there is an error converting a string to a type or vice versa
 */
@Getter
@RequiredArgsConstructor
public class TypeMappingException extends RuntimeException {

    /**
     * Reason why the exception was thrown
     */
    private final String message;

}