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
package org.appcomponents.platform.mvc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.appcomponents.platform.api.Component;
import org.appcomponents.platform.api.Platform;
import org.appcomponents.platform.http.ComponentRelativeHttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

/**
 * Platform request handler. Handler is responsible for handling request between App Components.
 * @author Martin Janys
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PlatformRequestMappingHandlerMapping implements HandlerMapping, ApplicationContextAware {

	private ApplicationContext applicationContext;
	protected static final Log logger = LogFactory.getLog(PlatformRequestMappingHandlerMapping.class); // todo logger ?

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
		Platform platform = this.applicationContext.getBean(Platform.class);
		Component component = resolveComponent(platform, request);
		if (component != null) {
			if (!component.isRelativeContextPath()) {
				return handle(request, component);
			}
			else {
				ComponentRelativeHttpServletRequest relativeRequest = new ComponentRelativeHttpServletRequest(request, component.getComponentName());
				return handle(relativeRequest, component);
			}
		}
		else {
			return null;
		}
	}

	protected Component resolveComponent(Platform platform, HttpServletRequest request) {
		String componentName = request.getParameter(Platform.PARAM_COMPONENT);
		if (Platform.NONE_COMPONENT.equals(componentName)) {
			return platform.getRootModule();
		}
		else {
			for (Component component : platform.getChildComponents()) {
				if (component.getComponentName().equals(componentName)) {
					return component;
				}
				else if (component.isRelativeContextPath() && isComponentRelativePath(component.getComponentName(), request.getRequestURI())) {
					return component;
				}
			}
			return platform.getDefaultComponent();
		}
	}

	private boolean isComponentRelativePath(String componentName, String requestURI) {
		return requestURI.startsWith("/" + componentName);
	}

	protected HandlerExecutionChain handle(HttpServletRequest request, Component component) throws Exception {
		ApplicationContext applicationContext = component.getApplicationContext();
		if (this.applicationContext.equals(applicationContext)) {
			return null;
		}
		else {
			Map<String, HandlerMapping> handlerMappingMap = applicationContext.getBeansOfType(HandlerMapping.class);
			for (HandlerMapping hm : handlerMappingMap.values()) {
				if (hm.getClass().equals(getClass())) {
					continue;
				}
				if (logger.isTraceEnabled()) {
					logger.trace(
							"Testing handler map [" + hm + "] in PlatformRequestMappingHandlerMapping");
				}
				HandlerExecutionChain handler = hm.getHandler(request);
				if (handler != null) {
					return handler;
				}
			}
			return null;
		}
	}
}
