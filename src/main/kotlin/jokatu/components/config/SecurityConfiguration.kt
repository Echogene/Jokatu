package jokatu.components.config

import ophelia.exceptions.voidmaybe.VoidMaybe.wrap
import ophelia.exceptions.voidmaybe.VoidMaybeCollectors.merge
import org.slf4j.LoggerFactory
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.HttpSecurityBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import org.springframework.security.web.util.matcher.AndRequestMatcher
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.NegatedRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import java.util.*

/**
 * Sets up how users log in.
 * @author Steven Weston
 */
@EnableWebSecurity
class SecurityConfiguration : WebSecurityConfigurerAdapter() {

	@Throws(Exception::class)
	override fun configure(auth: AuthenticationManagerBuilder?) {
		Arrays.stream(arrayOf("user", "user2", "user3", "user4"))
				.map(wrap { name -> auth!!.inMemoryAuthentication().withUser(name).password("password").roles("USER") })
				.collect(merge()).throwOnFailure()
		log.debug("{} added user accounts", SecurityConfiguration::class.java.simpleName)
	}

	@Throws(Exception::class)
	override fun configure(http: HttpSecurity) {

		ignoreWsRequestsWhenSaving(http)

		http
			.authorizeRequests()
				.antMatchers("/css/**").permitAll()
				.antMatchers("/js/**").permitAll()
				.antMatchers("/favicon.ico").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.defaultSuccessUrl("/")
				.and()
			.logout()
				.logoutSuccessUrl("/")

		log.debug("{} configured HTTP security", SecurityConfiguration::class.java.simpleName)
	}

	@Throws(Exception::class)
	private fun ignoreWsRequestsWhenSaving(http: HttpSecurity) {
		// Please let me configure the default request matcher.  I just want to give it an extra AND term.
		val createDefaultSavedRequestMatcher = RequestCacheConfigurer::class.java.getDeclaredMethod(
				"createDefaultSavedRequestMatcher", HttpSecurityBuilder::class.java
		)
		createDefaultSavedRequestMatcher.isAccessible = true

		val configurer = http.requestCache()
		val defaultRequestMatcher = createDefaultSavedRequestMatcher.invoke(configurer, http) as RequestMatcher
		val andRequestMatcher = AndRequestMatcher(
				defaultRequestMatcher,
				NegatedRequestMatcher(AntPathRequestMatcher("/ws"))
		)

		val requestCache = HttpSessionRequestCache()
		requestCache.setRequestMatcher(andRequestMatcher)

		configurer.requestCache(requestCache)
	}

	companion object {
		private val log = LoggerFactory.getLogger(SecurityConfiguration::class.java)
	}
}
