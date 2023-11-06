package com.warehouse.deliveryreturn.domain.port.primary;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.warehouse.deliveryreturn.domain.model.DeliveryReturnDetails;
import com.warehouse.deliveryreturn.domain.model.DeliveryReturnRequest;
import com.warehouse.deliveryreturn.domain.port.secondary.ParcelStatusControlChangeServicePort;
import com.warehouse.deliveryreturn.domain.port.secondary.RouteLogServicePort;
import com.warehouse.deliveryreturn.domain.service.DeliveryReturnService;
import com.warehouse.deliveryreturn.domain.vo.*;

import com.warehouse.deliveryreturn.infrastructure.adapter.secondary.api.DeliveryReturnRouteDetails;
import com.warehouse.deliveryreturn.infrastructure.adapter.secondary.api.DeliveryReturnRouteRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

@AllArgsConstructor
@Slf4j
public class DeliveryReturnPortImpl implements DeliveryReturnPort {

    private final DeliveryReturnService deliveryReturnService;

    private final ParcelStatusControlChangeServicePort parcelStatusControlChangeServicePort;

    private final RouteLogServicePort logServicePort;

    @Override
    public DeliveryReturnResponse deliverReturn(DeliveryReturnRequest deliveryRequest) {

        validateRequest(deliveryRequest);
        
        if (!deliveryRequest.isReturnProcessType()) {
            logWrongProcessTypeInformation();
            return DeliveryReturnResponse.builder().build();
        }

        deliveryRequest.validateStatuses();

        final Set<DeliveryReturnDetails> deliveryReturnRequests = deliveryRequest.getDeliveryReturnDetails()
                .stream()
                .filter(Objects::nonNull)
                .map(DeliveryReturnDetails::updateDeliveryStatus)
                .collect(Collectors.toSet());

        final List<DeliveryReturn> deliveryReturnResponses =
                deliveryReturnService.deliverReturn(deliveryReturnRequests);

		final List<DeliveryReturnResponseDetails> deliveryReturnResponseDetails = deliveryReturnResponses.stream()
                .map(deliveryReturn -> {
					final UpdateStatusParcelRequest updateStatusParcelRequest = UpdateStatusParcelRequest
                            .builder()
							.parcelId(deliveryReturn.getParcelId())
                            .build();
					final UpdateStatus updateStatus = parcelStatusControlChangeServicePort
							.updateStatus(updateStatusParcelRequest);
                    return new DeliveryReturnResponseDetails(deliveryReturn.getId(), deliveryReturn.getParcelId(),
                            deliveryReturn.getDeliveryStatus(), deliveryReturn.getToken(), updateStatus);
				}).collect(Collectors.toList());
		
		final DeliveryReturnResponse deliveryReturnResponse = DeliveryReturnResponse
                .builder()
				.deliveryReturnResponses(deliveryReturnResponseDetails)
                .supplierCode(deliveryRequest.getZebraDeviceInformation().getUsername())
                .depotCode(deliveryRequest.getZebraDeviceInformation().getDepotCode())
                .build();

        logRouteFlow(deliveryReturnResponse);
        
        return deliveryReturnResponse;
    }

    private void validateRequest(DeliveryReturnRequest deliveryRequest) {
        if (Objects.isNull(deliveryRequest)) {
            // exception
        }
        if (CollectionUtils.isEmpty(deliveryRequest.getDeliveryReturnDetails())) {
            // exception
        }
    }

    private void logRouteFlow(DeliveryReturnResponse deliveryReturnResponse) {
        final DeliveryReturnRouteRequest request = DeliveryReturnRouteRequest.builder()
                .depotCode(deliveryReturnResponse.getDepotCode())
                .username(deliveryReturnResponse.getSupplierCode())
				.deliveryReturnRouteDetails(
						deliveryReturnRouteDetails(deliveryReturnResponse.getDeliveryReturnResponses()))
                .supplierCode(deliveryReturnResponse.getSupplierCode())
                .build();
        //logServicePort.logDeliverReturn(request);
    }
    
	private List<DeliveryReturnRouteDetails> deliveryReturnRouteDetails(
			List<DeliveryReturnResponseDetails> deliveryReturnResponses) {
        return deliveryReturnResponses.stream()
                .map(this::convertToDeliveryReturnRouteDetails)
                .collect(Collectors.toList());
    }

	private DeliveryReturnRouteDetails convertToDeliveryReturnRouteDetails(DeliveryReturnResponseDetails response) {
        return DeliveryReturnRouteDetails.builder()
                .deliveryStatus(response.getDeliveryStatus())
                .id(response.getId())
                .parcelId(response.getParcelId())
                .updateStatus(response.getUpdateStatus().name())
                .returnToken(response.getReturnToken())
                .build();
    }

    private void logWrongProcessTypeInformation() {
        log.warn("Process type is different than RETURN");
    }
}

