package com.yizhuoyan.txtgen.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.yizhuoyan.common.ValueDisplayNameEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonObjectMapperConfig {

    @Bean
    public Module enumCodeModule() {
        final SimpleModule enumCodeModule = new SimpleModule("codeEnum");
        enumCodeModule.addSerializer(ValueDisplayNameEnum.class, new StdSerializer<ValueDisplayNameEnum>(ValueDisplayNameEnum.class){
            @Override
            public void serialize(ValueDisplayNameEnum e, JsonGenerator gen, SerializerProvider provider) throws IOException {
                if (e == null) {
                    gen.writeNull();
                    return;
                }
                gen.writeObject(e.getValue());
            }
        });

        enumCodeModule.addDeserializer(ValueDisplayNameEnum.class, new StdDeserializer<ValueDisplayNameEnum>(ValueDisplayNameEnum.class) {
            @Override
            public ValueDisplayNameEnum deserialize(JsonParser p, DeserializationContext ctx) throws IOException, JsonProcessingException {
                return ValueDisplayNameEnum.parseByValue(ctx.getActiveView(), p.getText());
            }
        });

        enumCodeModule.addKeySerializer(ValueDisplayNameEnum.class,new StdSerializer<ValueDisplayNameEnum>(ValueDisplayNameEnum.class){

            @Override
            public void serialize(ValueDisplayNameEnum e, JsonGenerator gen, SerializerProvider provider) throws IOException {
                if (e == null) {
                    gen.writeNull();
                    return;
                }
                gen.writeString(String.valueOf(e.getValue()));
            }
        });
        return enumCodeModule;
    }

    /**
     * LocalDateTime全局配置yyyy-MM-ddTHH:mm:ss.SSS 前端js可直接识别
     * 局部格式化使用@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss'xxx'")
     */
    @Bean
    public Module javaTimeModule() {
        final SimpleModule module = new SimpleModule("local-date-time");
        //转换为标准格式字符串
        module.addSerializer(LocalDateTime.class,new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        //字符串转换为local datetime
        module.addDeserializer(LocalDateTime.class,new LocalDateTimeDeserializer(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        //用于Map集合的key
        module.addKeySerializer(LocalDateTime.class,new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        return module;
    }






}

