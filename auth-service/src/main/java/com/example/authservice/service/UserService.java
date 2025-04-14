package com.example.authservice.service;

import com.example.authservice.dto.UserSignUpRequest;
import com.example.authservice.dto.UserSignUpResponse;
import org.keycloak.representations.idm.UserRepresentation;

public interface UserService {

    UserRepresentation findById(String userId);

    UserSignUpResponse createUser(UserSignUpRequest userSignUpRequest);

    UserSignUpResponse createDriver(UserSignUpRequest userSignUpRequest);

    void delete(String userId);

    void assignRole(String userId, String roleName);

}
