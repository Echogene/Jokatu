package jokatu.components.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.groovy.GroovyMarkupViewResolver;

/**
 * Expose request attributes to the GroovyMarkupViewResolver.
 * todo: this is a workaround for an issue and should be done in application.properties on upgrade to Spring Boot 1.3.0
 * @author Steven Weston
 */
@Component
public class RequestAttributeExposer implements BeanPostProcessor {

	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

		if (bean instanceof GroovyMarkupViewResolver) {
			((GroovyMarkupViewResolver) bean).setExposeRequestAttributes(true);
		}
		return bean;
	}

	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}
}
