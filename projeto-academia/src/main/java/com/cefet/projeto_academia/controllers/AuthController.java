package com.cefet.projeto_academia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cefet.projeto_academia.dto.JwtAuthenticationDTO;
import com.cefet.projeto_academia.dto.LoginDTO;
import com.cefet.projeto_academia.security.JwtTokenProvider;



@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider tokenProvider;
    
    @PostMapping
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		loginDTO.getLogin(),
                		loginDTO.getSenha()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationDTO(jwt));
    }      

}
