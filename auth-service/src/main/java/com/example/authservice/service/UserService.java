package com.example.authservice.service;

import com.example.authservice.dto.person.PersonResponse;
import com.example.authservice.dto.user.AuthResponse;
import com.example.authservice.dto.user.UserLoginRequest;
import com.example.authservice.dto.user.UserPageResponse;
import com.example.authservice.dto.person.PersonRequest;
import com.example.authservice.dto.user.UserResponse;
import com.example.authservice.enums.PersonType;
import jakarta.validation.constraints.Min;
import java.util.UUID;

public interface UserService {

    UserResponse findById(String name);

    UserPageResponse findAllUsers(@Min(0) Integer offset, @Min(1) Integer limit);

    PersonResponse createUser(PersonRequest personRequest);

    PersonResponse createPerson(PersonRequest personRequest, PersonType role);

    PersonResponse updateUser(UUID userId, PersonRequest updateRequest);

    void deactivateUser(UUID userId);

    void assignRole(UUID userId, String roleName);

    AuthResponse login(UserLoginRequest userLoginRequest);
}
