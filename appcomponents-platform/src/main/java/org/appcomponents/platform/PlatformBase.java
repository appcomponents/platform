package org.appcomponents.platform;

import org.appcomponents.platform.api.Component;
import org.appcomponents.platform.api.Platform;
import org.appcomponents.platform.component.DefaultAppComponent;
import org.appcomponents.platform.configuration.PlatformConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.util.*;

/**
 * @author Martin Janys
 */
@SpringBootApplication
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Object.class)) // exclude all
public class PlatformBase extends SpringApplication implements Platform, InitializingBean, ApplicationContextAware {

    private final Map<String, Class<?>> componentConfigurations;
    private ApplicationContext applicationContext;

    private DefaultAppComponent rootModule;
    private String defaultComponentName;

    public PlatformBase() {
        super(PlatformConfiguration.class);
        this.componentConfigurations = new HashMap<>();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    protected ConfigurableApplicationContext createApplicationContext() {
        return new AnnotationConfigEmbeddedWebApplicationContext();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Set<Component> modules = createChildAppComponents(componentConfigurations, (ConfigurableApplicationContext) applicationContext);
        this.rootModule = new DefaultAppComponent(getClass().getName(), (WebApplicationContext) applicationContext, modules);
        this.rootModule.refresh();
    }

    protected Set<Component> createChildAppComponents(Map<String, Class<?>> componentConfigurations, ConfigurableApplicationContext applicationContext) {
        Set<Component> components = new HashSet<>();
        for (String componentName : componentConfigurations.keySet()) {
            Class<?> configuration = componentConfigurations.get(componentName);
            Component component = addChildAppComponent(componentName, configuration, applicationContext);
            Assert.isTrue(!components.contains(component));
            components.add(addChildAppComponent(componentName, configuration, applicationContext));
        }
        return components;
    }

    private Component addChildAppComponent(String name, Class<?> configuration, ConfigurableApplicationContext parentContext) {
        AnnotationConfigWebApplicationContext applicationContext = createChildApplicationContext(configuration);
        applicationContext.setParent(parentContext);

        return new DefaultAppComponent(name, applicationContext);
    }

    protected AnnotationConfigWebApplicationContext createChildApplicationContext(Class cls) {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(cls);
        return applicationContext;
    }

    public Platform addComponentConfigurations(String componentName, Class<?> componentConfiguration) {
        componentConfigurations.put(componentName, componentConfiguration);
        return this;
    }

    @Override
    public DefaultAppComponent getRootModule() {
        return rootModule;
    }

    public Platform setDefaultComponentName(String defaultComponentName) {
        this.defaultComponentName = defaultComponentName;
        return this;
    }

    public String getDefaultComponentName() {
        return defaultComponentName;
    }

    @Override
    public Component getDefaultComponent() {
        return getComponent(getDefaultComponentName());
    }

    @Override
    public Component getComponent(String componentName) {
        if (StringUtils.isEmpty(componentName)) {
            return getRootModule();
        }
        else {
            Map<String, Component> components = getRootModule().getAppComponentsMap();
            return components.get(componentName);
        }
    }
}
