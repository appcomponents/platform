package org.appcomponents.platform.api;

import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContext;

/**
 * @author Martin Janys
 */
public interface Component {

    String getComponentName();

    void refresh();

    void refresh(ServletContext servletContext);

    ApplicationContext getApplicationContext();

}
