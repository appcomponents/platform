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
package org.appcomponents.platform.component;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.servlet.ServletContext;

import org.appcomponents.platform.api.Component;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.Assert;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.WebApplicationContext;

/**
 * Implementation of {@link Component}.
 * It contains name, context and child components.
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
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		DefaultAppComponent that = (DefaultAppComponent) o;
		return Objects.equals(this.componentName, that.componentName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.componentName);
	}

	public Map<String, Component> getAppComponentsMap() {
		Map<String, Component> map = new HashMap<>();
		for (Component appComponent : this.appComponents) {
			map.put(appComponent.getComponentName(), appComponent);
		}
		return map;
	}

	@Override
	public String getComponentName() {
		return this.componentName;
	}

	public WebApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

	@Override
	public void refresh() {
		WebApplicationContext applicationContext = this.applicationContext;
		Set<Component> appComponents = this.appComponents;

		Assert.isInstanceOf(ConfigurableApplicationContext.class, applicationContext);
		ServletContext servletContext = applicationContext.getServletContext();
		appComponents.forEach(appComponent -> appComponent.refresh(servletContext));
	}

	@Override
	public void refresh(ServletContext servletContext) {
		WebApplicationContext applicationContext = this.applicationContext;
		Assert.isInstanceOf(ConfigurableWebApplicationContext.class, applicationContext);

		((ConfigurableWebApplicationContext) applicationContext).setServletContext(servletContext);
		((ConfigurableWebApplicationContext) applicationContext).refresh();
	}
}
