package com.example.authservice.service.impl;

import static com.example.authservice.enums.PersonType.DRIVER;
import static com.example.authservice.enums.PersonType.PASSENGER;
import static com.example.authservice.utility.constants.InternationalizationExceptionVariablesConstants.DUPLICATE_USER_DATA;
import static com.example.authservice.utility.constants.InternationalizationExceptionVariablesConstants.FORBIDDEN_ATTEMPT_TO_ASSIGN_ROLE;
import static com.example.authservice.utility.constants.InternationalizationExceptionVariablesConstants.FORBIDDEN_ATTEMPT_TO_CREATE_USER;
import static com.example.authservice.utility.constants.InternationalizationExceptionVariablesConstants.INVALID_ATTEMPT_TO_ASSIGN_ROLE;
import static com.example.authservice.utility.constants.InternationalizationExceptionVariablesConstants.INVALID_ATTEMPT_TO_CREATE_USER;
import static com.example.authservice.utility.constants.InternationalizationExceptionVariablesConstants.LOGIN_EXCEPTION;
import static com.example.authservice.utility.constants.InternationalizationExceptionVariablesConstants.REFRESH_TOKEN_EXCEPTION;

import com.example.authservice.client.DriverClient;
import com.example.authservice.client.PassengerClient;
import com.example.authservice.configuration.properties.AuthServiceProperties;
import com.example.authservice.configuration.properties.KeycloakProperties;
import com.example.authservice.dto.external.ExternalEntityRequest;
import com.example.authservice.dto.person.PersonResponse;
import com.example.authservice.dto.user.AuthResponse;
import com.example.authservice.dto.user.UserLoginRequest;
import com.example.authservice.dto.user.UserPageResponse;
import com.example.authservice.dto.person.PersonRequest;
import com.example.authservice.dto.user.UserRefreshRequest;
import com.example.authservice.dto.user.UserResponse;
import com.example.authservice.enums.PersonType;
import com.example.authservice.exception.custom.DuplicateUsersException;
import com.example.authservice.exception.custom.ForbiddenAccessException;
import com.example.authservice.exception.custom.InvalidRoleAssignmentException;
import com.example.authservice.exception.custom.InvalidUserCreationException;
import com.example.authservice.exception.custom.LoginAttemptException;
import com.example.authservice.exception.custom.RefreshTokenException;
import com.example.authservice.mapper.UserMapper;
import com.example.authservice.service.UserService;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.admin.client.token.TokenManager;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RestTemplate restTemplate;
    private final Keycloak keycloak;
    private final UserMapper userMapper;
    private final DriverClient driverClient;
    private final PassengerClient passengerClient;
    private final KeycloakProperties keycloakProperties;
    private final AuthServiceProperties authServiceProperties;

    @Override
    public UserResponse findById(String userName) {
        UserRepresentation user = getUsersResource().get(userName).toRepresentation();
        return userMapper.toUserResponseFromRepresentation(user);
    }

    @Override
    public UserPageResponse findAllUsers(@Min(0) Integer offset, @Min(1) Integer limit) {
        limit = limit < authServiceProperties.maxPageLimit() ? limit : authServiceProperties.maxPageLimit();
        List<UserRepresentation> userList = getUsersResource().search(null, offset * limit, limit);
        return userMapper.toResponsePage(userList, offset, limit);
    }

    @Override
    public PersonResponse createUser(PersonRequest personRequest) {
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(personRequest.username());
        user.setFirstName(personRequest.name());
        user.setLastName("");
        user.setEmail(personRequest.email());
        user.setEmailVerified(true);

        CredentialRepresentation credentials = new CredentialRepresentation();
        credentials.setType(CredentialRepresentation.PASSWORD);
        credentials.setValue(personRequest.password());
        credentials.setTemporary(false);

        List<CredentialRepresentation> list = List.of(credentials);
        user.setCredentials(list);

        Response response = getUsersResource().create(user);
        int responseStatus = response.getStatus();

        switch (responseStatus) {
            case 201:
                List<UserRepresentation> listContainingCreatedUser = getUsersResource()
                        .searchByUsername(personRequest.username(), true);
                if (!listContainingCreatedUser.isEmpty()) {
                    UserRepresentation createdUser = listContainingCreatedUser
                            .stream()
                            .findFirst()
                            .orElse(null);
                    return userMapper.toResponseFromRequest(personRequest, UUID.fromString(createdUser.getId()));
                }
            case 403:
                throw new ForbiddenAccessException(FORBIDDEN_ATTEMPT_TO_CREATE_USER);
            case 409:
                throw new DuplicateUsersException(DUPLICATE_USER_DATA);
            default:
                throw new InvalidUserCreationException(INVALID_ATTEMPT_TO_CREATE_USER);
        }
    }

    @Override
    public PersonResponse createPerson(PersonRequest personRequest, PersonType role) {
        PersonResponse personResponse = createUser(personRequest);
        log.info("User {} with role {} created", personRequest.username(), role);
        ExternalEntityRequest externalResponse = userMapper
                .toExternalFromRequest(personRequest, personResponse.id());

        switch (role) {
            case PASSENGER -> passengerClient.createPassenger(externalResponse);
            case DRIVER -> driverClient.createDriver(externalResponse);
        }

        try {
            switch (role) {
                case PASSENGER -> assignRole(personResponse.id(), PASSENGER.getType());
                case DRIVER -> assignRole(personResponse.id(), DRIVER.getType());
            }
        } catch (ForbiddenException e) {
            throw new ForbiddenAccessException(FORBIDDEN_ATTEMPT_TO_ASSIGN_ROLE, e.getMessage());
        } catch (Exception e) {
            throw new InvalidRoleAssignmentException(INVALID_ATTEMPT_TO_ASSIGN_ROLE, e.getMessage());
        }
        return personResponse;
    }

    @Override
    public PersonResponse updateUser(UUID userId, PersonRequest updateRequest) {
        UserResource userResource = getUserResource(userId.toString());

        List<RoleRepresentation> roleRepresentationList = userResource.roles().getAll().getRealmMappings();
        if (roleRepresentationList.contains(getRoleRepresentation(PASSENGER.getType()))) {
            passengerClient.updatePassenger(userMapper.toExternalFromRequest(updateRequest, userId), userId);
        }
        if (roleRepresentationList.contains(getRoleRepresentation(DRIVER.getType()))) {
            driverClient.updateDriver(userMapper.toExternalFromRequest(updateRequest, userId), userId);
        }

        UserRepresentation user = userResource.toRepresentation();
        user.setUsername(updateRequest.username());
        user.setEmail(updateRequest.email());
        user.setFirstName(updateRequest.name());
        user.setEmailVerified(true);
        if (updateRequest.password() != null && !updateRequest.password().isEmpty()) {
            CredentialRepresentation credentials = new CredentialRepresentation();
            credentials.setType(CredentialRepresentation.PASSWORD);
            credentials.setValue(updateRequest.password());
            credentials.setTemporary(false);
            user.setCredentials(Collections.singletonList(credentials));
        }

        userResource.update(user);
        UserRepresentation updatedUser = userResource.toRepresentation();
        return PersonResponse.builder()
                .id(UUID.fromString(updatedUser.getId()))
                .username(updatedUser.getUsername())
                .name(updatedUser.getFirstName())
                .email(updatedUser.getEmail())
                .phone(updateRequest.phone())
                .gender(updateRequest.gender())
                .build();
    }

    @Override
    public void deactivateUser(UUID userId) {
        UserResource userResource = getUserResource(userId.toString());

        List<RoleRepresentation> roleRepresentationList = userResource.roles().getAll().getRealmMappings();
        if (roleRepresentationList.contains(getRoleRepresentation(PASSENGER.getType()))) {
            passengerClient.deletePassenger(userId);
        }
        if (roleRepresentationList.contains(getRoleRepresentation(DRIVER.getType()))) {
            driverClient.deleteDriver(userId);
        }

        UserRepresentation user = userResource.toRepresentation();
        user.setEnabled(false);
        userResource.update(user);
    }

    @Override
    public void assignRole(UUID userId, String roleName) {
        UserResource userResource = getUserResource(userId.toString());
        RoleRepresentation representation = getRoleRepresentation(roleName);
        userResource.roles().realmLevel().add(Collections.singletonList(representation));
    }

    private RoleRepresentation getRoleRepresentation(String roleName){
        RolesResource rolesResource = keycloak.realm(keycloakProperties.realm()).roles();
        return rolesResource.get(roleName).toRepresentation();
    }

    private UsersResource getUsersResource() {
        return keycloak.realm(keycloakProperties.realm()).users();
    }

    private UserResource getUserResource(String userId){
        UsersResource usersResource = getUsersResource();
        return usersResource.get(userId);
    }

    @Override
    public AuthResponse login(UserLoginRequest userLoginRequest) {
        try(Keycloak userKeycloak = KeycloakBuilder.builder()
                    .serverUrl(keycloakProperties.authServerUrl())
                    .realm(keycloakProperties.realm())
                    .grantType(OAuth2Constants.PASSWORD)
                    .clientId(keycloakProperties.adminClientId())
                    .clientSecret(keycloakProperties.adminClientSecret())
                    .username(userLoginRequest.username())
                    .password(userLoginRequest.password())
                    .build()) {
            TokenManager tokenManager = userKeycloak.tokenManager();
            AccessTokenResponse tokens = tokenManager.grantToken();
            return AuthResponse.builder()
                    .accessToken(tokens.getToken())
                    .refreshToken(tokens.getRefreshToken())
                    .expirationTime(tokens.getExpiresIn())
                    .build();
        } catch (Exception e) {
            throw new LoginAttemptException(LOGIN_EXCEPTION, e.getMessage());
        }
    }

    @Override
    public AuthResponse refreshTokens(UserRefreshRequest refreshRequest) {
        String tokenEndpoint = String.format("%s/realms/%s/protocol/openid-connect/token",
                keycloakProperties.authServerUrl(),
                keycloakProperties.realm());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", keycloakProperties.adminClientId());
        body.add("client_secret", keycloakProperties.adminClientSecret());
        body.add("grant_type", OAuth2Constants.REFRESH_TOKEN);
        body.add("refresh_token", refreshRequest.refreshToken());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map<String, Object>> response = restTemplate
                    .exchange(tokenEndpoint, HttpMethod.POST, request, new ParameterizedTypeReference<>() {});
            Map<String, Object> responseBody = response.getBody();

            return AuthResponse.builder()
                    .accessToken((String) responseBody.get("access_token"))
                    .refreshToken((String) responseBody.get("refresh_token"))
                    .expirationTime(((Integer) responseBody.get("expires_in")).longValue())
                    .build();
        } catch (Exception e) {
            throw new RefreshTokenException(REFRESH_TOKEN_EXCEPTION, e.getMessage());
        }
    }

}
