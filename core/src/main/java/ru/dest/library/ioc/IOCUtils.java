package ru.dest.library.ioc;

import lombok.experimental.UtilityClass;

/**
 * Some utils for work with Inversion Of Control
 */
@UtilityClass
public class IOCUtils {

    public String getSearchPackage(Class<?> cl){
        BasePackage annotation = cl.getDeclaredAnnotation(BasePackage.class);

        return annotation == null ? cl.getPackageName() : annotation.value();
    }

}
