package me.cnm.impl.shared.utility.mapping;

import lombok.NonNull;
import me.cnm.shared.utility.mapping.ITypeMapper;
import me.cnm.shared.utility.mapping.TypeMappingException;
import org.apache.commons.lang3.ClassUtils;
import org.jetbrains.annotations.NotNull;

public class TypeMapper implements ITypeMapper {

    private final EnumMapper enumConverter = new EnumMapper();
    private final ArrayMapper arrayConverter = new ArrayMapper();

    @Override
    @NotNull
    public <T> T fromString(@NonNull String value, @NonNull Class<T> toClass) {
        if (toClass.isEnum()) {
            //noinspection unchecked,rawtypes
            return (T) this.enumConverter.fromString(value, (Class<? extends Enum>) toClass);
        }

        if (toClass.isArray()) return this.arrayConverter.fromString(value, toClass, this);

        Class<?> clazz = toClass.isPrimitive() ? ClassUtils.primitiveToWrapper(toClass) : toClass;

        for (Mapper<?> mapper : Mapper.getMAPPERS()) {
            if (clazz.equals(mapper.getClazz())) {
                try {
                    //noinspection unchecked
                    return (T) mapper.getFromString().apply(value);
                } catch (Exception e) {
                    throw new TypeMappingException("The type must be " + clazz.getSimpleName());
                }
            }
        }

        throw new TypeMappingException("No type was found that would fit for mapping");
    }

    @Override
    @NotNull
    public String toString(@NonNull Object value) {
        Class<?> typeClass = value.getClass();
        if (typeClass.isEnum()) return value.toString();
        if (typeClass.isArray()) return this.arrayConverter.toString(value, this);

        Class<?> clazz = typeClass.isPrimitive() ? ClassUtils.primitiveToWrapper(typeClass) : typeClass;

        for (Mapper<?> converter : Mapper.getMAPPERS()) {
            if (clazz.equals(converter.getClazz())) {
                try {
                    return converter.convertToString(value);
                } catch (Exception e) {
                    throw new TypeMappingException("Can't convert " + clazz.getSimpleName());
                }
            }
        }

        throw new TypeMappingException("No type was found that would fit for mapping");
    }
}
