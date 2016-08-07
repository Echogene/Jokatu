package jokatu.components.config;

import ophelia.exceptions.voidmaybe.VoidMaybe;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ophelia.exceptions.voidmaybe.VoidMaybe.mergeFailures;
import static ophelia.exceptions.voidmaybe.VoidMaybe.wrap;

/**
 * Sets up how users log in.
 * @author Steven Weston
 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		List<VoidMaybe> results = Arrays.stream(new String[]{"user", "user2", "user3", "user4"})
				.map(wrap(name -> auth.inMemoryAuthentication().withUser(name).password("password").roles("USER")))
				.collect(Collectors.toList());

		mergeFailures(results).throwOnFailure();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		ignoreWsRequestsWhenSaving(http);

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
				.logoutSuccessUrl("/");
	}

	private void ignoreWsRequestsWhenSaving(HttpSecurity http) throws Exception {
		// Please let me configure the default request matcher.  I just want to give it an extra AND term.
		Method createDefaultSavedRequestMatcher = RequestCacheConfigurer.class.getDeclaredMethod(
				"createDefaultSavedRequestMatcher", HttpSecurityBuilder.class
		);
		createDefaultSavedRequestMatcher.setAccessible(true);

		RequestCacheConfigurer<HttpSecurity> configurer = http.requestCache();
		RequestMatcher defaultRequestMatcher = (RequestMatcher) createDefaultSavedRequestMatcher.invoke(configurer, http);
		AndRequestMatcher andRequestMatcher = new AndRequestMatcher(
				defaultRequestMatcher,
				new NegatedRequestMatcher(new AntPathRequestMatcher("/ws"))
		);

		HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
		requestCache.setRequestMatcher(andRequestMatcher);

		configurer.requestCache(requestCache);
	}
}
