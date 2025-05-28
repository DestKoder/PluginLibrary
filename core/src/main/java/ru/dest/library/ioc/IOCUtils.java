package ru.dest.library.ioc;

import lombok.experimental.UtilityClass;

@UtilityClass
public class IOCUtils {

    public String getSearchPackage(Class<?> cl){
        BasePackage annotation = cl.getDeclaredAnnotation(BasePackage.class);

        return annotation == null ? cl.getPackageName() : annotation.value();
    }

}
