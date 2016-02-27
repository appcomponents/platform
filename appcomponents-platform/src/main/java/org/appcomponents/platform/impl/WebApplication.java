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

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * Web Application.
 * @author Martin Janys
 */
public class WebApplication extends SpringApplication {

	public WebApplication(Object... sources) {
		super(sources);
	}

	public WebApplication(ResourceLoader resourceLoader, Object... sources) {
		super(resourceLoader, sources);
	}

	@Override
	protected ConfigurableApplicationContext createApplicationContext() {
		return new AnnotationConfigWebApplicationContext();
	}

	@Override
	public void setWebEnvironment(boolean webEnvironment) {
		Assert.isTrue(webEnvironment);
		super.setWebEnvironment(webEnvironment);
	}
}
