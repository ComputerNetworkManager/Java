package me.cnm.impl.shared.util.mapping;

import me.cnm.shared.util.mapping.TypeMappingException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumMapper {

    public <T extends Enum<T>> T fromString(String value, Class<T> enumClass) {
        if (!enumClass.isEnum()) return null;

        try {
            return Enum.valueOf(enumClass, value);
        } catch (IllegalArgumentException e) {
            try {
                Method method = enumClass.getDeclaredMethod("values");
                List<String> values = Arrays.stream(((Object[]) method.invoke(null)))
                        .map(Object::toString)
                        .collect(Collectors.toList());

                throw new TypeMappingException("The value must be one of " +
                        String.join(", ", values.subList(0, values.size() - 1))
                        + " or "
                        + values.get(values.size() - 1));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassCastException ex) {
                throw new TypeMappingException("This enum value is not present.");
            }
        }
    }

}