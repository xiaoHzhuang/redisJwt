package com.inspur.supplier.controller;

import com.inspur.system.page.PO.PageRequest;
import com.inspur.system.page.PO.PageResult;
import com.inspur.supplier.DO.Supplier;
import com.inspur.supplier.DO.SupplierQueryModel;
import com.inspur.supplier.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuanghuan
 * Date: 2020/2/14   20:07
 * Description:
 */
@RestController
@RequestMapping("supplier")
public class SupplierController {
    @Autowired
    private ISupplierService supplierService;

    @RequestMapping("save")
    public Map<String, String> save(@RequestBody Supplier supplier) {
        Map<String, String> rs = new HashMap<String, String>();
        rs.put("code", "1");
        try {
            supplierService.save(supplier);
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("code", "0");
        }
        return rs;
    }

    @RequestMapping("list/search/{pageNum}/{pageSize}")
    public PageResult getAllSupplier(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize, String supplierName, String phone, String contactPerson) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNum(pageNum);
        pageRequest.setPageSize(pageSize);
        SupplierQueryModel memberQueryModel = new SupplierQueryModel(supplierName, contactPerson, phone);
        return supplierService.getAllSupplier(memberQueryModel, pageRequest);
    }

    @RequestMapping("get/{id}")
    public Supplier getSupplierById(@PathVariable("id") String id) {
        return supplierService.getSupplierById(id);
    }

    @RequestMapping("detete/{id}")
    public Map<String, String> deleteMemberById(@PathVariable("id") String id) {
        Map<String, String> rs = new HashMap<String, String>();
        try {
            supplierService.deleteSupplierById(id);
            rs.put("code", "1");
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("code", "0");
        }
        return rs;
    }

}