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
package org.appcomponents.platform;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.appcomponents.platform.configuration.PlatformConfig;
import org.appcomponents.platform.impl.EmbeddedWebApplication;
import org.appcomponents.platform.impl.RootPlatformComponent;
import org.appcomponents.platform.impl.WebApplication;

import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * Platform builder.
 * @author Martin Janys
 */
public class PlatformBuilder {

	private SpringApplicationBuilder springApplicationBuilder;

	private final Map<String, Object> properties = new LinkedHashMap<>();
	private final Set<Object> sources = new LinkedHashSet<>();
	private final Set<String> profiles = new LinkedHashSet<>();

	private final RootPlatformComponent platform = new RootPlatformComponent();

	private ConfigurableEnvironment environment;

	public PlatformBuilder(Object ... sources) {
		this.springApplicationBuilder = new SpringApplicationBuilder(sources) {
			@Override
			protected SpringApplication createSpringApplication(Object... sources) {
				return new EmbeddedWebApplication(PlatformBuilder.this.platform, sources);
			}
		};
		this.springApplicationBuilder.web(true);
	}

	public ConfigurableApplicationContext context() {
		return this.springApplicationBuilder.context();
	}

	public SpringApplicationBuilder sibling(Object... sources) {
		return this.springApplicationBuilder.sibling(sources);
	}

	public SpringApplicationBuilder registerShutdownHook(boolean registerShutdownHook) {
		return this.springApplicationBuilder.registerShutdownHook(registerShutdownHook);
	}

	public SpringApplicationBuilder listeners(ApplicationListener<?>... listeners) {
		return this.springApplicationBuilder.listeners(listeners);
	}

	public SpringApplicationBuilder sources(Object... sources) {
		this.sources.addAll(new LinkedHashSet<>(Arrays.asList(sources)));
		return this.springApplicationBuilder.sources(sources);
	}

	public SpringApplicationBuilder logStartupInfo(boolean logStartupInfo) {
		return this.springApplicationBuilder.logStartupInfo(logStartupInfo);
	}

	public SpringApplicationBuilder sibling(Object[] sources, String... args) {
		return this.springApplicationBuilder.sibling(sources, args);
	}

	public SpringApplicationBuilder beanNameGenerator(BeanNameGenerator beanNameGenerator) {
		return this.springApplicationBuilder.beanNameGenerator(beanNameGenerator);
	}

	public SpringApplicationBuilder main(Class<?> mainApplicationClass) {
		return this.springApplicationBuilder.main(mainApplicationClass);
	}

	public WebApplication application() {
		return (WebApplication) this.springApplicationBuilder.application();
	}

	public ConfigurableApplicationContext run(String... args) {
		return this.springApplicationBuilder.run(args);
	}

	public WebApplication build() {
		return (WebApplication) this.springApplicationBuilder.build();
	}

	public PlatformBuilder profiles(String... profiles) {
		this.profiles.addAll(Arrays.asList(profiles));
		this.springApplicationBuilder.profiles(profiles);
		return this;
	}

	public PlatformBuilder parent(ConfigurableApplicationContext parent) {
		this.springApplicationBuilder.parent(parent);
		return this;
	}

	public PlatformBuilder parent(Object... sources) {
		this.springApplicationBuilder.parent(sources);
		return this;
	}

	public PlatformBuilder headless(boolean headless) {
		this.springApplicationBuilder.headless(headless);
		return this;
	}

	public PlatformBuilder properties(Map<String, Object> defaults) {
		this.properties.putAll(defaults);
		this.springApplicationBuilder.properties(defaults);
		return this;
	}

	public PlatformBuilder resourceLoader(ResourceLoader resourceLoader) {
		this.springApplicationBuilder.resourceLoader(resourceLoader);
		return this;
	}

	public PlatformBuilder banner(Banner banner) {
		this.springApplicationBuilder.banner(banner);
		return this;
	}

	public PlatformBuilder sources(Class<?>... sources) {
		this.sources.addAll(new LinkedHashSet<Object>(Arrays.asList(sources)));
		this.springApplicationBuilder.sources(sources);
		return this;
	}

	public PlatformBuilder environment(ConfigurableEnvironment environment) {
		this.environment = environment;
		this.springApplicationBuilder.environment(environment);
		return this;
	}

	public PlatformBuilder initializers(ApplicationContextInitializer<?>... initializers) {
		this.springApplicationBuilder.initializers(initializers);
		return this;
	}

	public PlatformBuilder properties(Properties defaultProperties) {
		this.springApplicationBuilder.properties(defaultProperties);
		return this;
	}

	public PlatformBuilder addCommandLineProperties(boolean addCommandLineProperties) {
		this.springApplicationBuilder.addCommandLineProperties(addCommandLineProperties);
		return this;
	}

	public PlatformBuilder bannerMode(Banner.Mode bannerMode) {
		this.springApplicationBuilder.bannerMode(bannerMode);
		return this;
	}

	public PlatformBuilder child(String childName, Class<?> config) {
		AnnotationConfigWebApplicationContext child = child(config);
		this.platform.addChild(childName, child);
		return this;
	}

	private AnnotationConfigWebApplicationContext child(Class<?> ... config) {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(config);
		return context;
	}

	public PlatformBuilder defaultComponent(String moduleName) {
		this.platform.setDefaultComponentName(moduleName);
		return this;
	}
}
