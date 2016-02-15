package org.appcomponents.platform.test.loader;

import org.appcomponents.platform.test.PlatformTest;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.test.context.MergedContextConfiguration;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * @author Martin Janys
 */
public class PlatformContextLoader extends SpringApplicationContextLoader {

    private MergedContextConfiguration config;


    @Override
    public ApplicationContext loadContext(final MergedContextConfiguration config) throws Exception {
        this.config = config;
        return super.loadContext(config);
    }

    @Override
    protected SpringApplication getSpringApplication() {
        StandardAnnotationMetadata annotationMetadata = new StandardAnnotationMetadata(config.getTestClass());
        Map<String, Object> annotationAttributes = annotationMetadata.getAnnotationAttributes(PlatformTest.class.getName());
        Assert.notNull(annotationAttributes, "No PlatformTest is specified");

        @SuppressWarnings("unchecked")
        Class<SpringApplication> platformClass = (Class<SpringApplication>) annotationAttributes.get("value");
        Assert.isAssignable(SpringApplication.class, platformClass, "Platform must be instance of SpringApplication");
        return BeanUtils.instantiate(platformClass);
    }
}
