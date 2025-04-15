package com.example.authservice.dto.user;

import java.util.List;
import lombok.Builder;

@Builder
public record UserPageResponse(

        List<UserResponse> userList,
        int currentPageNumber,
        int pageLimit

) {
}
