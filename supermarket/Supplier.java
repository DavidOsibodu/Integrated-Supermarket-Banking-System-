package supermarket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author David
 */
public class Supplier {

    private String name;
    private String address;
    private String supplierSince;

    public Supplier(String name, String address, String supplierSince) {
        this.name = name;
        this.address = address;
        this.supplierSince = supplierSince;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getSupplierSince() {
        return supplierSince;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSupplierSince(String supplierSince) {
        this.supplierSince = supplierSince;
    }

}
