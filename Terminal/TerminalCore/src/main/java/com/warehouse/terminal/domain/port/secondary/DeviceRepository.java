package com.warehouse.terminal.domain.port.secondary;

import java.util.List;

import com.warehouse.commonassets.identificator.DeviceId;
import com.warehouse.terminal.domain.model.Terminal;

public interface DeviceRepository<T> {
    T findById(final DeviceId deviceId);
    T saveOrUpdate(final Terminal terminal);
    List<T> findAll();
}
