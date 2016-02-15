package org.appcomponents.platform.context;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.appcomponents.platform.PlatformIntegrationTest;
import org.appcomponents.platform.api.PlatformConstants;
import org.appcomponents.platform.test.PlatformTest;
import org.appcomponents.platform.test.beans.platform.TestPlatform;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;

import static com.jayway.restassured.RestAssured.when;

/**
 * @author Martin Janys
 */
@PlatformTest(TestPlatform.class)
@ContextConfiguration(classes = TestPlatform.class)
public class PlatformWebIntegrationTest extends PlatformIntegrationTest {

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
        /** Module 1 */
        when().get("/?component={component}", Collections.singletonMap(PlatformConstants.PARAM_COMPONENT, TestPlatform.MODULE_1))
                .then()
                .contentType(ContentType.TEXT)
                .statusCode(HttpStatus.SC_OK)
                .content(Matchers.is("main"));
        when().get("/hello?component={component}", Collections.singletonMap(PlatformConstants.PARAM_COMPONENT, TestPlatform.MODULE_1))
                .then()
                .contentType(ContentType.TEXT)
                .statusCode(HttpStatus.SC_OK)
                .content(Matchers.is("hello"));
        /** Module 2 */
        when().get("/?component={component}", Collections.singletonMap(PlatformConstants.PARAM_COMPONENT, TestPlatform.MODULE_2))
                .then()
                .contentType(ContentType.TEXT)
                .statusCode(HttpStatus.SC_OK)
                .content(Matchers.is("main2"));
        when().get("/hello?component={component}", Collections.singletonMap(PlatformConstants.PARAM_COMPONENT, TestPlatform.MODULE_2))
                .then()
                .contentType(ContentType.TEXT)
                .statusCode(HttpStatus.SC_OK)
                .content(Matchers.is("hello2"));
    }

}