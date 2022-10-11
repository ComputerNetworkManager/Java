package me.cnm.shared.cli.print;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Formats that can be used to format text with the {@link ICLIMessageBuilder}
 */
@Getter
@RequiredArgsConstructor
public enum Format {

    /**
     * Make text bold or increased intensity
     */
    BOLD(1, 22),

    /**
     * Make a text faint or decreased intensity
     */
    FAINT(2, 22),

    /**
     * Make a text italic<br>
     * Not widely supported
     */
    ITALIC(3, 23),

    /**
     * Make a text underlined
     */
    UNDERLINED(4, 24),

    /**
     * Make a text crossed out or strike, characters legible but marked as if for deletion<br>
     * Not supported in {@code Terminal.app}
     */
    CROSSED_OUT(9, 24),

    /**
     * Make a texted dobuled underlined<br>
     * On some consoles this code is used as {@code not bold}
     */
    DOUBLE_UNDERLINED(21, 24);

    /**
     * The code used to set the format
     */
    private final int set;

    /**
     * The code used to reset the format
     */
    private final int reset;

}
