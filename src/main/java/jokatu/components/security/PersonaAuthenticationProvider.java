package jokatu.components.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;

import static jokatu.components.security.PersonaAuthenticationToken.authenticated;

/**
 * @author Steven Weston
 */
public class PersonaAuthenticationProvider implements AuthenticationProvider {

	private final AuthenticationUserDetailsService<PersonaAuthenticationToken> userDetailsService;

	public PersonaAuthenticationProvider(
			AuthenticationUserDetailsService<PersonaAuthenticationToken> userDetailsService
	) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		if (!(authentication instanceof PersonaAuthenticationToken)) {
			return null;
		}

		PersonaAuthenticationToken token = (PersonaAuthenticationToken) authentication;

		UserDetails userDetails = userDetailsService.loadUserDetails(token);

		return authenticated(token.getId(), userDetails);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return PersonaAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
