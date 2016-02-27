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

import javax.servlet.ServletContext;

import org.appcomponents.platform.api.Component;
import org.appcomponents.platform.api.PlatformComponent;

import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.WebApplicationContext;

/**
 * Embedded Web Application.
 * @author Martin Janys
 */
public class EmbeddedWebApplication extends WebApplication {

	protected final PlatformComponent platform;

	public EmbeddedWebApplication(PlatformComponent platform, Object... sources) {
		super(sources);
		this.platform = platform;
	}

	public EmbeddedWebApplication(PlatformComponent platform, ResourceLoader resourceLoader, Object... sources) {
		super(resourceLoader, sources);
		this.platform = platform;
	}

	@Override
	protected ConfigurableApplicationContext createApplicationContext() {
		AnnotationConfigEmbeddedWebApplicationContext context = new AnnotationConfigEmbeddedWebApplicationContext();
		this.platform.setApplicationContext(context);
		context.getBeanFactory().registerSingleton(this.platform.getClass().getCanonicalName(), this.platform);

		return context;
	}

	@Override
	protected void refresh(ApplicationContext applicationContext) {
		super.refresh(applicationContext);
		for (Component component : this.platform.getComponents()) {
			refreshChildWebApplicationContext((WebApplicationContext) applicationContext, (WebApplicationContext) component.getApplicationContext());
		}
	}

	private void refreshChildWebApplicationContext(WebApplicationContext parent, WebApplicationContext child) {
		ServletContext servletContext = parent.getServletContext();
		((ConfigurableWebApplicationContext) child).setServletContext(servletContext);
		((ConfigurableWebApplicationContext) child).refresh();
	}
}
