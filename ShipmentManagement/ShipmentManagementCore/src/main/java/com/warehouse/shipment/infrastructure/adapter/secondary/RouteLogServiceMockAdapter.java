package com.warehouse.shipment.infrastructure.adapter.secondary;

import com.warehouse.commonassets.identificator.ShipmentId;
import com.warehouse.shipment.domain.port.secondary.RouteLogServicePort;
import com.warehouse.shipment.domain.vo.RouteProcess;
import com.warehouse.shipment.domain.vo.SoftwareConfiguration;

public class RouteLogServiceMockAdapter implements RouteLogServicePort {

    @Override
    public RouteProcess initializeRouteProcess(final ShipmentId shipmentId,
                                               final SoftwareConfiguration softwareConfiguration) {
        return null;
    }
}
