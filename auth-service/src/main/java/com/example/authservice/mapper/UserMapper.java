package com.example.authservice.mapper;

import com.example.authservice.dto.person.PersonResponse;
import com.example.authservice.dto.person.PersonRequest;
import com.example.authservice.dto.user.UserPageResponse;
import com.example.authservice.dto.user.UserResponse;
import com.example.authservice.dto.external.ExternalEntityRequest;
import java.util.List;
import java.util.UUID;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    UserResponse toUserResponseFromRepresentation(UserRepresentation user);

    PersonResponse toResponseFromRequest(PersonRequest personRequest, UUID id);

    ExternalEntityRequest toExternalFromRequest(PersonRequest personRequest, UUID id);

    @Mapping(target = "userList", source = "userList", qualifiedByName = "userRepresentationListToResponseList")
    @Mapping(target = "currentPageNumber", source = "offset")
    @Mapping(target = "pageLimit", source = "limit")
    UserPageResponse toResponsePage(List<UserRepresentation> userList, int offset, int limit);

    @Named("userRepresentationListToResponseList")
    List<UserResponse> userRepresentationListToResponseList(List<UserRepresentation> userList);

}
