package com.warehouse.delivery.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SupplierTokenRequest {
    List<DeliveryPackageRequest> deliveryPackageRequests;
}
