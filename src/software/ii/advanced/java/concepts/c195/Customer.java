/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.ii.advanced.java.concepts.c195;

/**
 *
 * @author "Hussan Khan"
 */
public class Customer {
    
    private String customerName;
    private String active;
    private String address; 
    private String address2;
    private String city;
    private String postal;
    private String phone;
    private String country;
    
    // construct
    public Customer(
            String customerName,
            String active,
            String address, 
            String address2,
            String city,
            String postal,
            String phone,
            String country
    ) {
        
        this.customerName = customerName;
        this.active = active;
        this.address = address;
        this.address2 = address2;
        this.city = city;
        this.postal = postal;
        this.phone = phone;
        this.country = country;
    };

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    

    
}
