package com.login.service.controller;

import com.login.service.dto.JwtResponse;
import com.login.service.dto.JwtValidationDto;
import com.login.service.dto.UserDTO;
import com.login.service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    //Login SSO API - Validate credentials and generate JWT
    @PostMapping("/sso")
    public ResponseEntity<JwtResponse> login(@RequestBody UserDTO userDTO) {
        JwtResponse response = authService.login(userDTO);
        return ResponseEntity.ok(response);
    }

    //Refresh Token API - Accept refresh token and generate new access token
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshToken(@RequestHeader String refreshToken) {
        JwtResponse response = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(response);
    }

    //Logout API - Mark token as invalid (In-memory or database-based)
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader String authToken) {
        authService.logout(authToken); // Remove the token from active tokens list
        return ResponseEntity.ok("Logged out successfully");
    }

    //Validate Token API - Validate JWT token and return the username
    @GetMapping("/validate")
    public ResponseEntity<JwtValidationDto> validateToken(@RequestHeader String authToken) {
        return ResponseEntity.ok(authService.validateToken(authToken));
    }
}
