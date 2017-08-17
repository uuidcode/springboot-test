package com.github.uuidcode.springboot.test.domain;

import java.io.IOException;
import java.io.Writer;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;

import com.samskivert.mustache.Mustache.Compiler;
import com.samskivert.mustache.Mustache.Lambda;
import com.samskivert.mustache.Template;

public class Layout implements Lambda {
    public static enum LayoutType {
        DEFAULT, TOP;
    }

    private String title;
    private String body;
    private Compiler compiler;
    private LayoutType layoutType;
    private String layoutPrefix;

    public String getLayoutPrefix() {
        return this.layoutPrefix;
    }

    public Layout setLayoutPrefix(String layoutPrefix) {
        this.layoutPrefix = layoutPrefix;
        return this;
    }

    public LayoutType getLayoutType() {
        return this.layoutType;
    }

    public Layout setLayoutType(LayoutType layoutType) {
        this.layoutType = layoutType;
        return this;
    }

    public Compiler getCompiler() {
        return this.compiler;
    }

    public Layout setCompiler(Compiler compiler) {
        this.compiler = compiler;
        return this;
    }

    public String getBody() {
        return this.body;
    }

    public Layout setBody(String body) {
        this.body = body;
        return this;
    }
    public String getTitle() {
        return this.title;
    }

    public Optional<String> getTitleOptional() {
        return Optional.ofNullable(this.title);
    }

    public Layout setTitle(String title) {
        this.title = title;
        return this;
    }

    public Layout titleIsNull() {
        Assertions.assertThat(this.title).isNull();
        return this;
    }

    public Layout titleIsNotNull() {
        Assertions.assertThat(this.title).isNotNull();
        return this;
    }

    public Layout titleIsEqualTo(String title) {
        Assertions.assertThat(this.title).isEqualTo(title);
        return this;
    }

    @Override
    public void execute(Template.Fragment fragment, Writer writer) throws IOException {
        this.body = fragment.execute();

        String template =
            Stream.<String>builder()
                .add("{{>")
                .add(Optional.ofNullable(this.layoutPrefix).orElse(""))
                .add("layout/")
                .add(this.layoutType.name().toLowerCase())
                .add("}}")
                .build()
                .collect(Collectors.joining());

        this.compiler
            .compile(template)
            .execute(fragment.context(), writer);
    }
}
