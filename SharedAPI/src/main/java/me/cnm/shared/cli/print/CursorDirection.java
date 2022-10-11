package me.cnm.shared.cli.print;

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
    UP('a'),

    /**
     * Move the cursor down
     */
    DOWN('b'),

    /**
     * Move the cursor to the left
     */
    LEFT('c'),

    /**
     * Move the cursor to the right
     */
    RIGHT('d');

    /**
     * The code used to make this direction
     */
    private final char code;

}
