package com.warehouse.redirect.domain.model;

import com.warehouse.redirect.domain.vo.Token;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RedirectResponse {
    Token token;
    Long parcelId;
}
