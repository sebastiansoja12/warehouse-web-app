package com.warehouse.routetracker.infrastructure.adapter.secondary.mapper;

import com.warehouse.routetracker.domain.model.Route;
import com.warehouse.routetracker.domain.model.RouteRequest;
import com.warehouse.routetracker.domain.model.RouteResponse;
import com.warehouse.routetracker.infrastructure.adapter.primary.api.dto.RouteRequestDto;
import com.warehouse.routetracker.infrastructure.adapter.primary.api.dto.RouteResponseDto;

//@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN)
public interface RouteMapper {

    RouteResponseDto map(RouteResponse response);

    RouteRequestDto map(RouteRequest request);

    RouteResponse mapToRouteResponse(RouteRequest routeRequest);

    Route mapToRoute(RouteRequest request);
}
