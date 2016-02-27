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
package org.appcomponents.platform.impl;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.appcomponents.platform.api.Component;
import org.appcomponents.platform.api.PlatformComponent;
import org.appcomponents.platform.component.AppComponent;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * App Components Platform. Extend this class for use App Components Platform.
 *
 * @author Martin Janys
 */
public class RootPlatformComponent implements PlatformComponent {

	private final Map<String, Component> components = new LinkedHashMap<>();

	private Component rootComponent;
	private String defaultComponentName;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.rootComponent = new AppComponent(PlatformComponent.ROOT_COMPONENT_NAME, (ConfigurableApplicationContext) applicationContext);
	}

	public PlatformComponent addChild(String componentName, AnnotationConfigWebApplicationContext context) {
		AppComponent appComponent = new AppComponent(componentName, context);
		this.components.put(componentName, appComponent);
		return this;
	}

	public PlatformComponent setDefaultComponentName(String defaultComponentName) {
		this.defaultComponentName = defaultComponentName;
		return this;
	}

	public String getDefaultComponentName() {
		return this.defaultComponentName;
	}

	@Override
	public Component getRootComponent() {
		return this.rootComponent;
	}

	@Override
	public Component getDefaultComponent() {
		return this.components.getOrDefault(getDefaultComponentName(), this.rootComponent);
	}

	@Override
	public Set<Component> getComponents() {
		return new LinkedHashSet<>(this.components.values());
	}
}
