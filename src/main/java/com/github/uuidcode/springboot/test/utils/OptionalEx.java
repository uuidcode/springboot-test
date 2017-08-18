package com.github.uuidcode.springboot.test.utils;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class OptionalEx<T> {
    private static final OptionalEx<?> EMPTY = new OptionalEx<>();

    private final T value;

    public static <T> OptionalEx<T> ofNullable(T value) {
        return value == null ? empty() : of(value);
    }

    public static<T> OptionalEx<T> empty() {
        @SuppressWarnings("unchecked")
        OptionalEx<T> t = (OptionalEx<T>) EMPTY;
        return t;
    }

    public static <T> OptionalEx<T> of(T value) {
        return new OptionalEx<>(value);
    }

    private OptionalEx(T value) {
        this.value = Objects.requireNonNull(value);
    }

    private OptionalEx() {
        this.value = null;
    }

    public <U> OptionalEx<T> mapAndIfPresent(Function<? super T, ? extends U> mapper, Consumer<? super U> consumer) {
        this.map(mapper).ifPresent(consumer);
        return this;
    }

    public<U> OptionalEx<U> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent())
            return empty();
        else {
            return OptionalEx.ofNullable(mapper.apply(value));
        }
    }

    public boolean isPresent() {
        return value != null;
    }

    public void ifPresent(Consumer<? super T> consumer) {
        if (value != null) {
            consumer.accept(value);
        }
    }
}
