package com.github.uuidcode.springboot.test.configuration;

import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import com.github.uuidcode.springboot.test.utils.CoreUtil;

@ControllerAdvice
public class BinderConfiguration {
    public static class LocalDateTimeEditor extends PropertyEditorSupport {
        public LocalDateTimeEditor() {
            super();
        }

        public LocalDateTimeEditor(Object source) {
            super(source);
        }

        public void setAsText(String value) {
            if (CoreUtil.isEmpty(value)) {
                this.setValue(null);
            } else {
                setValue(CoreUtil.parseLocalDateTime(value.trim()));
            }
        }

        public String getAsText() {
            return CoreUtil.formatDateTime((LocalDateTime) this.getValue());
        }
    }

    public static class StringEditor extends PropertyEditorSupport {
        public StringEditor() {
            super();
        }

        public StringEditor(Object source) {
            super(source);
        }

        public void setAsText(String value) {
            if (CoreUtil.isEmpty(value)) {
                this.setValue(null);
            } else {
                setValue(value.trim());
            }
        }

        public String getAsText() {
            Object value = this.getValue();

            if (value == null) {
                return null;
            }

            return value.toString();
        }
    }

    public static class LongEditor extends PropertyEditorSupport {
        public LongEditor() {
            super();
        }

        public LongEditor(Object source) {
            super(source);
        }

        @Override
        public void setAsText(String text) {
            if (CoreUtil.isEmpty(text)) {
                this.setValue(null);
            } else {
                try {
                    this.setValue(Long.parseLong(text.trim().replaceAll("\\,", ""), 10));
                } catch (Exception e) {
                    this.setValue(null);
                }
            }
        }

        @Override
        public String getAsText() {
            Object object = this.getValue();
            if (object == null) {
                return "";
            }

            return object.toString();
        }
    };

    public static class IntegerEditor extends PropertyEditorSupport {
        public IntegerEditor() {
            super();
        }

        public IntegerEditor(Object source) {
            super(source);
        }

        @Override
        public void setAsText(String text) {
            if (CoreUtil.isEmpty(text)) {
                this.setValue(null);
            } else {
                try {
                    this.setValue(Integer.parseInt(text.trim().replaceAll("\\,", ""), 10));
                } catch (Exception e) {
                    this.setValue(null);
                }
            }
        }

        @Override
        public String getAsText() {
            Object object = this.getValue();
            if (object == null) {
                return "";
            }

            return object.toString();
        }
    };

    @InitBinder
    public void binder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDateTime.class, new LocalDateTimeEditor());
        binder.registerCustomEditor(String.class, new StringEditor());
        binder.registerCustomEditor(Long.class, new LongEditor());
        binder.registerCustomEditor(long.class, new LongEditor());
        binder.registerCustomEditor(Integer.class, new IntegerEditor());
        binder.registerCustomEditor(int.class, new IntegerEditor());
    }
}
