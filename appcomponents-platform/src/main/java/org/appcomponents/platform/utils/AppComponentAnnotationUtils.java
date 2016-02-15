package org.appcomponents.platform.utils;

import org.appcomponents.platform.annotation.AppComponent;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * @author Martin Janys
 */
public class AppComponentAnnotationUtils {

    public static AppComponent findAppComponentAnnotation(Class cls) {
        AppComponent appAnnotation = AnnotationUtils.findAnnotation(cls, AppComponent.class);
        if (appAnnotation != null) {
            return appAnnotation;
        }

        throw new IllegalStateException("Missing AppComponent annotation");
    }

}
