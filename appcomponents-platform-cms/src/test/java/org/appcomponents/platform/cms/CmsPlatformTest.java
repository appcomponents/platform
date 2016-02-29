package org.appcomponents.platform.cms;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.appcomponents.platform.cms.test.bean.platform.PlatformConfig;
import org.appcomponents.platform.test.AbstractPlatformIntegrationTest;
import org.appcomponents.platform.test.PlatformTest;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import static com.jayway.restassured.RestAssured.when;

import static org.junit.Assert.*;

/**
 * @author Martin Janys
 */
@PlatformTest(platformFactory = PlatformConfig.Platform.class)
public class CmsPlatformTest extends AbstractPlatformIntegrationTest {

	@Autowired
	private ApplicationContext applicationContext;

	@Before
	public void setup() {
		RestAssured.port = port;
	}

	@Test
	public void moduleIntegrationTest() {
		when().get("/")
				.then()
				.content(Matchers.is("todo"))
				.contentType(ContentType.TEXT)
				.statusCode(HttpStatus.SC_OK);
	}
}