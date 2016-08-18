package jokatu.components.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.user.DefaultUserDestinationResolver;
import org.springframework.messaging.simp.user.UserDestinationResolver;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;

/**
 * Set up STOMP web sockets.
 */
@Configuration
@EnableWebSocket
// todo: secure web sockets
@EnableWebSocketMessageBroker
public class WebSocketConfiguration
		extends AbstractSessionWebSocketMessageBrokerConfigurer {

	private final UserDestinationResolver userDestinationResolver;

	public WebSocketConfiguration(UserDestinationResolver userDestinationResolver) {
		this.userDestinationResolver = userDestinationResolver;
	}

	@Override
	protected void configureStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws");
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableStompBrokerRelay("/topic/", "/queue/");
		registry.setPathMatcher(new AntPathMatcher("."));
	}

	/**
	 * <p>In Spring 4.3, someone decided that the path matcher I use ({@code new AntPathMatcher(".")} see
	 * {@link #configureMessageBroker(MessageBrokerRegistry)}) can never match destinations that start with a slash.
	 * It did work fine for all my message destinations (which start with a slash) in 4.2; there is no problem with
	 * that path matcher matching the destinations I use.</p>
	 *
	 * <p>Hack the {@link DefaultUserDestinationResolver} so that it keeps the leading slashes.  Thanks.</p>
	 *
	 * <pre>
	 * todo: Perhaps the correct fix for this is to be consistent with slashes and fullstops and use a different path
	 * todo: matcher, but this is not as trivial as just changing the {@code "."} to {@code "/"} above and changing all
	 * todo: the destinations in the same way, I tried that.
	 * </pre>
	 */
	@PostConstruct
	public void stopUserDestinationResolverRemovingLeadingSlashes() throws NoSuchFieldException, IllegalAccessException {
		Field keepLeadingSlash = userDestinationResolver.getClass().getDeclaredField("keepLeadingSlash");
		keepLeadingSlash.setAccessible(true);
		keepLeadingSlash.set(userDestinationResolver, true);
	}

	@Bean
	public MapSessionRepository sessionRepository() {
		return new MapSessionRepository();
	}

//	@Override
//	protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
//		messages.anyMessage().authenticated();
//	}
}
