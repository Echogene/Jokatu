package jokatu.components.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.user.DefaultUserDestinationResolver;
import org.springframework.messaging.simp.user.UserDestinationResolver;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;

@Configuration
public class UserDestinationConfiguration {

	private static final Logger log = LoggerFactory.getLogger(UserDestinationConfiguration.class);

	private final UserDestinationResolver userDestinationResolver;

	public UserDestinationConfiguration(UserDestinationResolver userDestinationResolver) {
		this.userDestinationResolver = userDestinationResolver;
	}

	/**
	 * <p>In Spring 4.3, someone decided that the path matcher I use ({@code new AntPathMatcher(".")} see
	 * {@link WebSocketConfiguration#configureMessageBroker(MessageBrokerRegistry)} ) can never match destinations that
	 * start with a slash. It did work fine for all my message destinations (which start with a slash) in 4.2; there is
	 * no problem with that path matcher matching the destinations I use.</p>
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

		log.debug("{} initialised", UserDestinationConfiguration.class.getSimpleName());
	}
}
