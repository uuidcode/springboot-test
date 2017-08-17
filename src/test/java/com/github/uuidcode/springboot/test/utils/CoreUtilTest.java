package com.github.uuidcode.springboot.test.utils;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


public class CoreUtilTest {
    @Test
    public void parseLocalDateTime() {
        LocalDateTime localDateTime = LocalDateTime.of(2010, 1, 1, 0, 0);

        assertThat(CoreUtil.parseLocalDateTime("2010-01-01")).isEqualTo(localDateTime);
        assertThat(CoreUtil.parseLocalDateTime("2010-01-01 01")).isEqualTo(localDateTime.withHour(1));
        assertThat(CoreUtil.parseLocalDateTime("2010-01-01 01:01")).isEqualTo(localDateTime.withHour(1).withMinute(1));
        assertThat(CoreUtil.parseLocalDateTime("2010-01-01 01:01:01")).isEqualTo(localDateTime.withHour(1).withMinute(1).withSecond(1));

        assertThat(CoreUtil.parseLocalDateTime("2010/01/01")).isEqualTo(localDateTime);
        assertThat(CoreUtil.parseLocalDateTime("2010/01/01 01")).isEqualTo(localDateTime.withHour(1));
        assertThat(CoreUtil.parseLocalDateTime("2010/01/01 01:01")).isEqualTo(localDateTime.withHour(1).withMinute(1));
        assertThat(CoreUtil.parseLocalDateTime("2010/01/01 01:01:01")).isEqualTo(localDateTime.withHour(1).withMinute(1).withSecond(1));

        assertThat(CoreUtil.parseLocalDateTime("2010.01.01")).isEqualTo(localDateTime);
        assertThat(CoreUtil.parseLocalDateTime("2010.01.01 01")).isEqualTo(localDateTime.withHour(1));
        assertThat(CoreUtil.parseLocalDateTime("2010.01.01 01:01")).isEqualTo(localDateTime.withHour(1).withMinute(1));
        assertThat(CoreUtil.parseLocalDateTime("2010.01.01 01:01:01")).isEqualTo(localDateTime.withHour(1).withMinute(1).withSecond(1));

        assertThat(CoreUtil.parseLocalDateTime("2001-07-04T12:08:56.235-0700")).isEqualTo(LocalDateTime.of(2001, 7, 5, 4, 8, 56, 235000000));
        assertThat(CoreUtil.parseLocalDateTime("2001-07-04T12:08:56.235-07:00")).isEqualTo(LocalDateTime.of(2001, 7, 5, 4, 8, 56, 235000000));
        assertThat(CoreUtil.parseLocalDateTime("2016-02-11T10:16:17+09:00")).isEqualTo(LocalDateTime.of(2016, 2, 11, 10, 16, 17, 0));
        assertThat(CoreUtil.parseLocalDateTime("2016-02-11T10:16:17")).isEqualTo(LocalDateTime.of(2016, 2, 11, 10, 16, 17, 0));
    }
}