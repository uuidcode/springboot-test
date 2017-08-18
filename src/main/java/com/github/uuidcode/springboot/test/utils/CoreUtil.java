package com.github.uuidcode.springboot.test.utils;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.commons.codec.net.URLCodec;
import org.hibernate.engine.jdbc.internal.BasicFormatterImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public class CoreUtil {
    protected static String dateTimePattern = "yyyy-MM-dd HH:mm:ss";
    protected static Logger logger = LoggerFactory.getLogger(CoreUtil.class);

    public static List<String> dateTimePatternList = new ArrayList<String>() {{
        this.add("yyyyMMddHHmmss");
        this.add("yyyyMMddHHmm");
        this.add("yyyyMMddHH");
        this.add("yyyyMMdd");

        this.add("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        this.add("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        this.add("yyyy-MM-dd'T'HH:mm:ssXXX");
        this.add("yyyy-MM-dd'T'HH:mm:ssZ");
        this.add("yyyy-MM-dd'T'HH:mm:ss");

        this.add("yyyy-MM-dd HH:mm:ss,SSSXXX");
        this.add("yyyy-MM-dd HH:mm:ss,SSSZ");
        this.add("yyyy-MM-dd HH:mm:ss,SSS");
        this.add("yyyy-MM-dd HH:mm:ss");
        this.add("yyyy-MM-dd HH:mm");
        this.add("yyyy-MM-dd HH");
        this.add("yyyy-MM-dd");

        this.add("yyyy/MM/dd HH:mm:ss");
        this.add("yyyy/MM/dd HH:mm");
        this.add("yyyy/MM/dd HH");
        this.add("yyyy/MM/dd");

        this.add("yyyy.MM.dd HH:mm:ss");
        this.add("yyyy.MM.dd HH:mm");
        this.add("yyyy.MM.dd HH");
        this.add("yyyy.MM.dd");
    }};

    public static DateTimeFormatter dateTimeFormatter;
    public static ObjectMapper objectMapper;

    static {
        dateTimeFormatter = createDateTimeFormatter();
        objectMapper = createObjectMapper(null);
    }

    public static ObjectMapper createObjectMapper(Class<?> aClass) {
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        LocalDateTimeSerializer localDateTimeSerializer = new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        javaTimeModule.addSerializer(LocalDateTime.class, localDateTimeSerializer);

        LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer(dateTimeFormatter);
        javaTimeModule.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);

        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder
            .json()
            .serializationInclusion(JsonInclude.Include.NON_NULL)
            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .modules(javaTimeModule)
            .indentOutput(true)
            .build();

        if (aClass != null) {
            objectMapper.writerWithView(aClass);
        }

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.registerModule(new SimpleModule() {
            @Override
            public void setupModule(SetupContext context) {
                super.setupModule(context);
                context.addBeanSerializerModifier(new BeanSerializerModifier() {
                    @Override
                    public JsonSerializer<?> modifySerializer(SerializationConfig config, BeanDescription desc, JsonSerializer<?> serializer) {
                        if (desc.getBeanClass().equals(Optional.class)) {
                            return null;
                        }

                        return serializer;
                    }
                });
            }
        });
        return objectMapper;
    }

    private static DateTimeFormatter createDateTimeFormatter() {
        DateTimeFormatterBuilder dateTimeFormatterBuilder =
            new DateTimeFormatterBuilder();

        dateTimePatternList
            .forEach(i -> dateTimeFormatterBuilder.appendOptional(DateTimeFormatter.ofPattern(i).withZone(ZoneId.systemDefault())));
        TimeZone timeZone = TimeZone.getTimeZone(ZoneId.systemDefault());

        return dateTimeFormatterBuilder
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .parseDefaulting(ChronoField.NANO_OF_SECOND, 0)
            .parseDefaulting(ChronoField.OFFSET_SECONDS, timeZone.getRawOffset()/1000)
            .toFormatter();
    }

    public static String formatSql(String sql) {
        return new BasicFormatterImpl().format(sql);
    }

    public static String toJson(Object object) {
        return toJson(objectMapper, object);
    }

    public static String toJson(Object object, Class<?> aClass) {
        return toJson(createObjectMapper(aClass), object);
    }

    public static String toJson(ObjectMapper objectMapper, Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            logger.error("error", e);
        }

        return null;
    }

    public static <T> T parseJson(String json, Class<T> tClass) {
        try {
            return objectMapper.readValue(json, tClass);
        } catch (Exception e) {
            logger.error("error", e);
        }

        return null;
    }

    public static LocalDateTime parseLocalDateTime(String text) {
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(text, dateTimeFormatter);
        return LocalDateTime.ofInstant(offsetDateTime.toInstant(), ZoneId.systemDefault());
    }

    public static String createUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String urlEncode(String text) {
        try {
            return new URLCodec().encode(text);
        } catch (Exception e) {
        }
        return "";
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static String formatDateTime(LocalDateTime localDateTime) {
        return formatDateTime(localDateTime, dateTimePattern);
    }

    public static String formatDateTime(LocalDateTime localDateTime, String pattern) {
        if (localDateTime == null) {
            return "";
        }

        return DateTimeFormatter
            .ofPattern(pattern)
            .format(localDateTime);
    }

    public static <T, U> Optional<T> mapAndIfPresent(Optional<T> optional, Function<? super T, ? extends U> mapper, Consumer<? super U> consumer) {
        optional.map(mapper).ifPresent(consumer);
        return optional;
    }
}
