package jokatu.components.security;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Wrap the JSON response from verifier.login.persona.org.
 * @author Steven Weston
 */
public class PersonaVerificationResponse {

	public final String email;
	public final String status;
	public final String reason;

	public PersonaVerificationResponse(
			@JsonProperty("email") String email,
			@JsonProperty("status") String status,
			@JsonProperty("reason") String reason
	) {
		this.email = email;
		this.status = status;
		this.reason = reason;
	}
}
