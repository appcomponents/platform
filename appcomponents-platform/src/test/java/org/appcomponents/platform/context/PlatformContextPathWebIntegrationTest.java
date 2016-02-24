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
package org.appcomponents.platform.context;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.appcomponents.platform.PlatformIntegrationTest;
import org.appcomponents.platform.test.PlatformTest;
import org.appcomponents.platform.test.beans.platform.TestDefaultPlatformContextPath;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import static com.jayway.restassured.RestAssured.when;

/**
 * @author Martin Janys
 */
@PlatformTest(TestDefaultPlatformContextPath.class)
@ContextConfiguration(classes = TestDefaultPlatformContextPath.class)
public class PlatformContextPathWebIntegrationTest extends PlatformIntegrationTest {

	@Before
	public void setup() {
		RestAssured.port = port;
	}

	@Test
	public void moduleIntegrationTest() {
		when().get("/echo")
				.then()
				.contentType(ContentType.TEXT)
				.statusCode(HttpStatus.SC_OK)
				.content(Matchers.is("echo"));
		when().get("/")
				.then()
				.contentType(ContentType.TEXT)
				.statusCode(HttpStatus.SC_OK)
				.content(Matchers.is("index"));
		/** Module 1 */
		when().get(String.format("/%s", TestDefaultPlatformContextPath.MODULE_1))
				.then()
				.content(Matchers.is("main"))
				.contentType(ContentType.TEXT)
				.statusCode(HttpStatus.SC_OK);
		when().get(String.format("/%s/hello", TestDefaultPlatformContextPath.MODULE_1))
				.then()
				.contentType(ContentType.TEXT)
				.statusCode(HttpStatus.SC_OK)
				.content(Matchers.is("hello"));
		/** Module 2 */
		when().get(String.format("/%s", TestDefaultPlatformContextPath.MODULE_2))
				.then()
				.contentType(ContentType.TEXT)
				.statusCode(HttpStatus.SC_OK)
				.content(Matchers.is("main2"));
		when().get(String.format("/%s/hello", TestDefaultPlatformContextPath.MODULE_2))
				.then()
				.contentType(ContentType.TEXT)
				.statusCode(HttpStatus.SC_OK)
				.content(Matchers.is("hello2"));
	}

}
