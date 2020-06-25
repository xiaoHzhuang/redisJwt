package com.inspur.supplier.service;

import com.inspur.system.page.PO.PageRequest;
import com.inspur.system.page.PO.PageResult;
import com.inspur.supplier.DO.Supplier;
import com.inspur.supplier.DO.SupplierQueryModel;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuanghuan
 * Date: 2020/2/14   20:09
 * Description:
 */
public interface ISupplierService {
    void save(Supplier supplier);

    PageResult getAllSupplier(SupplierQueryModel memberQueryModel, PageRequest pageRequest);

    Supplier getSupplierById(String id);

    void deleteSupplierById(String id);
}