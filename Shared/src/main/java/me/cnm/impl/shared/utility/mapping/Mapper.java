package me.cnm.impl.shared.utility.mapping;

import lombok.Data;
import lombok.Getter;
import me.cnm.shared.utility.json.JsonDocument;
import me.cnm.shared.utility.mapping.TypeMappingException;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Data
public class Mapper<T> {

    @Getter
    private static final List<Mapper<?>> MAPPERS = Arrays.asList(
            new Mapper<>(String.class, value -> value, Object::toString),
            new Mapper<>(Byte.class, Byte::parseByte, Object::toString),
            new Mapper<>(Short.class, Short::parseShort, Object::toString),
            new Mapper<>(Integer.class, Integer::parseInt, Object::toString),
            new Mapper<>(Long.class, Long::parseLong, Object::toString),
            new Mapper<>(Character.class, value -> {
                if (value.length() != 1) throw new TypeMappingException("The value must have 1 character");
                return value.charAt(0);
            }, Object::toString),
            new Mapper<>(Float.class, Float::parseFloat, Object::toString),
            new Mapper<>(Double.class, Double::parseDouble, Object::toString),
            new Mapper<>(Boolean.class, Boolean::parseBoolean, Object::toString),
            new Mapper<>(JsonDocument.class, JsonDocument::new, JsonDocument::toJson)
    );

    private final Class<T> clazz;
    private final Function<String, T> fromString;
    private final Function<T, String> toString;

    public String convertToString(Object value) {
        //noinspection unchecked
        return this.toString.apply((T) value);
    }

}