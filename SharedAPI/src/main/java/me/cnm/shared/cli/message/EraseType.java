package me.cnm.shared.cli.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * {@code EraseTypes} tell the console, what to erase<br>
 * Used by {@link ICLIMessageBuilder#eraseScreen(EraseType)} and {@link ICLIMessageBuilder#eraseLine(EraseType)}
 */
@Getter
@RequiredArgsConstructor
public enum EraseType {

    /**
     * Erase from the current position of the cursor to the end of the line or screen
     */
    TO_END(0),

    /**
     * Erase from the current position of the cursor to the start of the line or screen
     */
    TO_BEGIN(1),

    /**
     * Erase in both directions (to the start and to the end of the line/screen)
     */
    ALL(2);

    /**
     * The code used to make this erase type
     */
    private final int code;

}
