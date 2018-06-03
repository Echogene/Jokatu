package jokatu.components.config

import org.slf4j.getLogger
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.user.DefaultUserDestinationResolver
import org.springframework.messaging.simp.user.UserDestinationResolver
import javax.annotation.PostConstruct

@Configuration
class UserDestinationConfiguration(private val userDestinationResolver: UserDestinationResolver) {

	/**
	 * In Spring 4.3, someone decided that the path matcher I use (`new AntPathMatcher(".")` see
	 * [WebSocketConfiguration.configureMessageBroker] ) can never match destinations that
	 * start with a slash. It did work fine for all my message destinations (which start with a slash) in 4.2; there is
	 * no problem with that path matcher matching the destinations I use.
	 *
	 *
	 * Hack the [DefaultUserDestinationResolver] so that it keeps the leading slashes.  Thanks.
	 *
	 * <pre>
	 * todo: Perhaps the correct fix for this is to be consistent with slashes and fullstops and use a different path
	 * todo: matcher, but this is not as trivial as just changing the `"."` to `"/"` above and changing all
	 * todo: the destinations in the same way, I tried that.
	</pre> *
	 */
	@PostConstruct
	@Throws(NoSuchFieldException::class, IllegalAccessException::class)
	fun stopUserDestinationResolverRemovingLeadingSlashes() {
		val keepLeadingSlash = userDestinationResolver.javaClass.getDeclaredField("keepLeadingSlash")
		keepLeadingSlash.isAccessible = true
		keepLeadingSlash.set(userDestinationResolver, true)

		log.debug("{} initialised", UserDestinationConfiguration::class.simpleName)
	}

	companion object {
		private val log = getLogger(UserDestinationConfiguration::class)
	}
}
