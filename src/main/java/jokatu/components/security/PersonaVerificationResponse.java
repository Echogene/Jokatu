package jokatu.components.security;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {"audience":"http://localhost:8080","expires":1436464604148,"issuer":"gmail.login.persona.org","email":"steven.weston.alpha@gmail.com","status":"okay"}
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
