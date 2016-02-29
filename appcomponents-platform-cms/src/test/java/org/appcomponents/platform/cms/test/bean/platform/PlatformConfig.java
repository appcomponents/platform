/*
 * Copyright 2015-2016 the original author or authors.
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
package org.appcomponents.platform.cms.test.bean.platform;

import org.appcomponents.platform.PlatformBuilder;
import org.appcomponents.platform.PlatformFactory;
import org.appcomponents.platform.annotation.Platform;
import org.appcomponents.platform.cms.annotation.CmsPlatform;
import org.appcomponents.platform.theme.defaults.DefaultTheme;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

/**
 * @author Martin Janys
 */
@CmsPlatform
@Platform
@Import(DefaultTheme.class)
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Object.class)) // exclude all
public class PlatformConfig {

	public static class Platform implements PlatformFactory {

		@Override
		public SpringApplication build() {
			return new PlatformBuilder(PlatformConfig.class)
					.build();
		}
	}

	public static void main(String[] args) {
		new Platform().build().run(args);
	}

}
