package org.appcomponents.platform.theme;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

/**
 * @author Martin Janys
 */
public interface Theme {

	String name();

	default String index() {
		return "index";
	}

	void addThemeResourceHandlers(ResourceHandlerRegistry registry);
}
