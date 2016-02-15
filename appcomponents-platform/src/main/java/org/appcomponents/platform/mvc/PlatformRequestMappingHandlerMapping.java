package org.appcomponents.platform.mvc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.appcomponents.platform.api.Component;
import org.appcomponents.platform.api.Platform;
import org.appcomponents.platform.api.PlatformConstants;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Martin Janys
 */
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class PlatformRequestMappingHandlerMapping implements HandlerMapping, ApplicationContextAware {

    private ApplicationContext applicationContext;
    protected final Log logger = LogFactory.getLog(getClass()); // todo logger ?

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        Platform platform = applicationContext.getBean(Platform.class);
        Component component = resolveComponent(platform, request);
        if (component != null) {
            return handle(request, component);
        }
        else {
            return null;
        }
    }

    protected Component resolveComponent(Platform platform, HttpServletRequest request) {
        String componentName = request.getParameter(PlatformConstants.PARAM_COMPONENT);
        if (PlatformConstants.NONE_COMPONENT.equals(componentName)) {
            return platform.getRootModule();
        }
        else if (!StringUtils.isEmpty(componentName)) {
            return platform.getComponent(componentName);
        }
        else {
            return platform.getDefaultComponent();
        }
    }

    protected HandlerExecutionChain handle(HttpServletRequest request, Component component) throws Exception {
        ApplicationContext applicationContext = component.getApplicationContext();
        if (this.applicationContext.equals(applicationContext)) {
            return null;
        }
        else {
            Map<String, HandlerMapping> handlerMappingMap = applicationContext.getBeansOfType(HandlerMapping.class);
            for (HandlerMapping hm : handlerMappingMap.values()) {
                if (hm.getClass().equals(getClass())) {
                    continue;
                }
                if (logger.isTraceEnabled()) {
                    logger.trace(
                            "Testing handler map [" + hm + "] in PlatformRequestMappingHandlerMapping");
                }
                HandlerExecutionChain handler = hm.getHandler(request);
                if (handler != null) {
                    return handler;
                }
            }
            return null;
        }
    }
}
