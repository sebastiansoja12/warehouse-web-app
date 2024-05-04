package com.warehouse.routetracker.infrastructure.adapter.primary.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class TerminalRequestDto {
    ProcessTypeDto processType;
    Long parcelId;
    String request;
}
