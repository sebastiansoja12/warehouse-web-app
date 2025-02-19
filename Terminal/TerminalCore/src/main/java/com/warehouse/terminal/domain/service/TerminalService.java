package com.warehouse.terminal.domain.service;

import java.util.List;

import com.warehouse.commonassets.enumeration.DeviceType;
import com.warehouse.commonassets.identificator.DeviceId;
import com.warehouse.commonassets.identificator.Username;
import com.warehouse.terminal.domain.model.Terminal;
import com.warehouse.terminal.domain.model.request.DeviceSettingsRequest;

public interface TerminalService {
    void createTerminal(final Terminal terminal);
    void changeDeviceType(final DeviceId deviceId, final DeviceType deviceType);
    void assignUser(final DeviceId deviceId, final Username username);
    void updateVersion(final DeviceId deviceId, final String version);
    Terminal findByDeviceId(final DeviceId deviceId);
    List<Terminal> findAll();

    void updateSettings(final DeviceSettingsRequest request);
}
