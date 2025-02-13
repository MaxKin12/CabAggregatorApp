package com.example.driverservice.mapper;

import com.example.driverservice.dto.EntityResponse;
import com.example.driverservice.dto.common.PageResponse;
import com.example.driverservice.model.DriverServiceEntity;
import java.util.List;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

public interface PageMapper<T extends EntityResponse, G extends DriverServiceEntity> {

    @Mapping(target = "list", source = "page", qualifiedByName = "pageToResponseList")
    @Mapping(target = "currentPageNumber", source = "offset")
    @Mapping(target = "pageLimit", source = "limit")
    @Mapping(target = "totalPages", source = "page.totalPages")
    @Mapping(target = "totalElements", source = "page.totalElements")
    PageResponse<T> toResponsePage(Page<G> page, int offset, int limit);

    @Named("pageToResponseList")
    List<T> pageToResponseList(Page<G> page);

}
