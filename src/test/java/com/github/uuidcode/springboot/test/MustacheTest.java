package com.github.uuidcode.springboot.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;

import org.junit.Test;
import org.springframework.boot.autoconfigure.mustache.MustacheResourceTemplateLoader;

import com.github.uuidcode.springboot.test.entity.Project;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.MustacheException;

public class MustacheTest {
    @Test
    public void test() {
        assertThat(
            Mustache
                .compiler()
                .compile("Hello, {{name}}!")
                .execute(new Project().setName("World"))).isEqualTo("Hello, World!");

        assertThat(
            Mustache
                .compiler()
                .compile("Hello, {{{name}}}!")
                .execute(new Project().setName("<html>"))).isEqualTo("Hello, <html>!");

        assertThatThrownBy(() ->
            Mustache
                .compiler()
                .compile("Hello, {{{name}}}!")
                .execute(new HashMap())).isInstanceOf(MustacheException.class);

        assertThat(
            Mustache
                .compiler()
                .compile("Hello, {{#name}}{{{this}}}{{/name}}!")
                .execute(new HashMap())).isEqualTo("Hello, !");

        assertThat(
            Mustache
                .compiler()
                .compile("Hello, {{^name}}Empty{{/name}}!")
                .execute(new HashMap())).isEqualTo("Hello, Empty!");

        assertThat(
            Mustache
                .compiler()
                .compile("Hello, {{#name}}{{{this}}}{{/name}}!")
                .execute(new Project().setName("<html>"))).isEqualTo("Hello, <html>!");

        assertThat(
            Mustache.compiler()
                .withLoader(new MustacheResourceTemplateLoader("templates/", ".html"))
                .compile("Hello, {{>world}}!")
                .execute(new Project().setName("<html>"))).isEqualTo("Hello, <html>!");
    }
}
