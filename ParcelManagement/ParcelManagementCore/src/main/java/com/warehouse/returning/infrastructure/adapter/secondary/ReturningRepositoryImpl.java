package com.warehouse.returning.infrastructure.adapter.secondary;

import static com.warehouse.returning.infrastructure.adapter.secondary.enumeration.ReturnStatus.PROCESSING;
import static org.mapstruct.factory.Mappers.getMapper;

import com.warehouse.returning.domain.model.ReturnPackage;
import com.warehouse.returning.domain.port.secondary.ReturnRepository;
import com.warehouse.returning.domain.vo.ProcessReturn;
import com.warehouse.returning.domain.vo.ReturnId;
import com.warehouse.returning.domain.vo.ReturnModel;
import com.warehouse.returning.infrastructure.adapter.secondary.entity.ReturnEntity;
import com.warehouse.returning.infrastructure.adapter.secondary.exception.ReturnEntityNotFoundException;
import com.warehouse.returning.infrastructure.adapter.secondary.mapper.ReturnMapper;

import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class ReturningRepositoryImpl implements ReturnRepository {

    private final ReturnReadRepository repository;

    private final ReturnMapper returnMapper = getMapper(ReturnMapper.class);
    
    private final String exceptionMessage = "Return Entity for parcel %s was not found";
    private final String returnEntityExceptionMessage = "Return Entity with id [%s] was not found";

    @Override
    public ProcessReturn save(ReturnPackage returnPackage) {
        final ReturnEntity returnEntity = returnMapper.map(returnPackage);
        repository.save(returnEntity);
        return returnMapper.map(returnEntity);
    }

    @Override
    public ProcessReturn update(ReturnPackage returnPackage) {
		final ReturnEntity returnEntity = repository.findFirstByParcelId(returnPackage.getParcelId())
				.orElseThrow(() -> new ReturnEntityNotFoundException(8083,
						String.format(exceptionMessage, returnPackage.getParcelId())));
        if (PROCESSING.equals(returnEntity.getReturnStatus())) {
            returnEntity.completeReturn();
            repository.save(returnEntity);
        }
        return returnMapper.map(returnEntity);
    }

	@Override
	public ReturnModel get(ReturnId returnId) {
		final Optional<ReturnEntity> returnEntity = repository.findFirstByParcelId(returnId.getValue());
		return returnEntity.map(returnMapper::mapToReturnModel)
				.orElseThrow(() -> new ReturnEntityNotFoundException(8084,
						String.format(returnEntityExceptionMessage, returnId.getValue())));
	}

    @Override
    public void delete(ReturnId returnId) {
        final Long id = returnMapper.map(returnId);
        repository.deleteById(id);
    }
}
