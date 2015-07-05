package jokatu.components.security;

/**
 * @author Steven Weston
 */
public class PersonaVerificationResponse {

	public final String email;
	public final String status;
	public final String reason;

	public PersonaVerificationResponse(String email, String status, String reason) {
		this.email = email;
		this.status = status;
		this.reason = reason;
	}
}
