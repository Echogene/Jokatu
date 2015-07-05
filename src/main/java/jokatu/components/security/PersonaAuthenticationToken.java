package jokatu.components.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Steven Weston
 */
public class PersonaAuthenticationToken extends AbstractAuthenticationToken {

	private final String id;

	private final Object principal;
	PersonaAuthenticationToken(
			String id, Object principal,
			Collection<? extends GrantedAuthority> authorities
	) {

		super(authorities);
		this.id = id;
		this.principal = principal;
	}

	public String getId() {
		return id;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@NotNull
	public static PersonaAuthenticationToken unauthenticated(String id) {

		PersonaAuthenticationToken token = new PersonaAuthenticationToken(
				id, id,
				Collections.emptySet()
		);
		token.setAuthenticated(false);
		return token;
	}

	@NotNull
	public static PersonaAuthenticationToken authenticated(String id, Object principal) {

		PersonaAuthenticationToken token = new PersonaAuthenticationToken(
				id, principal,
				Collections.emptySet()
		);
		token.setAuthenticated(true);
		return token;
	}
}
