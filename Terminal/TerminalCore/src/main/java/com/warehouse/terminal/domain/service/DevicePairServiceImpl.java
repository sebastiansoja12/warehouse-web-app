package com.warehouse.terminal.domain.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.warehouse.commonassets.identificator.DeviceId;
import com.warehouse.terminal.domain.model.DevicePair;
import com.warehouse.terminal.domain.model.Terminal;
import com.warehouse.terminal.domain.port.secondary.DevicePairRepository;
import com.warehouse.terminal.domain.vo.DevicePairId;

public class DevicePairServiceImpl implements DevicePairService {

    private final DevicePairRepository devicePairRepository;

    public DevicePairServiceImpl(final DevicePairRepository devicePairRepository) {
        this.devicePairRepository = devicePairRepository;
    }

    @Transactional
    @Override
    public void pairDevice(final Terminal terminal) {
        final Optional<DevicePair> devicePair = this.devicePairRepository.findByDeviceId(terminal.getDeviceId());
        devicePair.ifPresent(device -> {
            if (!device.isPaired()) {
                device.pair();
                this.devicePairRepository.save(device);
            }
        });

        if (devicePair.isEmpty()) {
            final DevicePairId devicePairId = this.nextDevicePairId();
            final String pairKey = DevicePairKeyGeneratorService.generatePairKey();
            this.devicePairRepository.save(new DevicePair(terminal.getDeviceId(), pairKey, devicePairId));
        }
	}

    @Override
    public void unpairDevice(final DeviceId deviceId) {
        final DevicePair devicePair = this.devicePairRepository.findDevicePairByDeviceId(deviceId);
        devicePair.unpair();
        this.devicePairRepository.update(devicePair);
    }

    @Override
    public DevicePair findByDeviceId(final DeviceId deviceId) {
        return this.devicePairRepository.findDevicePairByDeviceId(deviceId);
    }

    private DevicePairId nextDevicePairId() {
        return new DevicePairId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
    }
}
