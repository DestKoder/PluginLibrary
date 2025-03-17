package ru.dest.library.http;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.vavr.collection.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Class for async working with Rest-API
 *
 * @since 1.4
 * @author DestKoder
 */
public class ApiWorker{

    protected final String baseUrl;
    protected final HttpClient httpClient;

    public ApiWorker(@NotNull String baseUrl) {
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(baseUrl.length()-1) : baseUrl;

        httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .connectTimeout(Duration.ofSeconds(30000))
                .build();
    }

    /**
     * Make a request
     * @param endpoint Endpoint on which we are making request
     * @param method What method we want to use
     * @param headers Headers of request
     * @param data Parameters
     * @param handler Result handler
     * @return {@link CompletableFuture} which contains request
     * @param <T> Type of result
     * @throws Exception if any exception occupied
     */
    public <T> CompletableFuture<HttpResponse<T>> make(@NotNull String endpoint, @NotNull  HttpMethod method, @NotNull Headers headers, @Nullable Map<String, Object> data, HttpResponse.BodyHandler<T> handler) throws Exception {
        return httpClient.sendAsync(method.createRequest(baseUrl + (endpoint.startsWith("/")? endpoint : "/" + endpoint), headers, data), handler);
    }

    /**
     * Make a request with json result
     * @param endpoint Endpoint on which we are making request
     * @param method What method we want to use
     * @param headers Headers of request
     * @param data Parameters
     * @param onSuccessConsumer Consumer of object if request is successful
     * @param onErrorConsumer Consumer of response if request is not successful
     */
    public void make(@NotNull String endpoint, @NotNull HttpMethod method, @NotNull Headers headers, @NotNull Map<String, Object> data, @NotNull Consumer<JsonElement> onSuccessConsumer,  @Nullable Consumer<HttpResponse<String>> onErrorConsumer) throws Exception {
        make(endpoint, method, headers.add("Accept", "application/json"), data, HttpResponse.BodyHandlers.ofString()).thenAcceptAsync(response -> {
            if(response.statusCode() >= 200 && response.statusCode() < 300) onSuccessConsumer.accept(JsonParser.parseString(response.body()));
            else if(onErrorConsumer != null) onErrorConsumer.accept(response);
        });
    }
}
