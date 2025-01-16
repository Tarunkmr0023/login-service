package com.login.service.service;

import com.login.service.dto.JwtResponse;
import com.login.service.dto.JwtValidationDto;
import com.login.service.dto.UserDTO;
import com.login.service.entity.User;
import com.login.service.helper.JwtUtil;
import com.login.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public JwtResponse login(UserDTO userDTO) {
        Optional<User> user = userRepository.findByEmail(userDTO.getEmail());

        if (user.isPresent() && passwordEncoder.matches(userDTO.getPassword(), user.get().getPassword())) {
            String accessToken = jwtUtil.generateAccessToken(userDTO.getEmail());
            String refreshToken = jwtUtil.generateRefreshToken(userDTO.getEmail());

            LocalDateTime accessTokenExpiryTime = LocalDateTime.now().plusSeconds(jwtUtil.getExpirationTime() / 1000);
            LocalDateTime refreshTokenExpiryTime = LocalDateTime.now().plusSeconds(jwtUtil.getRefreshExpirationTime() / 1000);

            // Create a JwtResponse object with access and refresh token
            JwtResponse jwtResponse = new JwtResponse(accessToken, refreshToken, userDTO.getEmail(), accessTokenExpiryTime, refreshTokenExpiryTime);

            // Add the token to the active tokens list in JwtUtil
            jwtUtil.addToken(jwtResponse);

            return jwtResponse;
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    public JwtResponse refreshToken(String refreshToken) {
        String username = jwtUtil.extractUsername(refreshToken);

        if (username != null && !jwtUtil.isTokenExpired(refreshToken)) {
            String newAccessToken = jwtUtil.generateAccessToken(username);

            LocalDateTime accessTokenExpiryTime = LocalDateTime.now().plusSeconds(jwtUtil.getExpirationTime() / 1000);
            LocalDateTime refreshTokenExpiryTime = LocalDateTime.now().plusSeconds(jwtUtil.getRefreshExpirationTime() / 1000);

            JwtResponse jwtResponse = new JwtResponse(newAccessToken, refreshToken, username, accessTokenExpiryTime, refreshTokenExpiryTime);

            // Add the new token to the active tokens list
            jwtUtil.addToken(jwtResponse);

            return jwtResponse;
        } else {
            throw new RuntimeException("Invalid or expired refresh token");
        }
    }

    // Method to validate the JWT token
    public JwtValidationDto validateToken(String token) {
        if (jwtUtil.isTokenPresentAndValid(token)) {
            return new JwtValidationDto(true, jwtUtil.getUsernameFromToken(token));
        }
        return new JwtValidationDto(false, null);
    }

    // Method to logout (invalidate token)
    public void logout(String token) {
        // Remove the token from the active tokens list in JwtUtil
        jwtUtil.removeToken(token);
    }
}
