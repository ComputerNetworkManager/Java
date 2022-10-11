package me.cnm.shared.cli.print;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Colors used as fore- and background colors
 */
@Getter
@RequiredArgsConstructor
public enum Color {

    /**
     * Reset to the default color
     */
    RESET(39, 49),

    /**
     * Black
     */
    BLACK(30, 40),

    /**
     * Dark red or red
     */
    RED(31, 41),

    /**
     * Green
     */
    GREEN(32, 42),

    /**
     * Yellow or orange
     */
    YELLOW(33, 43),

    /**
     * Dark blue or blue
     */
    BLUE(34, 44),

    /**
     * Magenta or dark blue
     */
    MAGENTA(35, 45),

    /**
     * Cyan
     */
    CYAN(36, 46),

    /**
     * Light gray (in ansi known as white)
     */
    LIGHT_GRAY(37, 47),

    /**
     * Dark gray or gray (in ansi known as bright black)
     */
    GRAY(90, 100),

    /**
     * Light red or red
     */
    LIGHT_RED(91, 101),

    /**
     * Light green or green
     */
    LIGHT_GREEN(92, 102),

    /**
     * Yellow
     */
    LIGHT_YELLOW(93, 103),

    /**
     * Light blue or blue
     */
    LIGHT_BLUE(94, 104),

    /**
     * Light magenta or magenta
     */
    LIGHT_MAGENTA(95, 105),

    /**
     * Light cyan
     */
    LIGHT_CYAN(96, 106),

    /**
     * White or very bright gray
     */
    WHITE(97, 107);

    /**
     * The code used to set this color as foreground-color
     */
    private final int fg;

    /**
     * The code used to set this color as background-color
     */
    private final int bg;

}
