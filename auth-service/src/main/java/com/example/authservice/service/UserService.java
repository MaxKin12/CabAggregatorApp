package com.example.authservice.service;

import com.example.authservice.dto.person.PersonResponse;
import com.example.authservice.dto.user.AuthResponse;
import com.example.authservice.dto.user.UserLoginRequest;
import com.example.authservice.dto.user.UserPageResponse;
import com.example.authservice.dto.person.PersonRequest;
import com.example.authservice.dto.user.UserResponse;
import jakarta.validation.constraints.Min;

public interface UserService {

    UserResponse findById(String userId);

    UserPageResponse findAllUsers(@Min(0) Integer offset, @Min(1) Integer limit);

    PersonResponse createUser(PersonRequest personRequest);

    PersonResponse createPerson(PersonRequest personRequest, String role);

    PersonResponse updateUser(String userId, PersonRequest updateRequest);

    void deactivateUser(String userId);

    void assignRole(String userId, String roleName);

    AuthResponse login(UserLoginRequest userLoginRequest);
}
