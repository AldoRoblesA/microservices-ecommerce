package com.paksoft.app.service.oauth.security.event;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.paksoft.app.commons.user.models.entity.User;
import com.paksoft.app.service.oauth.services.UserService;

import brave.Tracer;
import feign.FeignException;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

	@Autowired
	private Tracer tracer;

	// private Logger log =
	// LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);

	@Autowired
	private UserService userService;

	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		UserDetails userDet = (UserDetails) authentication.getPrincipal();
		String mensaje = "Success Login: " + userDet.getUsername();
		// log.info(mensaje);

		User user = userService.findByUsername(authentication.getName());

		if (user.getAttempts() != null && user.getAttempts() > 0) {
			user.setAttempts(0);
			userService.update(user, user.getId());
		}
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {

		String mensaje = "Error en el Login: " + exception.getMessage();
		// log.info(mensaje);
		try {
			StringBuilder errors = new StringBuilder();
			errors.append(mensaje);

			User user = userService.findByUsername(authentication.getName());

			if (user.getAttempts() == null) {
				user.setAttempts(0);
			}
			// log.info("Intentos actual es de: " + user.getAttempts());

			user.setAttempts(user.getAttempts() + 1);

			// log.info("Intentos después es de: " + user.getAttempts());
			errors.append(" - Intentos del login: " + user.getAttempts());

			if (user.getAttempts() >= 3) {
				String errorMaxIntentos = String.format("El usuario %s des-habilitado por máximos intentos.",
						user.getUsername());
				// log.error(errorMaxIntentos);
				errors.append(" - " + errorMaxIntentos);
				user.setEnabled(false);
			}
			userService.update(user, user.getId());

			tracer.currentSpan().tag("error.mensaje", errors.toString());
		} catch (FeignException e) {
			// log.error(String.format("El error es %s", e.getMessage()));
			// log.error(String.format("El usuario %s no existe en el sistema",
			// authentication.getName()));
		}

	}

}
