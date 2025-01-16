package com.login.service.controller;

import com.login.service.dto.UserDTO;
import com.login.service.entity.User;
import com.login.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${auth.token}")
    private String serviceAuthToken;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestHeader(value = "auth-token", required = true) String authToken, @RequestBody UserDTO userDTO) {
        // Validate if the authToken matches the serviceAuthToken
        if (!authToken.equals(serviceAuthToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Unauthorized: Invalid auth token");
        }

        // If authToken matches, create the user
        User createdUser = userService.createUser(userDTO);

        // Return success message
        return ResponseEntity.ok().body("User created successfully");
    }
}
