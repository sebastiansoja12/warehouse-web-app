package com.warehouse.deliveryreturn.infrastructure.adapter.secondary;

import static org.mapstruct.factory.Mappers.getMapper;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestGatewaySupport;

import com.warehouse.deliveryreturn.domain.port.secondary.ParcelStatusControlChangeServicePort;
import com.warehouse.deliveryreturn.domain.vo.UpdateStatus;
import com.warehouse.deliveryreturn.domain.vo.UpdateStatusParcelRequest;
import com.warehouse.deliveryreturn.infrastructure.adapter.secondary.api.dto.UpdateStatusParcelRequestDto;
import com.warehouse.deliveryreturn.infrastructure.adapter.secondary.mapper.UpdateStatusParcelRequestMapper;
import com.warehouse.deliveryreturn.infrastructure.adapter.secondary.property.ParcelStatusProperty;


public class ParcelStatusControlChangeServiceAdapter extends RestGatewaySupport
		implements ParcelStatusControlChangeServicePort {

    private final RestClient restClient;

    private final ParcelStatusProperty parcelStatusProperty;

    private final UpdateStatusParcelRequestMapper updateStatusParcelRequestMapper =
            getMapper(UpdateStatusParcelRequestMapper.class);

    public ParcelStatusControlChangeServiceAdapter(ParcelStatusProperty parcelStatusProperty) {
        this.restClient = RestClient.builder().baseUrl(parcelStatusProperty.getUrl()).build();
        this.parcelStatusProperty = parcelStatusProperty;
    }

    @Override
    public UpdateStatus updateStatus(UpdateStatusParcelRequest updateStatusParcelRequest) {
        final UpdateStatusParcelRequestDto request = updateStatusParcelRequestMapper.map(updateStatusParcelRequest);
        return restClient
                .post()
                .uri("/v2/api/{url}", parcelStatusProperty.getEndpoint())
                .body(request)
                .contentType(MediaType.APPLICATION_JSON)
                .exchange((req, res) -> {
                    final HttpStatusCode httpStatusCode = HttpStatusCode.valueOf(res.getStatusCode().value());
                    if (httpStatusCode.is2xxSuccessful()) {
                        return UpdateStatus.OK;
                    }
                    if (httpStatusCode.is5xxServerError()) {
                        return UpdateStatus.ERROR;
                    } else {
                        return UpdateStatus.NOT_OK;
                    }
                });
    }
}
