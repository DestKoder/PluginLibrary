package ru.dest.library.http;

import io.vavr.collection.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.dest.library.Library;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;

public abstract class HttpMethod {

    private static String buildUrlParams(Map<String, Object> params){
        StringBuffer sb = new StringBuffer();

        params.forEach((key,val) -> {
            sb.append(key).append('-').append(val.toString()).append('&');
        });
        return sb.toString();
    }

    public static HttpMethod GET = new HttpMethod() {
        @Override
        public @NotNull HttpRequest createRequest(@NotNull String url, @NotNull Headers headers, @Nullable Map<String, Object> data) throws URISyntaxException {
            HttpRequest.Builder builder = HttpRequest.newBuilder(new URI(data == null || data.isEmpty() ? url : url +"?"+HttpMethod.buildUrlParams(data))).GET();
            headers.forEach(builder::setHeader);
            return builder.build();
        }
    };

    public static HttpMethod DELETE = new HttpMethod() {
        @Override
        public @NotNull HttpRequest createRequest(@NotNull String url, @NotNull Headers headers, @Nullable Map<String, Object> data) throws URISyntaxException {
            HttpRequest.Builder builder = HttpRequest.newBuilder(new URI(data == null || data.isEmpty() ? url : url +"?"+HttpMethod.buildUrlParams(data))).DELETE();
            headers.forEach(builder::setHeader);
            return builder.build();
        }
    };


    public static HttpMethod POST = new HttpMethod() {
        @Override
        public HttpRequest createRequest(@NotNull String url, @NotNull Headers headers, @Nullable Map<String, Object> data) throws URISyntaxException {
            HttpRequest.Builder builder = HttpRequest.newBuilder(new URI(url));
            headers.forEach(builder::setHeader);
            if(data != null && !data.isEmpty()){
                HttpRequest.BodyPublisher publisher = HttpRequest.BodyPublishers.ofString(Library.builder.create().toJson(data));
                builder.POST(publisher);
            }
            return builder.build();
        }
    };

    public static HttpMethod PUT = new HttpMethod() {
        @Override
        public HttpRequest createRequest(@NotNull String url, @NotNull Headers headers, @Nullable Map<String, Object> data) throws URISyntaxException {
            HttpRequest.Builder builder = HttpRequest.newBuilder(new URI(url));
            headers.forEach(builder::setHeader);
            if(data != null && !data.isEmpty()){
                HttpRequest.BodyPublisher publisher = HttpRequest.BodyPublishers.ofString(Library.builder.create().toJson(data));
                builder.PUT(publisher);
            }
            return builder.build();
        }
    };

    public abstract HttpRequest createRequest(@NotNull String url,@NotNull Headers headers,@Nullable Map<String, Object> data) throws URISyntaxException;
}
