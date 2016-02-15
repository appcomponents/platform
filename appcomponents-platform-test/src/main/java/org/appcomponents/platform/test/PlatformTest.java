package org.appcomponents.platform.test;

import org.appcomponents.platform.test.loader.PlatformContextLoader;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.*;

/**
 * @author Martin Janys
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented

@ContextConfiguration(loader = PlatformContextLoader.class, classes = EmptyConfiguration.class)
public @interface PlatformTest {

    Class value();

}
