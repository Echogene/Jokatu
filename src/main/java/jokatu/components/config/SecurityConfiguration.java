package jokatu.components.config;

import jokatu.components.security.InMemoryPersonaUserDetailsService;
import jokatu.components.security.PersonaAuthenticationProvider;
import jokatu.components.security.PersonaIdentificationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author Steven Weston
 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		InMemoryPersonaUserDetailsService userDetailsService = new InMemoryPersonaUserDetailsService();

		PersonaAuthenticationProvider provider = new PersonaAuthenticationProvider(userDetailsService);

		http
			.authorizeRequests()
				.antMatchers("/css/**").permitAll()
				.antMatchers("/js/**").permitAll()
				.antMatchers("/favicon.ico").permitAll()
				.antMatchers("/").permitAll()
				.anyRequest().authenticated()
				.and()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout/persona"))
				.logoutSuccessUrl("/")
				.and()
			.authenticationProvider(provider)
			.userDetailsService(userDetailsService);

		PersonaIdentificationFilter filter = new PersonaIdentificationFilter();
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
	}
}
