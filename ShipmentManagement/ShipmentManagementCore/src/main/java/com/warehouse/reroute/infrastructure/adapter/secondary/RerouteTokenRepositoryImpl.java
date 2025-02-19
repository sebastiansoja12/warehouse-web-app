package com.warehouse.reroute.infrastructure.adapter.secondary;

import com.warehouse.commonassets.identificator.ShipmentId;
import com.warehouse.reroute.domain.model.RerouteToken;
import com.warehouse.reroute.domain.model.Token;
import com.warehouse.reroute.domain.port.secondary.RerouteTokenRepository;
import com.warehouse.reroute.domain.vo.RerouteProcessor;
import com.warehouse.reroute.infrastructure.adapter.secondary.entity.RerouteTokenEntity;
import com.warehouse.reroute.infrastructure.adapter.secondary.exception.RerouteTokenNotFoundException;
import com.warehouse.reroute.infrastructure.adapter.secondary.mapper.RerouteTokenMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RerouteTokenRepositoryImpl implements RerouteTokenRepository {

    private final RerouteTokenMapper rerouteTokenMapper;

    private final RerouteTokenReadRepository repository;

	@Override
	public RerouteToken loadByTokenAndParcelId(Integer token, Long parcelId) {
		return repository.loadByTokenAndParcelId(token, parcelId).map(rerouteTokenMapper::map)
				.orElseThrow(() -> new RerouteTokenNotFoundException("Reroute token was not found"));
	}

    @Override
    public RerouteToken findByToken(Token token) {
        return repository.findByToken(token.getValue()).map(rerouteTokenMapper::map).orElseThrow(
                () -> new RerouteTokenNotFoundException("Reroute token was not found"));
    }

    @Override
    public RerouteToken saveReroutingToken(RerouteProcessor rerouteProcessor) {
        final RerouteTokenEntity entity = rerouteTokenMapper.map(rerouteProcessor);
        repository.save(entity);
        return rerouteTokenMapper.map(entity);
    }

    @Override
    public void deleteByToken(RerouteToken token) {
        repository.deleteByToken(token.getToken());
    }

    @Override
    public RerouteToken findByShipmentId(final ShipmentId shipmentId) {
        return this.repository.findByParcelId(shipmentId.getValue())
                .map(rerouteTokenMapper::map)
                .orElseThrow(() -> new RerouteTokenNotFoundException("Reroute token was not found"));
    }

    @Override
    public void update(final RerouteToken rerouteToken) {
        final RerouteTokenEntity entity = rerouteTokenMapper.map(rerouteToken);
        this.repository.save(entity);
    }
}
