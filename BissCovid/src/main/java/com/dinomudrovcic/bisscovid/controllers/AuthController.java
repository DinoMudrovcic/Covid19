package com.dinomudrovcic.bisscovid.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dinomudrovcic.bisscovid.payload.request.LoginRequest;
import com.dinomudrovcic.bisscovid.payload.request.SignupRequest;
import com.dinomudrovcic.bisscovid.services.AuthenticationService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationService authService;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		return ResponseEntity.ok(authService.getJwtResponseFromLoginRequest(loginRequest));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		return authService.registerUser(signUpRequest);
	}

}
