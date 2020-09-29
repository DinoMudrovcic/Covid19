package com.dinomudrovcic.bisscovid.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dinomudrovcic.bisscovid.models.ERole;
import com.dinomudrovcic.bisscovid.models.Role;
import com.dinomudrovcic.bisscovid.models.User;
import com.dinomudrovcic.bisscovid.payload.request.LoginRequest;
import com.dinomudrovcic.bisscovid.payload.request.SignupRequest;
import com.dinomudrovcic.bisscovid.payload.response.JwtResponse;
import com.dinomudrovcic.bisscovid.payload.response.MessageResponse;
import com.dinomudrovcic.bisscovid.repositories.RoleRepository;
import com.dinomudrovcic.bisscovid.repositories.UserRepository;
import com.dinomudrovcic.bisscovid.security.jwt.JwtUtils;
import com.dinomudrovcic.bisscovid.security.services.UserDetailsImpl;

@Service
public class AuthenticationService {
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	public JwtResponse getJwtResponseFromLoginRequest(LoginRequest loginRequest) {
		Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwt = jwtUtils.generateJwtToken(auth);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles);
	}

	public ResponseEntity<?> registerUser(@Valid SignupRequest signUpRequest) {

		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}
		
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: role is not found."));
			roles.add(userRole);					
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role_admin is not found."));
					roles.add(adminRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role_user is not found."));
					roles.add(userRole);
				}
			});
		}
		
		User user = new User(signUpRequest.getUsername(), passwordEncoder.encode(signUpRequest.getPassword()), roles);
		userRepository.save(user);
		
		return ResponseEntity.ok(new MessageResponse("User registered!"));		
	}
	
	

}
