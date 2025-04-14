package com.example.authservice.controller;

import com.example.authservice.dto.UserSignUpRequest;
import com.example.authservice.dto.UserSignUpResponse;
import com.example.authservice.service.UserService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserRepresentation> getUser(Principal principal) {
        UserRepresentation userRepresentation = userService.findById(principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body(userRepresentation);
    }

    @PostMapping
    public ResponseEntity<UserSignUpResponse> createUser(@RequestBody UserSignUpRequest userRequest) {
        UserSignUpResponse signUpResponse = userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(signUpResponse);
    }

    @PostMapping("/driver")
    public ResponseEntity<UserSignUpResponse> createDriver(@RequestBody UserSignUpRequest signUpRequest) {
        UserSignUpResponse signUpResponse = userService.createDriver(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(signUpResponse);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") String userId) {
        userService.delete(userId);
    }

}
