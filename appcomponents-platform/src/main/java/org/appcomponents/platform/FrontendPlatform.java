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

import org.appcomponents.platform.configuration.FrontendPlatformConfig;

import org.springframework.util.ObjectUtils;

/**
 * App Components Platform. Extend this class for use App Components Platform.
 *
 * @author Martin Janys
 */
public class FrontendPlatform extends DefaultPlatform {

	public FrontendPlatform(Object ... configs) {
		super(ObjectUtils.addObjectToArray(configs, FrontendPlatformConfig.class));
	}

}