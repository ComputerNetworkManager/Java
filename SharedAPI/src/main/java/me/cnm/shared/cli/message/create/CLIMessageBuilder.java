package me.cnm.shared.cli.message.create;

import lombok.experimental.UtilityClass;
import me.cnm.shared.cli.message.ICLIMessageBuilder;
import me.cnm.shared.utility.scope.Scopes;

import java.lang.reflect.Constructor;

/**
 * Class to create an {@link ICLIMessageBuilder}
 */
@UtilityClass
public class CLIMessageBuilder {

    /**
     * Create a message builder
     * @return The message builder
     * @see ICLIMessageBuilder
     */
    public ICLIMessageBuilder create() {
        return Scopes.throwRuntime(() -> {
            Class<?> clazz = Class.forName("me.cnm.impl.shared.cli.message.CLIMessageBuilder");
            Constructor<?> constructor = clazz.getConstructor();
            return (ICLIMessageBuilder) constructor.newInstance();
        });
    }

}
