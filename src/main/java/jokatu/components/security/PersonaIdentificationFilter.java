package jokatu.components.security;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static jokatu.components.security.PersonaAuthenticationToken.unauthenticated;

/**
 * @author Steven Weston
 */
public class PersonaIdentificationFilter extends AbstractAuthenticationProcessingFilter {

	private final RestTemplate restTemplate = new RestTemplate();

	public PersonaIdentificationFilter() {

		super("/login/persona");
	}

	@Override
	public Authentication attemptAuthentication(
			HttpServletRequest request, HttpServletResponse response
	) throws AuthenticationException, IOException, ServletException {

		String assertion = request.getParameter("assertion");

		Map<String, String> params = new HashMap<>();
		params.put("assertion", assertion);
		params.put("audience", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());

		PersonaVerificationResponse verificationResponse = restTemplate.postForObject(
				"https://verifier.login.persona.org/verify",
				params,
				PersonaVerificationResponse.class
		);

		if (!"okay".equals(verificationResponse.status)) {
			throw new AuthenticationServiceException("Authentication failed. Status: " + verificationResponse.status);
		}
		return unauthenticated(verificationResponse.email);
	}
}
