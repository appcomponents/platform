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
package org.appcomponents.platform.test.beans.platform;

import org.appcomponents.platform.DefaultPlatform;
import org.appcomponents.platform.annotation.PlatformConfiguration;
import org.appcomponents.platform.test.beans.component.TestComponent;
import org.appcomponents.platform.test.beans.component.TestComponent2;
import org.appcomponents.platform.test.beans.controller.EchoController;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author Martin Janys
 */
@PlatformConfiguration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Object.class)) // exclude all
public class TestDefaultPlatform extends DefaultPlatform {

	public static final String MODULE_1 = "testModule1";
	public static final String MODULE_2 = "testModule2";

	public TestDefaultPlatform() {
		addComponentConfigurations(MODULE_1, TestComponent.class);
		addComponentConfigurations(MODULE_2, TestComponent2.class);
	}

	@Bean
	public EchoController echoController() {
		return new EchoController();
	}
}

