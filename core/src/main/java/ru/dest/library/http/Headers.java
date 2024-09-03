package ru.dest.library.http;

import java.util.HashMap;

public class Headers extends HashMap<String, String> {

    public Headers(){
        put("User-Agent", "PluginLibrary");
    }

    public Headers add(String header, String value){
        put(header, value);
        return this;
    }

}
