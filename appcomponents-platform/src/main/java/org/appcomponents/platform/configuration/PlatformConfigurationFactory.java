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
package org.appcomponents.platform.configuration;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.appcomponents.platform.PlatformFeature;

/**
 * Factory transforms {@link PlatformFeature}s to configuration classes.
 * @author Martin Janys
 */
public class PlatformConfigurationFactory {

	protected PlatformConfigurationFactory() {
	}

	public static Set<Class<?>> platformConfigurations(PlatformFeature... platformFeatures) {
		Set<Class<?>> configs = new LinkedHashSet<>();
		for (PlatformFeature platformFeature : platformFeatures) {
			configs.addAll(platformConfiguration(platformFeature));
		}
		return configs.size() > 0 ? configs : platformConfiguration(PlatformFeature.BASE);
	}

	public static Set<Class<?>> platformConfiguration(PlatformFeature platformFeature) {
		switch (platformFeature) {
			case FRONTEND:
				return configs(PlatformConfig.class, FrontendPlatformConfig.class);
			case BASE:
			default:
				return configs(PlatformConfig.class);
		}
	}

	private static Set<Class<?>> configs(Class<?> ... classes) {
		return new LinkedHashSet<>(Arrays.asList(classes));
	}

}
