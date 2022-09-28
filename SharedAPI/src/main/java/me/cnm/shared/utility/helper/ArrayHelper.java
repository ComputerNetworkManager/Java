package me.cnm.shared.utility.helper;

import lombok.experimental.UtilityClass;

/**
 * The array helper helps to deal with complex array operations
 */
@UtilityClass
public class ArrayHelper {

    /**
     * Checks if a string array contains a string, ignoring case
     *
     * @param array  The haystack, in witch to search
     * @param needle The needle, for witch to search
     * @return Wheaten the needle was found
     */
    public boolean containsIgnoreCase(String[] array, String needle) {
        for (String s : array) {
            if (s.equalsIgnoreCase(needle)) return true;
        }

        return false;
    }

    /**
     * Checks if a string array contains any entry of other string array, ignoring case
     *
     * @param array  The haystack, in witch to search
     * @param needle The needle, for witch to search
     * @return Wheaten the needle was found
     */
    public boolean containsIgnoreCase(String[] array, String[] needle) {
        for (String s : needle) {
            if (ArrayHelper.containsIgnoreCase(array, s)) return true;
        }

        return false;
    }

}
