package com.warehouse.terminal.domain.model;

import java.time.Instant;

import org.apache.commons.lang3.StringUtils;

import com.warehouse.commonassets.identificator.DeviceId;
import com.warehouse.terminal.domain.vo.DevicePairId;
import com.warehouse.terminal.infrastructure.adapter.secondary.entity.DeviceEntity;
import com.warehouse.terminal.infrastructure.adapter.secondary.entity.DevicePairEntity;

public class DevicePair {

	private DevicePairId devicePairId;

	private DeviceId deviceId;

	private boolean paired;

	private Instant loginTime;

	private String errorDescription;

	private String pairKey;

	public DevicePair(final DevicePairId devicePairId,
					  final DeviceId deviceId,
					  final boolean paired,
					  final Instant loginTime,
                      final String errorDescription,
					  final String pairKey) {
		this.devicePairId = devicePairId;
		this.deviceId = deviceId;
		this.paired = paired;
		this.loginTime = loginTime;
		this.errorDescription = errorDescription;
        this.pairKey = pairKey;
    }

	public DevicePair(final DeviceId deviceId, final String pairKey, final DevicePairId devicePairId) {
		this.pairKey = pairKey;
		this.paired = false;
		this.deviceId = deviceId;
		this.loginTime = Instant.now();
		this.errorDescription = StringUtils.EMPTY;
		this.devicePairId = devicePairId;
	}

	public DevicePair(final boolean paired) {
		this.paired = paired;
	}

	public String getPairKey() {
		return pairKey;
	}

	public void setPairKey(String pairKey) {
		this.pairKey = pairKey;
	}

	public DevicePairId getDevicePairId() {
		return devicePairId;
	}

	public void setDevicePairId(final DevicePairId devicePairId) {
		this.devicePairId = devicePairId;
	}

	public DeviceId getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(final DeviceId deviceId) {
		this.deviceId = deviceId;
	}

	public boolean isPaired() {
		return paired;
	}

	public void pair() {
		this.paired = true;
        updateLoginTime();
    }

    public void unpair() {
        this.paired = false;
    }

	public Instant getLoginTime() {
		return loginTime;
	}

	public void updateLoginTime() {
		this.loginTime = Instant.now();
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(final String errorDescription) {
		this.errorDescription = errorDescription;
	}

    public static DevicePair from(final DevicePairEntity devicePairEntity) {
		final DeviceEntity deviceEntity = devicePairEntity.getDevice();
		final DevicePairId pairId = new DevicePairId(devicePairEntity.getDevicePairId());
        return new DevicePair(pairId, deviceEntity.getDeviceId(),
                devicePairEntity.isPaired(), devicePairEntity.getLoginTime(),
                devicePairEntity.getErrorDescription(),
				devicePairEntity.getPairKey());
    }

	public boolean containsApiKey() {
		return StringUtils.isNotBlank(pairKey);
	}
}
