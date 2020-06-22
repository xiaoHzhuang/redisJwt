package com.inspur.supplier.DO;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuanghuan
 * Date: 2020/2/14   20:35
 * Description:
 */
public class SupplierQueryModel {
    private String supplierName;
    private String contactPerson;
    private String phone;

    public SupplierQueryModel(String supplierName, String contactPerson, String phone) {
        this.supplierName = supplierName;
        this.contactPerson = contactPerson;
        this.phone = phone;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}