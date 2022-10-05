package me.cnm.shared.module.exception;

/**
 * Exception thrown when the module.json of a module is not correct
 */
public class IllegalModuleDescriptionException extends RuntimeException {

    /**
     * Create the exception
     *
     * @param module     The module witch's module.json isn't correct
     * @param field      The field of the module.json witch isn't correct
     * @param type       The type, why it isn't correct
     * @param additional An additional context
     */
    public IllegalModuleDescriptionException(String module, String field, Type type, String additional) {
        super("The module.json of " + module + " isn't correct: " + type.getMessage(field, additional));
    }

    /**
     * Create the exception
     *
     * @param module The module witch's module.json isn't correct
     * @param field  The field of the module.json witch isn't correct
     * @param type   The type, why it isn't correct
     */
    public IllegalModuleDescriptionException(String module, String field, Type type) {
        this(module, field, type, null);
    }

    /**
     * Enum with types, why a module.json could be illegal
     */
    public enum Type {

        /**
         * The field is required but not present
         */
        REQUIRED {
            @Override
            public String getMessage(String field, String additional) {
                return field + " is required";
            }
        },

        /**
         * The field is from the wrong type
         */
        WRONG_TYPE {
            @Override
            public String getMessage(String field, String additional) {
                return field + " needs to be from type " + additional;
            }
        },

        /**
         * The field is from the correct type, but has wrong content (e.g. alphanumeric)
         */
        WRONG_CONTENT {
            @Override
            public String getMessage(String field, String additional) {
                return field + " needs be " + additional;
            }
        };

        /**
         * Get the message witch should be shown in the exception message
         *
         * @param field      The field which is wrong
         * @param additional The additional context
         * @return The message to be shown
         */
        public abstract String getMessage(String field, String additional);

    }

}
