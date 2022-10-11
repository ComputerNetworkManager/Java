package me.cnm.shared.cli.message;

import me.cnm.shared.cli.message.option.Color;
import me.cnm.shared.cli.message.option.CursorDirection;
import me.cnm.shared.cli.message.option.EraseType;
import me.cnm.shared.cli.message.option.Format;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * The {@code ICLIPrint} can be used to modify cli messages<br>
 * More information can be found on <a href="https://en.wikipedia.org/wiki/ANSI_escape_code#Description">wikipedia</a>
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public interface ICLIMessageBuilder {

    /**
     * Move the cursor in a direction
     *
     * @param amount    The amount of rows/columns to move in that direction
     * @param direction The direction in witch to move the cursor
     * @return This instance
     * @see CursorDirection
     */
    @Contract("_, _ -> this")
    ICLIMessageBuilder cursor(int amount, @NotNull CursorDirection direction);

    /**
     * Move the cursor to the next line
     *
     * @return This instance
     */
    @Contract("-> this")
    ICLIMessageBuilder cursorNextLine();

    /**
     * Move the cursor to the previous line
     *
     * @return This instance
     */
    @Contract("-> this")
    ICLIMessageBuilder cursorPreviousLine();

    /**
     * Set the position of the cursor in the current line
     *
     * @param position The new postion of the cursor
     * @return This instance
     */
    @Contract("_ -> this")
    ICLIMessageBuilder setCursorHorizontally(int position);

    /**
     * Set the position of the cursor on the screen with x and y position
     *
     * @param x The x position
     * @param y The y position
     * @return This instance
     */
    @Contract("_, _ -> this")
    ICLIMessageBuilder moveCursor(int x, int y);

    /**
     * Store the current position of the cursor, can later be restored with {@link #restoreCursorPosition()}
     *
     * @return This instance
     */
    @Contract("-> this")
    ICLIMessageBuilder storeCursorPosition();

    /**
     * Restore the position of the cursor, previously store with {@link #storeCursorPosition()}
     *
     * @return This instance
     */
    ICLIMessageBuilder restoreCursorPosition();

    /**
     * Erase all content from the cursor to the end of the screen
     *
     * @return This instance
     * @see #eraseScreen(EraseType)
     * @see EraseType#TO_END
     */
    @Contract("-> this")
    ICLIMessageBuilder eraseScreen();

    /**
     * Erase part of the content or all of the screen
     *
     * @param eraseType The type
     * @return This instance
     * @see EraseType
     */
    @Contract("_ -> this")
    ICLIMessageBuilder eraseScreen(@NotNull EraseType eraseType);

    /**
     * Erase all content from the cursor to the end of the line
     *
     * @return This instance
     * @see #eraseLine(EraseType)
     * @see EraseType#TO_END
     */
    @Contract("-> this")
    ICLIMessageBuilder eraseLine();

    /**
     * Erase part of the content or all of the line
     *
     * @return This instance
     * @see EraseType
     */
    @Contract("_ -> this")
    ICLIMessageBuilder eraseLine(@NotNull EraseType eraseType);

    /**
     * Scroll one line up
     *
     * @return This instance
     */
    @Contract("-> this")
    ICLIMessageBuilder scrollUp();

    /**
     * Scroll one line down
     *
     * @return This instance
     */
    @Contract("-> this")
    ICLIMessageBuilder scrollDown();

    /**
     * Reset all formats (including colors)
     *
     * @return This instance
     */
    @Contract("-> this")
    ICLIMessageBuilder resetFormats();

    /**
     * Add a format in which to display text, added afterwards
     *
     * @param formats The format to add
     * @return This instance
     * @see Format
     */
    @Contract("_ -> this")
    ICLIMessageBuilder addFormats(@NotNull Format... formats);

    /**
     * Remove a format from the text added afterwards
     *
     * @param formats The format to remove
     * @return This instance
     * @see Format
     */
    @Contract("_ -> this")
    ICLIMessageBuilder removeFormats(@NotNull Format... formats);

    /**
     * Set the foreground-color of the text
     *
     * @param color The new foreground-color
     * @return This instance
     * @see Color
     */
    @Contract("_ -> this")
    ICLIMessageBuilder fg(@NotNull Color color);

    /**
     * Set the foreground-color of the text with rgb
     *
     * @param r The red component of the color
     * @param g The green component of the color
     * @param b The blue component of the color
     * @return This instance
     */
    @Contract("_, _, _ -> this")
    ICLIMessageBuilder fg(int r, int g, int b);

    /**
     * Set the background-color of the text
     *
     * @param color The new background-color
     * @return This instance
     * @see Color
     */
    @Contract("_ -> this")
    ICLIMessageBuilder bg(@NotNull Color color);

    /**
     * Set the background-color of the text with rgb
     *
     * @param r The red component of the color
     * @param g The green component of the color
     * @param b The blue component of the color
     * @return This instance
     */
    @Contract("_, _, _ -> this")
    ICLIMessageBuilder bg(int r, int g, int b);

    /**
     * Append a text with formats, that only apply to the added text<br>
     * The text will have only the formats in the {@code formats} parameter<br>
     * The previously defined colors will be used<br>
     * All formats (except colors) are reset afterwards<br>
     *
     * @param text    The text
     * @param formats The formats
     * @return This instance
     * @see Format
     */
    @Contract("_, _ -> this")
    ICLIMessageBuilder text(@NotNull Object text, @NotNull Format... formats);

    /**
     * Append a text with a foreground-color, that only apply to the added text<br>
     * Previously added formats will be used (except foreground-color)<br>
     * The foreground-color is reset afterwards
     *
     * @param text The text
     * @param fg   The foreground-color
     * @return This instance
     * @see Color
     */
    @Contract("_, _ -> this")
    ICLIMessageBuilder textFg(@NotNull Object text, @NotNull Color fg);

    /**
     * Append a text with a background-color, that only apply to the added text<br>
     * Previously added formats will be used (except background-color)<br>
     * The foreground-color is reset afterwards
     *
     * @param text The text
     * @param bg   The foreground-color
     * @return This instance
     * @see Color
     */
    @Contract("_, _ -> this")
    ICLIMessageBuilder textBg(@NotNull Object text, @NotNull Color bg);

    /**
     * Append a text with a fore- and background-color, that only apply to the added text<br>
     * Previously added formats will be used (except colors)<br>
     * The colors is reset afterwards
     *
     * @param text The text
     * @param fg   The foreground-color
     * @param bg   The background-color
     * @return This instance
     * @see Color
     */
    @Contract("_, _, _ -> this")
    ICLIMessageBuilder text(@NotNull Object text, @NotNull Color fg, @NotNull Color bg);

    /**
     * Append a text with a foreground-color, that only apply to the added text<br>
     * Previously added formats will be used (except foreground-color)<br>
     * The foreground-color is reset afterwards
     *
     * @param text The text
     * @param r    The red component of the color
     * @param g    The green component of the color
     * @param b    The blue component of the color
     * @return This instance
     * @see Color
     */
    @Contract("_, _, _, _ -> this")
    ICLIMessageBuilder textFg(@NotNull Object text, int r, int g, int b);

    /**
     * Append a text with a background-color, that only apply to the added text<br>
     * Previously added formats will be used (except background-color)<br>
     * The foreground-color is reset afterwards
     *
     * @param text The text
     * @param r    The red component of the color
     * @param g    The green component of the color
     * @param b    The blue component of the color
     * @return This instance
     * @see Color
     */
    @Contract("_, _, _, _ -> this")
    ICLIMessageBuilder textBg(@NotNull Object text, int r, int g, int b);

    /**
     * Append a text with a fore- and background-color, that only apply to the added text<br>
     * Previously added formats will be used (except colors)<br>
     * The colors is reset afterwards
     *
     * @param text The text
     * @param fgr  The red component of the foreground-color
     * @param fgg  The green component of the foreground-color
     * @param fgb  The blue component of the foreground-color
     * @param bgr  The red component of the background-color
     * @param bgg  The green component of the background-color
     * @param bgb  The blue component of the background-color
     * @return This instance
     * @see Color
     */
    @Contract("_, _, _, _, _, _, _ -> this")
    ICLIMessageBuilder text(@NotNull Object text, int fgr, int fgg, int fgb, int bgr, int bgg, int bgb);

    /**
     * Append a text with a foreground-color and formats, that only apply to the added text<br>
     * The text will have only the formats in the {@code formats} parameter<br>
     * The previously defined background-color will be used<br>
     * All formats (except background-color) are reset afterwards<br>
     *
     * @param text    The text
     * @param fg      The color
     * @param formats The formats
     * @return This instance
     * @see Color
     * @see Format
     */
    @Contract("_, _, _ -> this")
    ICLIMessageBuilder textFg(@NotNull Object text, @NotNull Color fg, @NotNull Format... formats);

    /**
     * Append a text with a background-color and formats, that only apply to the added text<br>
     * The text will have only the formats in the {@code formats} parameter<br>
     * The previously defined foreground-color will be used<br>
     * All formats (except foreground-color) are reset afterwards<br>
     *
     * @param text    The text
     * @param bg      The color
     * @param formats The formats
     * @return This instance
     * @see Color
     * @see Format
     */
    @Contract("_, _, _ -> this")
    ICLIMessageBuilder textBg(@NotNull Object text, @NotNull Color bg, @NotNull Format... formats);

    /**
     * Append a text with colors and formats, that only apply to the added text<br>
     * All formats are reset afterwards<br>
     *
     * @param text    The text
     * @param fg      The foreground-color
     * @param bg      The background-color
     * @param formats The formats
     * @return This instance
     * @see Color
     * @see Format
     */
    @Contract("_, _, _, _ -> this")
    ICLIMessageBuilder text(@NotNull Object text, @NotNull Color fg, @NotNull Color bg, @NotNull Format... formats);

    /**
     * Append a text with a foreground-color and formats, that only apply to the added text<br>
     * The text will have only the formats in the {@code formats} parameter<br>
     * The previously defined background-color will be used<br>
     * All formats (except background-color) are reset afterwards<br>
     *
     * @param text    The text
     * @param r       The red component of the color
     * @param g       The green component of the color
     * @param b       The blue component of the color
     * @param formats The formats
     * @return This instance
     * @see Color
     * @see Format
     */
    @Contract("_, _, _, _, _ -> this")
    ICLIMessageBuilder textFg(@NotNull Object text, int r, int g, int b, @NotNull Format... formats);

    /**
     * Append a text with a background-color and formats, that only apply to the added text<br>
     * The text will have only the formats in the {@code formats} parameter<br>
     * The previously defined foreground-color will be used<br>
     * All formats (except foreground-color) are reset afterwards<br>
     *
     * @param text    The text
     * @param r       The red component of the color
     * @param g       The green component of the color
     * @param b       The blue component of the color
     * @param formats The formats
     * @return This instance
     * @see Color
     * @see Format
     */
    @Contract("_, _, _, _, _ -> this")
    ICLIMessageBuilder textBg(@NotNull Object text, int r, int g, int b, @NotNull Format... formats);

    /**
     * Append a text with colors and formats, that only apply to the added text<br>
     * All formats are reset afterwards<br>
     *
     * @param text    The text
     * @param fgr     The red component of the foreground-color
     * @param fgg     The green component of the foreground-color
     * @param fgb     The blue component of the foreground-color
     * @param bgr     The red component of the background-color
     * @param bgg     The green component of the background-color
     * @param bgb     The blue component of the background-color
     * @param formats The formats
     * @return This instance
     * @see Color
     * @see Format
     */
    @Contract("_, _, _, _, _, _, _, _ -> this")
    ICLIMessageBuilder text(@NotNull Object text, int fgr, int fgg, int fgb, int bgr, int bgg, int bgb, @NotNull Format... formats);

    /**
     * Append a text that uses all previously defined formats and colors
     *
     * @param text The text
     * @return This instance
     */
    @Contract("_ -> this")
    ICLIMessageBuilder text(@NotNull Object text);

    /**
     * Build all previously defined attributes to text
     * @return The build text
     */
    @NotNull
    String build();

}
