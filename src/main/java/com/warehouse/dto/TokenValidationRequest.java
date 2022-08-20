package com.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenValidationRequest {

    @NotNull
    private String parcelId;

    @NotNull
    private Integer token;
}
