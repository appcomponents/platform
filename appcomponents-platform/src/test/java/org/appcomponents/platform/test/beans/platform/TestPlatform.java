package org.appcomponents.platform.test.beans.platform;

import org.appcomponents.platform.PlatformBase;
import org.appcomponents.platform.annotation.AppPlatform;
import org.appcomponents.platform.test.beans.component.TestComponent;
import org.appcomponents.platform.test.beans.component.TestComponent2;
import org.appcomponents.platform.test.beans.controller.EchoController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author Martin Janys
 */
@AppPlatform
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Object.class)) // exclude all
public class TestPlatform extends PlatformBase {

    public static final String MODULE_1 = "testModule1";
    public static final String MODULE_2 = "testModule2";

    public TestPlatform() {
        addComponentConfigurations(MODULE_1, TestComponent.class);
        addComponentConfigurations(MODULE_2, TestComponent2.class);
    }

    @Bean
    public EchoController echoController() {
        return new EchoController();
    }
}

