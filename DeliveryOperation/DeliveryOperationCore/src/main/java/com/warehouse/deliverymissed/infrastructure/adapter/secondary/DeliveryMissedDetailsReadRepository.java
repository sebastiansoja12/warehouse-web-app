package com.warehouse.deliverymissed.infrastructure.adapter.secondary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.warehouse.deliverymissed.infrastructure.adapter.secondary.entity.DeliveryMissedDetailsEntity;
import com.warehouse.deliverymissed.infrastructure.adapter.secondary.entity.id.DeliveryMissedDetailId;

@Repository
public interface DeliveryMissedDetailsReadRepository extends JpaRepository<DeliveryMissedDetailsEntity, DeliveryMissedDetailId> {
}
