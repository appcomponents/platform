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
package org.appcomponents.platform.test.loader;

import java.util.Map;

import org.appcomponents.platform.test.PlatformTest;

import org.springframework.beans.BeanUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.test.context.MergedContextConfiguration;
import org.springframework.util.Assert;

/**
 * Test context loader for App Components Platform.
 * @author Martin Janys
 */
public class PlatformContextLoader extends SpringApplicationContextLoader {

	private MergedContextConfiguration config;


	@Override
	public ApplicationContext loadContext(final MergedContextConfiguration config) throws Exception {
		this.config = config;
		return super.loadContext(config);
	}

	@Override
	protected SpringApplication getSpringApplication() {
		StandardAnnotationMetadata annotationMetadata = new StandardAnnotationMetadata(this.config.getTestClass());
		Map<String, Object> annotationAttributes = annotationMetadata.getAnnotationAttributes(PlatformTest.class.getName());
		Assert.notNull(annotationAttributes, "No PlatformTest is specified");

		@SuppressWarnings("unchecked")
		Class<SpringApplication> platformClass = (Class<SpringApplication>) annotationAttributes.get("value");
		Assert.isAssignable(SpringApplication.class, platformClass, "Platform must be instance of SpringApplication");
		return BeanUtils.instantiate(platformClass);
	}
}
