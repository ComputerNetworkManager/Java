package me.cnm.shared.module.exception;

public class IllegalModuleDescriptionException extends RuntimeException {

    public IllegalModuleDescriptionException(String module, String field, Type type, String additional) {
        super("The module.json of " + module + " isn't correct: " + type.getMessage(field, additional));
    }

    public IllegalModuleDescriptionException(String module, String field, Type type) {
        this(module, field, type, null);
    }
    
    public enum Type {
        
        REQUIRED {
            @Override
            public String getMessage(String field, String additional) {
                return field + " is required";
            }
        },
        WRONG_TYPE {
            @Override
            public String getMessage(String field, String additional) {
                return field + " needs to be from type " + additional;
            }
        },
        WRONG_CONTENT {
            @Override
            public String getMessage(String field, String additional) {
                return field + " needs be " + additional;
            }
        };
        
        public abstract String getMessage(String field, String additional);
        
    }
    
}
