package com.github.uuidcode.springboot.test.utils;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalEx<T> {
    private Optional<T> optional;

    private OptionalEx(Optional<T> optional) {
        this.optional = optional;
    }

    public static <T> OptionalEx<T> empty() {
        return new OptionalEx<>(Optional.empty());
    }

    public static <T> OptionalEx<T> of(T value) {
        return new OptionalEx<>(Optional.of(value));
    }

    public static <T> OptionalEx<T> ofNullable(T value) {
        return new OptionalEx<>(Optional.ofNullable(value));
    }

    public T get() {
        return optional.get();
    }

    public boolean isPresent() {
        return optional.isPresent();
    }

    public void ifPresent(Consumer<? super T> consumer) {
        optional.ifPresent(consumer);
    }

    public OptionalEx<T> filter(Predicate<? super T> predicate) {
        return new OptionalEx<>(optional.filter(predicate));
    }

    public <U> OptionalEx<U> map(Function<? super T, ? extends U> mapper) {
        return new OptionalEx<>(optional.map(mapper));
    }

    public <U> OptionalEx<T> mapAndIfPresent(Function<? super T, ? extends U> mapper, Consumer<? super U> consumer) {
        optional.map(mapper).ifPresent(consumer);
        return this;
    }

    public <U> OptionalEx<U> flatMap(Function<? super T, Optional<U>> mapper) {
        return new OptionalEx<>(optional.flatMap(mapper));
    }

    public T orElse(T other) {
        return optional.orElse(other);
    }

    public T orElseGet(Supplier<? extends T> other) {
        return optional.orElseGet(other);
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        return optional.orElseThrow(exceptionSupplier);
    }

    @Override
    public boolean equals(Object obj) {
        return optional.equals(obj);
    }

    @Override
    public int hashCode() {
        return optional.hashCode();
    }

    @Override
    public String toString() {
        return optional.toString();
    }
}
