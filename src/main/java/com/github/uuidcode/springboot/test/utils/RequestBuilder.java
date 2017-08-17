package com.github.uuidcode.springboot.test.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.uuidcode.springboot.test.configuration.BinderConfiguration;
import com.github.uuidcode.springboot.test.domain.Result;

public class RequestBuilder {
    public static enum LatencyType {
        HIGH(1_000, 1_000),
        MIDDLE(3_000, 3_000),
        LOW(10_000, 10_000),
        VERY_LOW(30_000, 30_000);

        private Integer connectionTimeout;
        private Integer socketTimeout;

        LatencyType(Integer connectionTimeout, Integer socketTimeout) {
            this.connectionTimeout = connectionTimeout;
            this.socketTimeout = socketTimeout;
        }
    }

    protected Logger logger = LoggerFactory.getLogger(getClass());
    private Request request;
    private LatencyType latencyType = LatencyType.MIDDLE;

    public RequestBuilder get(String url) {
        this.request = Request.Get(url);
        return this;
    }

    public RequestBuilder get(String url, Object object) {
        return this.get(url, toQueryString(object));
    }

    public RequestBuilder get(String url, String queryString) {
        this.request = Request.Get(url + "?" + queryString);
        return this;
    }

    public RequestBuilder post(String url) {
        this.request = Request.Post(url);
        return this;
    }

    public RequestBuilder put(String url) {
        this.request = Request.Put(url);
        return this;
    }

    public RequestBuilder delete(String url) {
        this.request = Request.Delete(url);
        return this;
    }

    public RequestBuilder setLatencyType(LatencyType latencyType) {
        this.latencyType = latencyType;
        return this;
    }

    public RequestBuilder addHeader(String name, String value) {
        this.request.addHeader(name, value);
        return this;
    }

    public RequestBuilder addHeader(Header header) {
        this.request.addHeader(header);
        return this;
    }

    public RequestBuilder addHeader(List<Header> headerList) {
        Optional.of(headerList)
            .ifPresent(
                list -> {
                    list.forEach(this::addHeader);
                }
            );
        return this;
    }

    public RequestBuilder body(Object object) {
        this.request = this.request.bodyForm(this.toNameValuePairList(object), Consts.UTF_8);
        return this;
    }

    public RequestBuilder body(HttpEntity entity) {
        this.request = this.request.body(entity);
        return this;
    }

    public RequestBuilder body(String fileName, InputStream inputStream) {
        try {
            String urlEncodedFileName = CoreUtil.urlEncode(fileName);

            if (this.logger.isDebugEnabled()) {
                this.logger.debug("urlEncodedFileName:" + fileName);
            }

            ByteArrayBody fileBody = new ByteArrayBody(IOUtils.toByteArray(inputStream), urlEncodedFileName);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addPart("file", fileBody);

            return
                this.body(
                    MultipartEntityBuilder
                        .create()
                        .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                        .addPart("file", fileBody)
                        .build());
        } catch (Exception e) {
            logger.error("body error", e);
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public RequestBuilder json(Object object) {
        try {
            this.addHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8");
            String json = CoreUtil.toJson(object);

            if (this.logger.isDebugEnabled()) {
                this.logger.debug(">>> json: " + json);
            }

            return this.body(new StringEntity(json, StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public RequestBuilder cookie(String cookie) {
        this.addHeader("Cookie", cookie);
        return this;
    }

    public RequestBuilder referer(String referer) {
        this.addHeader("Referer", referer);
        return this;
    }

    public RequestBuilder userAgent(String userAgent) {
        this.request.userAgent(userAgent);
        return this;
    }

    public Response execute() {
        try {
            this.request.connectTimeout(this.latencyType.connectionTimeout);
            this.request.socketTimeout(this.latencyType.socketTimeout);
            return
                Executor
                    .newInstance()
                    .use(new BasicCookieStore())
                    .execute(this.request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String executeAndGetCookie()  {
        try {
            HttpResponse response = this.execute().returnResponse();

            if (this.logger.isDebugEnabled()) {
                this.logger.debug(">>> content: " + EntityUtils.toString(response.getEntity()));
            }

            return Stream.of(response.getHeaders("Set-Cookie"))
                .map(header -> header.getValue())
                .map(value -> value.split(";")[0])
                .collect(Collectors.joining("; "));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int executeAndGetCode() {
        try {
            return this.execute().returnResponse().getStatusLine().getStatusCode();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String executeAndGetContent() {
        try {
            String content =
                this.execute()
                    .returnContent()
                    .asString(StandardCharsets.UTF_8);

            if (this.logger.isDebugEnabled()) {
                this.logger.debug(">>> content: " + content);
            }

            return content;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void execute(File file) {
        try {
            HttpEntity entity =
                this.execute()
                .returnResponse()
                .getEntity();

            if (entity != null) {
                InputStream in = entity.getContent();
                FileOutputStream out = new FileOutputStream(file);
                IOUtils.copy(in, out);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T execute(Class<T> tclass) {
        try {
            return CoreUtil.parseJson(this.executeAndGetContent(), tclass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String toQueryString(Object object) {
        if (object == null) {
            return "";
        }

        List<NameValuePair> list = null;

        if (object instanceof Map) {
            Map map = (Map) object;
            list =
                new ArrayList<Object>(map.keySet())
                    .stream()
                    .map(key -> {
                    Object value = map.get(key);
                    if (value == null) {
                        return null;
                    }

                    return new BasicNameValuePair(key.toString(), value.toString());
                })
                .collect(Collectors.toList());
        } else {
            list = this.toNameValuePairList(object);
        }

        return list
            .stream()
            .map(p -> p.getName() + "=" + CoreUtil.urlEncode(p.getValue()))
            .collect(Collectors.joining("&"));
    }

    public List<NameValuePair> toNameValuePairList(Object object) {
        List<NameValuePair> list = new ArrayList<>();

        if (object == null) {
            return list;
        }

        return Arrays
            .stream(object.getClass().getDeclaredFields())
            .filter(field -> !Modifier.isStatic(field.getModifiers()))
            .filter(field -> !Modifier.isFinal(field.getModifiers()))
            .map(field -> {
                field.setAccessible(true);

                Object value = null;

                try {
                    value = field.get(object);
                } catch (Exception e) {
                }

                String text = "";

                if (value != null) {
                    if (value instanceof LocalDateTime) {
                        BinderConfiguration.LocalDateTimeEditor localDateTimeEditor = new BinderConfiguration.LocalDateTimeEditor();
                        localDateTimeEditor.setValue(value);
                        text = localDateTimeEditor.getAsText();
                    } else if (value instanceof Integer) {
                        BinderConfiguration.IntegerEditor integerEditor = new BinderConfiguration.IntegerEditor();
                        integerEditor.setValue(value);
                        text = integerEditor.getAsText();
                    } else if (value instanceof Long) {
                        BinderConfiguration.LongEditor longEditor = new BinderConfiguration.LongEditor();
                        longEditor.setValue(value);
                        text = longEditor.getAsText();
                    } else {
                        text = value.toString();
                    }

                    return new BasicNameValuePair(field.getName(), text);
                }

                return null;
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    public Result executeAndGetResult() {
        try {
            return CoreUtil.parseJson(this.executeAndGetContent(), Result.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
