package com.warehouse.shipment.mapper;

import com.warehouse.shipment.infrastructure.adapter.secondary.enumeration.Size;
import com.warehouse.shipment.infrastructure.adapter.secondary.enumeration.Status;
import com.warehouse.shipment.domain.model.Parcel;
import com.warehouse.shipment.infrastructure.adapter.secondary.entity.ParcelEntity;
import com.warehouse.shipment.infrastructure.adapter.secondary.mapper.ParcelMapper;
import com.warehouse.shipment.infrastructure.adapter.secondary.mapper.ParcelMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParcelMapperTest {

    private ParcelMapper mapper;


    @BeforeEach
    void setup() {
        mapper = new ParcelMapperImpl();
    }

    @Test
    void shouldMapFromParcelToEntity() {
        // given
        final Parcel parcel = createParcel();

        // when
        final ParcelEntity parcelEntity = null;

        // then
        assertThat(parcelEntity.getParcelSize().getSize()).isEqualTo("test");
        // and status is enum type CREATED
        assertThat(parcelEntity.getStatus().name()).isEqualTo("CREATED");

    }

    @Test
    void shouldMapFromEntityToParcel() {
        // given
        final ParcelEntity parcelEntity = ParcelEntity.builder()
                .parcelSize(Size.TEST)
                .lastName("test")
                .status(Status.CREATED)
                .build();
        // when
        final Parcel parcel = mapper.map(parcelEntity);

        // then
        assertThat(parcel.getPrice()).isEqualTo(99);
        assertThat(parcel.getSender().getLastName()).isEqualTo("test");

        // and status is enum type CREATED
        assertThat(parcel.getStatus()).isEqualTo(Status.CREATED);
    }
    private Parcel createParcel() {
        return Parcel.builder()
                .parcelSize(Size.TEST)
                .id(1L)
                .price(20)
                .destination("KT1")
                .status(Status.CREATED)
                .sender(null)
                .recipient(null)
                .build();
    }

}
