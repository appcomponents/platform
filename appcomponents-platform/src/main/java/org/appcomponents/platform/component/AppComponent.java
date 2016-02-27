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

import java.util.Objects;

import org.appcomponents.platform.api.Component;

import org.springframework.context.ConfigurableApplicationContext;

/**
 * Implementation of {@link Component}.
 * It contains name, context and child components.
 * @author Martin Janys
 */
public class AppComponent implements Component {

	private final String componentName;
	private final ConfigurableApplicationContext applicationContext;
	private final boolean relativeContextPath;

	public AppComponent(String componentName, ConfigurableApplicationContext applicationContext) {
		this.componentName = componentName;
		this.applicationContext = applicationContext;
		this.relativeContextPath = true;
	}

	public AppComponent(String componentName, ConfigurableApplicationContext applicationContext, boolean relativeContextPath) {
		this.componentName = componentName;
		this.applicationContext = applicationContext;
		this.relativeContextPath = relativeContextPath;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		AppComponent that = (AppComponent) o;
		return Objects.equals(this.componentName, that.componentName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.componentName);
	}

	@Override
	public String getComponentName() {
		return this.componentName;
	}

	public ConfigurableApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

	@Override
	public boolean useContextPath() {
		return this.relativeContextPath;
	}

}
