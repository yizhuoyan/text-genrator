package com.yizhuoyan.txtgen.configuration.spring.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * LocalDate全局转换器
 */
public class LocalDateFormatter implements Formatter<LocalDate> {
    private final String LOCAL_DATE_PATTERN_STRING="yyyy-MM-dd";

    private final DateTimeFormatter DEFAULT_FORMATTER=DateTimeFormatter.ofPattern(LOCAL_DATE_PATTERN_STRING);

    /**
     * 目前的格式，locale参数无用，直接忽略
     * @param source
     * @param locale
     * @return
     * @throws ParseException
     */
    @Override
    public LocalDate parse(String source, Locale locale) throws ParseException {
        if(source==null)return null;
        if((source=source.trim()).length()==0)return null;
        if(source.length()!=LOCAL_DATE_PATTERN_STRING.length()){
            throw new ParseException(String.format("日期字符串%s未匹配格式%s",source,LOCAL_DATE_PATTERN_STRING),0);
        }
        try {
            return DEFAULT_FORMATTER.parse(source, LocalDate::from);
        }catch (DateTimeParseException e){
            throw new ParseException(String.format("日期字符串%s未匹配格式%s",source,LOCAL_DATE_PATTERN_STRING),e.getErrorIndex());
        }
    }

    @Override
    public String print(LocalDate source, Locale locale) {
        if(source==null)return null;
        return DEFAULT_FORMATTER.format(source);
    }
}
