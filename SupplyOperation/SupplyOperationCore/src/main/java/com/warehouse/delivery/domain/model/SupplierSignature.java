package com.warehouse.delivery.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SupplierSignature {
    UUID deliveryId;
    String token;
}
