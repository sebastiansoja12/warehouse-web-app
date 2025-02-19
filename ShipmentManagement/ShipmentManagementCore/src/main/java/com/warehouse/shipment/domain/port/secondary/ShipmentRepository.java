package com.warehouse.shipment.domain.port.secondary;

import com.warehouse.commonassets.identificator.ShipmentId;
import com.warehouse.shipment.domain.model.Shipment;

public interface ShipmentRepository {

    void createOrUpdate(final Shipment shipment);

    Shipment findById(final ShipmentId shipmentId);

    boolean exists(final ShipmentId shipmentId);
}
