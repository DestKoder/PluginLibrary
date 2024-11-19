package ru.dest.library.dependency;

import io.vavr.control.Option;
import io.vavr.control.Try;
import org.apache.commons.io.FileUtils;
import ru.dest.library.Library;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;

/**
 * Class for working with runtime dependencies
 *
 * @since 1.2
 * @author DestKoder
 */
public final class RuntimeDependency {

    /**
     * Load dependency if not loaded
     * @param clName Main class of dependency to check if loaded
     * @param remoteUrl Url for download dependency
     * @return Optional of {@link URLClassLoader} for loading class...
     * @throws Exception if any error occupied
     */
    public static Optional<ClassLoader> loadIfAbsent(String clName, String remoteUrl) throws Exception {
        if(isLoaded(clName)) return Optional.of(Class.forName(clName).getClassLoader());

        String[] data = remoteUrl.split("/");
        File localDepFile = new File(Library.get().getFolder(), "libs/" + data[data.length-1] + (data[data.length-1].endsWith(".jar") ? "" : ".jar"));

        if(!localDepFile.exists()){
            download(remoteUrl, localDepFile);
        }

        return loadJar(localDepFile);
    }

    public static void download(String url, File to) throws Exception{
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .connectTimeout(Duration.ofSeconds(30000))
                .build();
        HttpRequest request = HttpRequest.newBuilder().GET().uri(new URL(url).toURI()).build();
        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
        FileUtils.copyInputStreamToFile(response.body(), to);
    }

    public static Optional<ClassLoader> loadJar(File file){
        return Try.ofCallable(() -> (ClassLoader) new URLClassLoader(new URL[]{
                file.toURI().toURL()
        }, RuntimeDependency.class.getClassLoader())).toJavaOptional();
    }


    private static boolean isLoaded(String cl){
        try{
            Class.forName(cl);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

}
