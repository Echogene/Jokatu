package jokatu.components.config;

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

/**
 * @author Steven Weston
 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
				.withUser("user").password("password").roles("USER")
				.and()
				.withUser("user2").password("password").roles("USER");
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
