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
package org.appcomponents.platform.test;

import org.appcomponents.platform.test.loader.PlatformContextLoader;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Abstract App Components Platform test.
 *
 * @author Martin Janys
 */
@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest("server.port:0")
@WebAppConfiguration
@ContextConfiguration(loader = PlatformContextLoader.class, classes = AutoConfiguration.class)
public abstract class AbstractPlatformIntegrationTest {

	@Value("${local.server.port}")
	protected int port;

}
