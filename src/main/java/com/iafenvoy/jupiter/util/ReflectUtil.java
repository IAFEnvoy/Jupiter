package com.iafenvoy.jupiter.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

public class ReflectUtil {
    public static Class<?> getGenericActualClass(Field field) {
        return field.getGenericType() instanceof ParameterizedType parameterized && parameterized.getActualTypeArguments().length > 0 && parameterized.getActualTypeArguments()[0] instanceof Class<?> actualClazz ? actualClazz : null;
    }
}
