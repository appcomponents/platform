package org.appcomponents.platform.test.beans.component;

import org.appcomponents.platform.annotation.AppComponent;
import org.appcomponents.platform.test.beans.controller.TestController2;
import org.springframework.context.annotation.Bean;

/**
 * @author Martin Janys
 */
@AppComponent
public class TestComponent2 {

    @Bean(name = "string")
    public String string() {
        return "string";
    }

    @Bean
    public TestController2 testController() {
        return new TestController2();
    }

}
