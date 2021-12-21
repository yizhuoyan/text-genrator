package com.yizhuoyan.txtgen.configuration.spring.converter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * LocalDateTime全局转换器
 * spring的WebConversionService中使用DateTimeFormatterRegistrar已经注册了String到LocalDateTime的转换器，
 * 但其模式格式为：DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT),eg:21-7-28 下午5:38
 * 且未提供扩展点
 *
 * 这里全局重新注册。局部使用可使用注解@{@link org.springframework.format.annotation.DateTimeFormat}即可
 */
public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {
    private static final String LOCAL_DATE_PATTERN_STRING = "yyyy-MM-dd HH:mm:ss";
    private final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern(LOCAL_DATE_PATTERN_STRING);

    @Override
    public LocalDateTime parse(String source, Locale locale) throws ParseException {
        if (source == null) return null;
        if ((source = source.trim()).length() == 0) return null;
        if (source.length() != LOCAL_DATE_PATTERN_STRING.length()) {
            throw new ParseException(String.format("日期字符串%s未匹配格式%s", source, LOCAL_DATE_PATTERN_STRING), 0);
        }
        //这里可考虑使用DateTimeXParser
        try {
            return DEFAULT_FORMATTER.parse(source, LocalDateTime::from);
        } catch (DateTimeParseException e) {
            throw new ParseException(String.format("日期字符串%s未匹配格式%s", source,LOCAL_DATE_PATTERN_STRING), e.getErrorIndex());
        }
    }

    @Override
    public String print(LocalDateTime source, Locale locale) {
        if (source == null) return null;
        return DEFAULT_FORMATTER.format(source);
    }
}
