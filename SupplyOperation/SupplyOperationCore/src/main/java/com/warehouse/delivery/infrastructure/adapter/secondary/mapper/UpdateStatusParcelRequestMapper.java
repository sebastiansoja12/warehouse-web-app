package com.warehouse.delivery.infrastructure.adapter.secondary.mapper;

import com.warehouse.delivery.domain.vo.ParcelStatus;
import com.warehouse.delivery.infrastructure.adapter.secondary.api.ParcelDto;
import com.warehouse.delivery.infrastructure.adapter.secondary.api.ParcelIdDto;
import com.warehouse.delivery.infrastructure.adapter.secondary.api.StatusDto;
import com.warehouse.delivery.infrastructure.adapter.secondary.api.UpdateStatusParcelRequestDto;
import com.warehouse.delivery.domain.vo.UpdateStatusParcelRequest;
import org.mapstruct.Mapper;


@Mapper
public interface UpdateStatusParcelRequestMapper {
    default UpdateStatusParcelRequestDto map(UpdateStatusParcelRequest updateStatusParcelRequest) {
        final ParcelIdDto parcelId = ParcelIdDto.builder()
                .value(updateStatusParcelRequest.getParcelId())
                .build();
        final StatusDto status = map(updateStatusParcelRequest.getParcelStatus());
        final ParcelDto parcel = new ParcelDto(parcelId, status);
        return new UpdateStatusParcelRequestDto(parcel);
    }

    StatusDto map(ParcelStatus parcelStatus);
}
