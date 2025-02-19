package com.warehouse.deliveryreject.infrastructure.adapter.secondary.api;

import com.warehouse.delivery.dto.DeliveryStatusDto;
import com.warehouse.delivery.dto.ShipmentIdDto;

public record RejectTrackerRequestDetailsDto(ShipmentIdDto shipmentId, RejectReasonDto rejectReason,
                                             DeliveryStatusDto deliveryStatus, ProcessTypeDto processType) {
}
