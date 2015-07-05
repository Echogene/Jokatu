package jokatu.game.user;

import java.util.Collections;

/**
 * @author Steven Weston
 */
public class User extends org.springframework.security.core.userdetails.User {

	public User(String username) {
		super(username, "unused", Collections.emptySet());
	}
}
