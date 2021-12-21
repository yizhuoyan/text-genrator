package com.yizhuoyan.txtgen.configuration.spring.converter;

import com.yizhuoyan.common.ValueDisplayNameEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * 统一注册EnumCodeValue枚举
 * @param <T>
 */
public class ValueDisplayNameEnumConverterFactory<T extends Enum<?> & ValueDisplayNameEnum> implements ConverterFactory<String, T> {
    @Override
    public <E extends T> Converter<String, E> getConverter(Class<E> aClass) {

        return new Converter<String, E>() {
            @Override
            public E convert(String s) {
                if (s == null | (s.trim()).length() == 0) return null;
                return ValueDisplayNameEnum.parseByValue(aClass, s);
            }
        };
    }

}
