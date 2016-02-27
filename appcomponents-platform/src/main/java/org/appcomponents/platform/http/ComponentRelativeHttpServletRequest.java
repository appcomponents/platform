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
package org.appcomponents.platform.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Wrapper for relative components handling.
 *
 * @author Martin Janys
 */
public class ComponentRelativeHttpServletRequest extends HttpServletRequestWrapper {

	private final String componentRelativePath;
	/**
	 * Constructs a request object wrapping the given request.
	 *
	 * @param request The request to wrap
	 * @param componentName Name of component
	 * @throws IllegalArgumentException if the request is null
	 */
	public ComponentRelativeHttpServletRequest(HttpServletRequest request, String componentName) {
		super(request);
		this.componentRelativePath = "/" + componentName;
	}

	@Override
	public String getRequestURI() {
		if (super.getRequestURI().startsWith(this.componentRelativePath)) {
			return super.getRequestURI().replaceFirst(this.componentRelativePath, "");
		}
		else {
			return super.getRequestURI();
		}
	}

	@Override
	public String getServletPath() {
		if (super.getServletPath().startsWith(this.componentRelativePath)) {
			return super.getServletPath().replaceFirst(this.componentRelativePath, "");
		}
		else {
			return super.getServletPath();
		}
	}

//	@Override
//	public String getContextPath() {
//		return super.getContextPath() + this.componentRelativePath;
//	}
}
