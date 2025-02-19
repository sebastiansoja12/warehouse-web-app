package com.warehouse.redirect.domain.service;

import com.warehouse.commonassets.identificator.ShipmentId;
import com.warehouse.redirect.domain.vo.RedirectToken;
import com.warehouse.redirect.domain.vo.Token;

public interface RedirectService {
    Token saveRedirectToken(RedirectToken redirectToken);

    void invalidateToken(ShipmentId shipmentId);
}
