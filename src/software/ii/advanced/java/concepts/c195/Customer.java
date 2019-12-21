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
    private String customerId;
    private String active;
    private String address; 
    private String address2;
    private String city;
    private String postal;
    private String phone;
    private String country;
    
    // construct
    public Customer(
    ) {
        
    };
    
    public void printAll(){
        System.out.println("ID: " + customerId);
        System.out.println("NAME: " + customerName);
        System.out.println("ADDRESS: " + address);
        System.out.println("ADDRESS2: " + address2);
        System.out.println("CITY: " + city);
        System.out.println("POSTAL: " + postal);
        System.out.println("PHONE: " + phone);
        System.out.println("COUNTRY: " + country);
    };
    
    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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
