package com.example.authservice.controller;

import static com.example.authservice.enums.PersonType.DRIVER;
import static com.example.authservice.enums.PersonType.PASSENGER;

import com.example.authservice.dto.person.PersonResponse;
import com.example.authservice.dto.user.AuthResponse;
import com.example.authservice.dto.user.UserLoginRequest;
import com.example.authservice.dto.user.UserPageResponse;
import com.example.authservice.dto.person.PersonRequest;
import com.example.authservice.dto.user.UserRefreshRequest;
import com.example.authservice.dto.user.UserResponse;
import com.example.authservice.service.UserService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    @RateLimiter(name = "loginRestRequest")
    public ResponseEntity<AuthResponse> login(@RequestBody UserLoginRequest loginRequest) {
        AuthResponse authResponse = userService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(authResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody UserRefreshRequest refreshRequest) {
        AuthResponse authResponse = userService.refreshTokens(refreshRequest);
        return ResponseEntity.status(HttpStatus.OK).body(authResponse);
    }

    @GetMapping
    public ResponseEntity<UserResponse> getUser(@AuthenticationPrincipal Jwt jwt) {
        if (jwt == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        UserResponse userResponse = userService.findById(jwt.getSubject());
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<UserPageResponse> getAllUsers(
            @RequestParam(name = "offset", defaultValue = "0") Integer offset,
            @RequestParam(name = "limit", defaultValue = "10") Integer limit
    ) {
        UserPageResponse userPageResponse = userService.findAllUsers(offset, limit);
        return ResponseEntity.status(HttpStatus.OK).body(userPageResponse);
    }

    @PostMapping
    public ResponseEntity<PersonResponse> createUser(@RequestBody PersonRequest personRequest) {
        PersonResponse personResponse = userService.createUser(personRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(personResponse);
    }

    @PostMapping("/passenger")
    public ResponseEntity<PersonResponse> createPassenger(@RequestBody PersonRequest signUpRequest) {
        PersonResponse personResponse = userService.createPerson(signUpRequest, PASSENGER);
        return ResponseEntity.status(HttpStatus.CREATED).body(personResponse);
    }

    @PostMapping("/driver")
    public ResponseEntity<PersonResponse> createDriver(@RequestBody PersonRequest signUpRequest) {
        PersonResponse personResponse = userService.createPerson(signUpRequest, DRIVER);
        return ResponseEntity.status(HttpStatus.CREATED).body(personResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PersonResponse> updateUser(@RequestBody PersonRequest personRequest,
                                                   @PathVariable("id") UUID userId) {
        PersonResponse personResponse = userService.updateUser(userId, personRequest);
        return ResponseEntity.status(HttpStatus.OK).body(personResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") UUID userId) {
        userService.deactivateUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
