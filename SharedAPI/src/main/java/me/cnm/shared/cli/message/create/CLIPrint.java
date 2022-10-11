package me.cnm.shared.cli.message.create;

import lombok.experimental.UtilityClass;
import me.cnm.shared.cli.message.ICLIMessageBuilder;

/**
 * Class to create an {@link ICLIMessageBuilder}
 */
@UtilityClass
public class CLIPrint {

    /**
     * Create a message builder
     * @return The message builder
     * @see ICLIMessageBuilder
     */
    public ICLIMessageBuilder create() {
        return CLIMessageBuilder.create();
    }

}
