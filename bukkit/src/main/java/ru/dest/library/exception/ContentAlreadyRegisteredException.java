package ru.dest.library.exception;

import ru.dest.library.object.RegistryKey;

public class ContentAlreadyRegisteredException extends RuntimeException{

    public ContentAlreadyRegisteredException(RegistryKey id){
        super("Content with id " + id  +" already registered");
    }

}
