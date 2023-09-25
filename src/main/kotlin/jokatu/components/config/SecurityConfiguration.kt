package jokatu.components.config

import org.slf4j.getLogger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.HttpSecurityBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import org.springframework.security.web.util.matcher.AndRequestMatcher
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.NegatedRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import java.util.*
import java.util.stream.Stream


/**
 * Sets up how users log in.
 * @author Steven Weston
 */
@EnableWebSecurity
@Configuration
class SecurityConfiguration {

	@Bean
	fun userDetailsService(): InMemoryUserDetailsManager {
		val users = Stream.of("user", "user2", "user3", "user4")
			.map { name ->
				User.withDefaultPasswordEncoder()
					.username(name)
					.password("password")
					.roles("USER")
					.build()
			}
			.toList()
		return InMemoryUserDetailsManager(users)
	}

	@Bean
	fun filterChain(http: HttpSecurity): SecurityFilterChain {

		ignoreWsRequestsWhenSaving(http)

		http
			.authorizeHttpRequests {
				it.requestMatchers("/css/**").permitAll()
					.requestMatchers("/js/**").permitAll()
					.requestMatchers("/favicon.ico").permitAll()
					.requestMatchers("/error").permitAll()
					.anyRequest().authenticated()
			}
			.formLogin {
				it.loginPage("/login")
					.permitAll()
			}
			.logout {
				it.logoutSuccessUrl("/")
			}

		log.debug("{} configured HTTP security", SecurityConfiguration::class.simpleName)
		return http.build()
	}

	@Throws(Exception::class)
	private fun ignoreWsRequestsWhenSaving(http: HttpSecurity) {
		// Please let me configure the default request matcher.  I just want to give it an extra AND term.
		val createDefaultSavedRequestMatcher = RequestCacheConfigurer::class.java.getDeclaredMethod(
				"createDefaultSavedRequestMatcher", HttpSecurityBuilder::class.java
		)
		createDefaultSavedRequestMatcher.isAccessible = true

		http.requestCache {
			val defaultRequestMatcher = createDefaultSavedRequestMatcher.invoke(it, http) as RequestMatcher
			val andRequestMatcher = AndRequestMatcher(
				defaultRequestMatcher,
				NegatedRequestMatcher(AntPathRequestMatcher("/ws"))
			)

			val requestCache = HttpSessionRequestCache()
			requestCache.setRequestMatcher(andRequestMatcher)
			it.requestCache(requestCache)
		}
	}

	companion object {
		private val log = getLogger(SecurityConfiguration::class)
	}
}
