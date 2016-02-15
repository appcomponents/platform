package org.appcomponents.platform.api;

/**
 * @author Martin Janys
 */
public interface Platform {

    Component getComponent(String componentName);

    Component getDefaultComponent();

    Component getRootModule();

}
