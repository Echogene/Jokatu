package jokatu.components.security;

import jokatu.game.user.User;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Steven Weston
 */
public class InMemoryPersonaUserDetailsService
		implements UserDetailsService, AuthenticationUserDetailsService<PersonaAuthenticationToken> {

	private final Map<String, UserDetails> registeredUsers = new HashMap<>();

	public InMemoryPersonaUserDetailsService() {
		registeredUsers.put("admin", new User("admin"));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (registeredUsers.containsKey(username)) {
			return registeredUsers.get(username);
		}
		throw new UsernameNotFoundException(username);
	}

	@Override
	public UserDetails loadUserDetails(PersonaAuthenticationToken token) throws UsernameNotFoundException {

		String id = token.getId();

		if (registeredUsers.containsKey(id)) {
			return registeredUsers.get(id);
		}
		throw new UsernameNotFoundException(id);
	}
}
