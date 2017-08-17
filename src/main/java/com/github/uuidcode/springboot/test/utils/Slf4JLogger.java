package com.github.uuidcode.springboot.test.utils;

import com.p6spy.engine.logging.Category;

public class Slf4JLogger extends com.p6spy.engine.spy.appender.Slf4JLogger {
    @Override
    public void logSQL(int connectionId, String now, long elapsed,
                       Category category, String prepared, String sql) {
        super.logSQL(connectionId, now, elapsed, category, prepared, sql);
    }
}
