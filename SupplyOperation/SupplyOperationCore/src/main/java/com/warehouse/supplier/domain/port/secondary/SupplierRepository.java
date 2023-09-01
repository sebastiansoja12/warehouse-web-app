package com.warehouse.supplier.domain.port.secondary;

import java.util.List;

import com.warehouse.supplier.domain.model.Supplier;
import com.warehouse.supplier.domain.model.SupplierModelRequest;

public interface SupplierRepository {
    Supplier create(Supplier supplier);

    List<Supplier> findAll();

    List<SupplierModelRequest> createMultipleSuppliers(List<Supplier> suppliers);

    List<Supplier> findByDepotCode(String depotCode);

    List<Supplier> findBySupplierCode(String supplierCode);

    Supplier findByCode(String supplierCode);
}
