/*
 * Copyright 2016 the original author or authors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.appcomponents.platform;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.appcomponents.platform.annotation.AppComponent;
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
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * App Components Platform. Extend this class for use App Components Platform.
 *
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
		Set<Component> modules = createChildAppComponents(this.componentConfigurations, (ConfigurableApplicationContext) this.applicationContext);
		this.rootModule = new DefaultAppComponent(getClass().getName(), (WebApplicationContext) this.applicationContext, modules);
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

		StandardAnnotationMetadata annotationMetadata = new StandardAnnotationMetadata(configuration);
		Map<String, Object> annotationAttributes = annotationMetadata.getAnnotationAttributes(AppComponent.class.getName());
		boolean relativePath = (boolean) annotationAttributes.getOrDefault("relativePath", false);

		return new DefaultAppComponent(name, applicationContext, relativePath);
	}

	protected AnnotationConfigWebApplicationContext createChildApplicationContext(Class cls) {
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		applicationContext.register(cls);
		return applicationContext;
	}

	public Platform addComponentConfigurations(String componentName, Class<?> componentConfiguration) {
		this.componentConfigurations.put(componentName, componentConfiguration);
		return this;
	}

	@Override
	public DefaultAppComponent getRootModule() {
		return this.rootModule;
	}

	public Platform setDefaultComponentName(String defaultComponentName) {
		this.defaultComponentName = defaultComponentName;
		return this;
	}

	public String getDefaultComponentName() {
		return this.defaultComponentName;
	}

	@Override
	public Component getDefaultComponent() {
		return getComponent(getDefaultComponentName());
	}

	@Override
	public Set<Component> getChildComponents() {
		return getRootModule().getChildComponents();
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
