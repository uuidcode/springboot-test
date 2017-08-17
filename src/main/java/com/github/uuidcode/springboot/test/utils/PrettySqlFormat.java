package com.github.uuidcode.springboot.test.utils;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

public class PrettySqlFormat implements MessageFormattingStrategy {
    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql) {
        final StringBuffer formattedMessage = new StringBuffer();

        Arrays
            .stream(new Exception().getStackTrace())
            .filter(i -> !i.getClassName().equals(Slf4JLogger.class.getName()))
            .filter(i -> !i.getClassName().equals(PrettySqlFormat.class.getName()))
            .filter(i -> i.getClassName().startsWith("com.github.uuidcode.springboot.test"))
            .findFirst()
            .ifPresent(i -> {
                String currentClassName = i.getClassName();
                String simpleClassName = currentClassName.substring(currentClassName.lastIndexOf(".") + 1);
                String methodName = i.getMethodName();
                long line = i.getLineNumber();

                String message =
                    Stream.<String>builder()
                        .add(">>>")
                        .add(currentClassName + "." + methodName + "(" + simpleClassName + ".java:" + line + ")")
                        .add("connectionId: " + connectionId)
                        .add("category: " + category)
                        .add("elapsed: " + elapsed  + "ms")
                        .add("prepared: " + prepared)
                        .add("formatted prepared: " + CoreUtil.formatSql(prepared))
                        .add("sql: " + sql)
                        .add("formatted sql: " + CoreUtil.formatSql(sql))
                        .build()
                        .collect(Collectors.joining("\n"));

                formattedMessage.append(message);

            });

        return formattedMessage.toString();
    }
}