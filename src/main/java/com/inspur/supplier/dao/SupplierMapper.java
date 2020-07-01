package com.inspur.supplier.dao;

import com.inspur.supplier.DO.Supplier;
import com.inspur.supplier.DO.SupplierQueryModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SupplierMapper {
    int insert(Supplier record);

    int insertSelective(Supplier record);

    void update(Supplier supplier);

    List<Supplier> listSuppliers(SupplierQueryModel supplierQueryModel);

    Supplier getSupplierById(@Param("id") String id);

    void deleteSupplierById(@Param("id") String id);
}