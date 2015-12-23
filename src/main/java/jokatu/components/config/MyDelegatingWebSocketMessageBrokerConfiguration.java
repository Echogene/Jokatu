package jokatu.components.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.annotation.support.SimpAnnotationMethodMessageHandler;
import org.springframework.messaging.simp.config.AbstractMessageBrokerConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration;

/**
 * This is a massive hack because on one (and not the other) of my machines, when
 * {@link AbstractMessageBrokerConfiguration#simpValidator simpValidator} is called when
 * {@link AbstractMessageBrokerConfiguration#simpAnnotationMethodMessageHandler getting the bean
 * simpAnnotationMethodMessageHandler}, the application context is null because it has not been set yet (it apparently
 * is set by this point on my other machine, wtf?).  This bean overrides that one and makes sure that the application
 * context is set upon construction (<em>like it should be</em>).
 */
@Component
public class MyDelegatingWebSocketMessageBrokerConfiguration extends DelegatingWebSocketMessageBrokerConfiguration {

	/**
	 * SET THE APPLICATION CONTEXT DAMMIT
	 */
	@Autowired
	public MyDelegatingWebSocketMessageBrokerConfiguration(ApplicationContext applicationContext) {
		setApplicationContext(applicationContext);
	}

	/**
	 * This is part of the massive hack because we need to get the {@link SimpAnnotationMethodMessageHandler} bean from
	 * the instance of {@link AbstractMessageBrokerConfiguration} that definitely has the application context (the
	 * purpose of this class) when we try to get the SimpAnnotationMethodMessageHandler
	 * bean.
	 */
	@Bean
	@Override
	public SimpAnnotationMethodMessageHandler simpAnnotationMethodMessageHandler() {
		return super.simpAnnotationMethodMessageHandler();
	}
}
