package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.domain.User;
import com.myriadquest.kreiscms.repository.UserRepository;
import com.myriadquest.kreiscms.security.jwt.JWTFilter;
import com.myriadquest.kreiscms.security.jwt.TokenProvider;
import com.myriadquest.kreiscms.web.rest.vm.LoginVM;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import javax.validation.Valid;


/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

	private final TokenProvider tokenProvider;

	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	@Autowired
	private UserRepository userRepository;

	public UserJWTController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
		this.tokenProvider = tokenProvider;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
	}

	@PostMapping("/authenticate")
	public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {
System.out.println("************************************************************************login :"+loginVM.getUsername());
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginVM.getUsername(), loginVM.getPassword());

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
		System.out.println("********************************************login vm :"+loginVM.getUsername());
		Optional<User> user = userRepository.findOneByLogin(loginVM.getUsername());
		System.out.println("===============================================user:"+user.get());
//		String jwt = tokenProvider.createToken(authentication, rememberMe);
		String jwt = tokenProvider.createToken(user.get(), rememberMe);
		System.err.println("================jwt :"+jwt);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
		return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
	}

	/**
	 * Object to return as body in JWT Authentication.
	 */
	static class JWTToken {

		private String idToken;

		JWTToken(String idToken) {
			this.idToken = idToken;
		}

		@JsonProperty("id_token")
		String getIdToken() {
			return idToken;
		}

		void setIdToken(String idToken) {
			this.idToken = idToken;
		}
	}
}
