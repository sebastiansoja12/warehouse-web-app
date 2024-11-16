package com.warehouse.department.domain.port.primary;

import java.util.List;

import com.warehouse.department.domain.model.Department;
import com.warehouse.department.domain.port.secondary.DepotRepository;
import com.warehouse.department.domain.vo.DepotCode;
import com.warehouse.department.domain.vo.UpdateStreetRequest;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DepotPortImpl implements DepotPort {


    private final DepotRepository depotRepository;

    @Override
    public Department viewDepotByCode(DepotCode depotCode) {
        return depotRepository.findByCode(depotCode);
    }

    @Override
    public List<Department> findAll() {
        return depotRepository.findAll();
    }

    @Override
    public void addMultipleDepots(List<Department> departments) {
        depotRepository.saveAll(departments);
    }

    @Override
    public void updateStreet(UpdateStreetRequest request) {
        final Department department = depotRepository.findByCode(request.depotCode());
        department.updateStreet(request.street());
        depotRepository.save(department);
    }
}
