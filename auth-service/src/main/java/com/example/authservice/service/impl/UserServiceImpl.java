package com.example.authservice.service.impl;

import static com.example.authservice.utility.constants.RoleConstants.DRIVER;

import com.example.authservice.client.DriverClient;
import com.example.authservice.configuration.properties.KeycloakProperties;
import com.example.authservice.dto.UserSignUpRequest;
import com.example.authservice.dto.UserSignUpResponse;
import com.example.authservice.dto.external.ExternalEntityResponse;
import com.example.authservice.service.UserService;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Keycloak keycloak;
    private final DriverClient driverClient;
    private final KeycloakProperties properties;

    @Override
    public UserRepresentation findById(String userId) {
        return getUsersResource().get(userId).toRepresentation();
    }

    @Override
    public UserSignUpResponse createUser(UserSignUpRequest userSignUpRequest) {

        log.info("000");
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(userSignUpRequest.username());
        user.setFirstName(userSignUpRequest.name());
        user.setEmail(userSignUpRequest.email());
        user.setEmailVerified(true);

        log.info("111");
        CredentialRepresentation credentials = new CredentialRepresentation();
        credentials.setType(CredentialRepresentation.PASSWORD);
        credentials.setValue(userSignUpRequest.password());
        credentials.setTemporary(false);

        log.info("222");
        List<CredentialRepresentation> list = List.of(credentials);
        user.setCredentials(list);

        log.info("333");
        Response response = getUsersResource().create(user);
        int responseStatus = response.getStatus();

        switch (responseStatus) {
            case 201:
                log.info("444");
                List<UserRepresentation> listContainingCreatedUser = getUsersResource()
                        .searchByUsername(userSignUpRequest.username(), true);
                if (!listContainingCreatedUser.isEmpty()) {
                    UserRepresentation createdUser = listContainingCreatedUser
                            .stream()
                            .findFirst()
                            .orElse(null);
                    return UserSignUpResponse
                            .builder()
                            .id(UUID.fromString(createdUser.getId()))
                            .username(userSignUpRequest.username())
                            .name(userSignUpRequest.name())
                            .email(userSignUpRequest.email())
                            .phone(userSignUpRequest.phone())
                            .gender(userSignUpRequest.gender())
                            .build();
                }
            case 409:

                log.info("555");
                throw new RuntimeException();
//                throw new RepeatedUserData(USER_REPEATED_DATA);
            default:
                log.info("{} + {}", responseStatus, response.getEntity());
                throw new RuntimeException();
//                throw new UserCreationException(USER_CREATION_ERROR);
        }
    }

    @Override
    public UserSignUpResponse createDriver(UserSignUpRequest userSignUpRequest) {
        UserSignUpResponse userResponse = createUser(userSignUpRequest);
        ExternalEntityResponse driverResponse = ExternalEntityResponse.builder()
                .id(userResponse.id())
                .name(userSignUpRequest.name())
                .email(userSignUpRequest.email())
                .phone(userSignUpRequest.phone())
                .gender(userSignUpRequest.gender())
                .build();
        //driverClient.createDriver(driverResponse);

        try {
            assignRole(userResponse.id().toString(), DRIVER);
        } catch (ForbiddenException exception) {
//            log.error("UserService.Client don't have enough rights to assign role: {}", exception.getMessage());
//            deleteUserById(UUID.fromString(userId));
//            throw new ClientRightException(USER_CREATION_ERROR);
            throw exception;
        }
        return userResponse;
    }

    @Override
    public void delete(String userId) {
        getUsersResource().delete(userId);
    }

    @Override
    public void assignRole(String userId, String roleName) {
        UserResource userResource = getUserResource(userId);
        RoleRepresentation representation = getRoleRepresentation(roleName);
        userResource.roles().realmLevel().add(Collections.singletonList(representation));
    }

    private RoleRepresentation getRoleRepresentation(String roleName){
        RolesResource rolesResource = keycloak.realm(properties.realm()).roles();
        return rolesResource.get(roleName).toRepresentation();
    }

    private UsersResource getUsersResource() {
        return keycloak.realm(properties.realm()).users();
    }

    public UserResource getUserResource(String userId){
        UsersResource usersResource = getUsersResource();
        return usersResource.get(userId);
    }

}
