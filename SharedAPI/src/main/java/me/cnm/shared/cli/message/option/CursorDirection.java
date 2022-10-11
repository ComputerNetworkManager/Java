package me.cnm.shared.cli.message.option;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Direction in witch to move the cursor
 */
@Getter
@RequiredArgsConstructor
public enum CursorDirection {

    /**
     * Move the cursor up
     */
    UP('A'),

    /**
     * Move the cursor down
     */
    DOWN('B'),

    /**
     * Move the cursor to the left
     */
    LEFT('C'),

    /**
     * Move the cursor to the right
     */
    RIGHT('D');

    /**
     * The code used to make this direction
     */
    private final char code;

}
