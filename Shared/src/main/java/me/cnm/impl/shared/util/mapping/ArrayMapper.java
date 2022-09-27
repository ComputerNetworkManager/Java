package me.cnm.impl.shared.util.mapping;

import me.cnm.shared.util.mapping.ITypeMapper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ArrayMapper {

    public <T> T fromString(String value, Class<T> arrayClass, ITypeMapper typeMapper) {
        if (!arrayClass.isArray()) return null;

        Class<?> componentType = arrayClass.getComponentType();
        String[] split = value.split(";");
        Object array = Array.newInstance(componentType, split.length);

        for (int i = 0; i < split.length; i++)
            Array.set(array, i, typeMapper.fromString(split[i], componentType));

        //noinspection unchecked
        return (T) array;
    }

    public String toString(Object array, ITypeMapper typeMapper) {
        if (!array.getClass().isArray()) return null;

        List<String> list = new ArrayList<>();
        int arrayLength = Array.getLength(array);

        for (int i = 0; i < arrayLength; i++) list.add(typeMapper.toString(Array.get(array, i)));

        return String.join(";", list);
    }

}