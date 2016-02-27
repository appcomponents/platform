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
package org.appcomponents.platform.api;

import java.util.Set;

import org.springframework.context.ApplicationContextAware;

/**
 * App Component Platform interface.
 * @author Martin Janys
 */
public interface PlatformComponent extends ApplicationContextAware {

	/**
	 * Request parameter witch determines App Component for request.
	 */
	String PARAM_COMPONENT = "component";

	/**
	 * Value of {@link PlatformComponent#PARAM_COMPONENT} witch describe no module. Fallback is root component.
	 */
	String NONE_COMPONENT = "NONE";

	/**
	 * Root compoennt name.
	 */
	String ROOT_COMPONENT_NAME = "root";

	Component getRootComponent();

	Component getDefaultComponent();

	Set<Component> getComponents();
}
