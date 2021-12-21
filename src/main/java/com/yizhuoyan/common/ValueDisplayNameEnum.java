package com.yizhuoyan.common;

import com.fasterxml.jackson.annotation.JsonValue;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

/**
 * @param <T>
 */
public interface ValueDisplayNameEnum<T> {
    @JsonValue
    T getValue();

    String getDisplayName();


    static <E extends Enum<?> & ValueDisplayNameEnum> E parseByValue(Class targetType, Object value) {
        if (value == null) return null;
        E[] enumConstants = (E[]) targetType.getEnumConstants();
        for (E enumConstant : enumConstants) {
            //简化判断，只要字符串相等及认为相等,这样就不需要细化判断类型了
            if (enumConstant.getValue().toString().equals(value.toString())) {
                return enumConstant;
            }
        }
        return null;
    }

    /**
     * 返回code的实际类型
     *
     * @param targetType
     * @param <E>
     * @return
     */
    public static <E extends Enum<?> & ValueDisplayNameEnum> Class getCodeClassType(Class<E> targetType) {
        final Type[] genericInterfaces = targetType.getGenericInterfaces();
        if (genericInterfaces.length == 0) {
            return null;
        }
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
                Class rawType = (Class) parameterizedType.getRawType();
                if (rawType.isAssignableFrom(ValueDisplayNameEnum.class)) {
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    if (actualTypeArguments.length == 0) return null;
                    Type actualTypeArgument = actualTypeArguments[0];
                    if (actualTypeArgument instanceof Class) {
                        return (Class) actualTypeArgument;
                    } else if (actualTypeArgument instanceof WildcardType) {
                        WildcardType wildcardType = (WildcardType) actualTypeArgument;
                        Type[] upperBounds = wildcardType.getUpperBounds();
                        if (upperBounds == null || upperBounds.length == 0) return null;
                        return (Class) (upperBounds[0]);
                    }
                }
            }
        }
        return null;
    }


}
