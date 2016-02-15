package org.appcomponents.platform.test.beans.component;

import org.appcomponents.platform.annotation.AppComponent;
import org.appcomponents.platform.test.beans.controller.TestController;
import org.springframework.context.annotation.Bean;

/**
 * @author Martin Janys
 */
@AppComponent
public class TestComponent {

    @Bean(name = "string")
    public String string() {
        return "string";
    }

    @Bean
    public TestController testController() {
        return new TestController();
    }

}
