package ru.dest.library.dependency;

import io.vavr.control.Try;
import org.apache.commons.io.FileUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import ru.dest.library.Library;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Optional;

public final class RuntimeDependency {

    public static Optional<URLClassLoader> loadIfAbsent(String clName, String remoteUrl) throws Exception {
        if(isLoaded(clName)) return Optional.empty();

        String[] data = remoteUrl.split("/");
        File localDepFile = new File(Library.get().getFolder(), "libs/" + data[data.length-1] + (data[data.length-1].endsWith(".jar") ? "" : ".jar"));

        if(!localDepFile.exists()){
//            FileUtils.copyURLToFile(new URL(remoteUrl), localDepFile);
            Connection.Response res = Jsoup.connect(remoteUrl)
                    .userAgent("PluginLibrary")
                    .timeout(30000)
                    .followRedirects(true)
                    .ignoreContentType(true)
                    .maxBodySize(Integer.MAX_VALUE)//Increase value if download is more than 20MB
                    .execute();
            FileOutputStream out = (new FileOutputStream(localDepFile));
            out.write( res.bodyAsBytes());
            out.close();
        }

        return Try.ofCallable(() -> new URLClassLoader(new URL[]{
                localDepFile.toURI().toURL()
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
