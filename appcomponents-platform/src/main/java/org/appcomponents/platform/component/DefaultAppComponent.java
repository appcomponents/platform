package org.appcomponents.platform.component;

import org.appcomponents.platform.api.Component;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.Assert;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.*;

/**
 * @author Martin Janys
 */
public class DefaultAppComponent implements Component {

    private final String componentName;
    private final WebApplicationContext applicationContext;
    private final Set<Component> appComponents;

    public DefaultAppComponent(String componentName, WebApplicationContext applicationContext, Set<Component> appComponents) {
        this.componentName = componentName;
        this.applicationContext = applicationContext;
        this.appComponents = Collections.unmodifiableSet(new HashSet<>(appComponents));
    }

    public DefaultAppComponent(String componentName, WebApplicationContext applicationContext) {
        this.componentName = componentName;
        this.applicationContext = applicationContext;
        this.appComponents = Collections.emptySet();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultAppComponent that = (DefaultAppComponent) o;
        return Objects.equals(componentName, that.componentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(componentName);
    }

    public Map<String, Component> getAppComponentsMap() {
        Map<String, Component> map = new HashMap<>();
        for (Component appComponent : appComponents) {
            map.put(appComponent.getComponentName(), appComponent);
        }
        return map;
    }

    @Override
    public String getComponentName() {
        return componentName;
    }

    public WebApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void refresh() {
        Assert.isInstanceOf(ConfigurableApplicationContext.class, applicationContext);
        ServletContext servletContext = applicationContext.getServletContext();
        appComponents.forEach(appComponent -> appComponent.refresh(servletContext));
    }

    @Override
    public void refresh(ServletContext servletContext) {
        Assert.isInstanceOf(ConfigurableWebApplicationContext.class, applicationContext);

        ((ConfigurableWebApplicationContext)applicationContext).setServletContext(servletContext);
        ((ConfigurableWebApplicationContext)applicationContext).refresh();
    }
}
