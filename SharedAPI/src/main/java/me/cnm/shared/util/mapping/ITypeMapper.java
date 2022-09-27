package me.cnm.shared.util.mapping;

import org.jetbrains.annotations.NotNull;

/**
 * The {@code TypeMapper} is used to convert objects to string and vice versa<br>
 * Currently supported are:
 * <ul>
 *     <li>String</li>
 *     <li>Byte</li>
 *     <li>Short</li>
 *     <li>Integer</li>
 *     <li>Long</li>
 *     <li>Character</li>
 *     <li>Float</li>
 *     <li>Double</li>
 *     <li>Boolean</li>
 *     <li>Any enum</li>
 *     <li>An array of any of the above</li>
 * </ul>
 */
public interface ITypeMapper {

    /**
     * Try to convert a string to a certain type<br>
     * To see which types are currently supported, see the class description
     *
     * @param value   The string to convert
     * @param toClass The class to convert the string to
     * @param <T>     The type to which the string should be converted
     * @return The converted string
     * @throws NullPointerException           If {@code value} or {@code toClass} is null
     * @throws TypeMappingException If for some reason the mapping didn't work
     */
    @NotNull
    <T> T fromString(@NotNull String value, @NotNull Class<T> toClass);

    /**
     * Try to convert a certain type to a string<br>
     * To see which types are currently supported, see the class description
     *
     * @param value The object to convert to a String
     * @return The object converted to a String
     * @throws NullPointerException   If {@code value} is null
     * @throws TypeMappingException If for some reason the mapping didn't work
     */
    @NotNull
    String toString(@NotNull Object value);

}
