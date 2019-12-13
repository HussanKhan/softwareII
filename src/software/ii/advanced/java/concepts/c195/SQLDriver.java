/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.ii.advanced.java.concepts.c195;

import java.sql.*;

/**
 *
 * @author "Hussan Khan"
 */
public class SQLDriver {
    
    // figure out add customer
    // add items from backwards
    // from country to city to address etc
    //
    
    private String serverName = "3.227.166.251";
    private String databaseName = "U05UOc";
    private String username = "U05UOc";
    private String password = "53688609167";
    private String databaseURL = "jdbc:mysql://" + serverName + "/U05UOc";
    
    // Handling DB 
    private Connection connection;
    private Statement statement;
    
    // Establish connection at init
    public SQLDriver() {
        
        try {
            connection = DriverManager.getConnection(databaseURL, username, password);
            statement = connection.createStatement();
        } catch ( Exception err ) {
            System.out.println(err);
        };
        
    };
    
    // Add country if not in DB
    public int setCountry(String country, String username) {
        
        // check to see if in DB
        String command;
        try {
             // QUERY
             Statement statement = connection.createStatement();
             // EXECUTE
             ResultSet result = statement.executeQuery(String.format("SELECT * FROM country WHERE country = '%s';", country ));

             result.last();
             
             // already in DB
             if (result.getRow() > 0) {
                 return 1;
             }
        
        } catch (Exception exc) {
            System.out.println(exc);
            
        };

        
        // Insert into DB
        try {
             command = String.format("INSERT INTO country (country, createDate, createdBy, lastUpdateBy) VALUES ('%s', NOW(), '%s', '%s');", country, username, username);
             statement.executeUpdate(command);
        
        } catch (Exception exc) {
            System.out.println(exc);
            System.out.println("country");
        };
     
        return 0;
    };
    
    // Add city if not in DB
    public int setCity(String city, String username, String country) {
        
        // check to see if in DB
        String command;
        try {
             // QUERY
             Statement statement = connection.createStatement();
             // EXECUTE
             ResultSet result = statement.executeQuery(String.format("SELECT * FROM city WHERE city = '%s';", city));

             result.last();
             
             // already in DB
             if (result.getRow() > 0) {
                 return 1;
             }
        
        } catch (Exception exc) {
            System.out.println(exc);
        };

        
        // Insert into DB
        try {
             command = String.format("INSERT INTO city (city, countryId, createDate, createdBy, lastUpdateBy) VALUES ('%s', (SELECT countryId FROM country WHERE country='%s'), NOW(), '%s', '%s');", city, country, username, username );
             statement.executeUpdate(command);
        
        } catch (Exception exc) {
            System.out.println(exc);
            System.out.println("city");
        };
     
        return 0;
    };
    
    
        // Add city if not in DB
    public int setAddress(
            String address, 
            String address2, 
            String city, 
            String postal, 
            String phone,
            String username) {
        
        // check to see if in DB
        String command;
        try {
             // QUERY
             Statement statement = connection.createStatement();
             // EXECUTE
             ResultSet result = statement.executeQuery(String.format("SELECT * FROM address WHERE address = '%s' AND postalCode = '%s' AND phone = '%s';", address, postal, phone));

             result.last();
             
             // already in DB
             if (result.getRow() > 0) {
                 return 1;
             }
        
        } catch (Exception exc) {
            System.out.println(exc);
        };

        
        // Insert into DB
        try {
             command = String.format("INSERT INTO address (address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdateBy) VALUES ('%s', '%s', (SELECT cityId FROM city WHERE city='%s'), '%s', '%s', NOW(), '%s', '%S');", address, address2, city, postal, phone, username, username );
             statement.executeUpdate(command);
        
        } catch (Exception exc) {
            System.out.println(exc);
            System.out.println("address");

        };
     
        return 0;
    };
    
        // Add city if not in DB
    public int setCustomer(
            String customerName,
            String active,
            String address, 
            String address2,
            String postal, 
            String phone,
            String city,
            String country,
	    String username) {
        
        // check to see if in DB
        String command;
        try {
             // QUERY
             Statement statement = connection.createStatement();
             // EXECUTE
             ResultSet result = statement.executeQuery(String.format("SELECT * FROM customer WHERE customerName='%s' AND addressId=(SELECT addressId FROM address WHERE address='%s' AND postalCode='%s' AND phone='%s');", customerName, address, postal, phone));

             result.last();
             
             // already in DB
             if (result.getRow() > 0) {
                 return 1;
             }
        
        } catch (Exception exc) {
            System.out.println(exc);
        };

        
        // Insert into DB
        try {
            
         // Add country
         setCountry(country, username);
         // Add City
         setCity(city, username, country);
         
        // Add address
         setAddress(address, address2, city, postal, phone, username);

             command = String.format("INSERT INTO customer (customerName, addressId, active, createDate, createdBy, lastUpdateBy) VALUES ('%s', (SELECT addressId FROM address WHERE address='%s' AND postalCode='%s' AND phone='%s'), %s, NOW(), '%s', '%S');", customerName, address, postal, phone, active, username, username );
             statement.executeUpdate(command);
        
        } catch (Exception exc) {
            System.out.println(exc);
            System.out.println("Customer");
        };
     
        return 0;
    };
    
        // Add city if not in DB
    // DATETIME == YYYY-MM-DD HH:MM:SS
    public int setAppointment(
            String customerName, 
            String phone,
            String username,
            String title,
            String desc,
            String location,
            String contact,
            String type,
            String url,
            String start,
            String end
            ) {
        
        // check to see if in DB
//        String command;
//        try {
//             // QUERY
//             Statement statement = connection.createStatement();
//             // EXECUTE
//             ResultSet result = statement.executeQuery(String.format("SELECT * FROM customer WHERE customerName='%s' AND addressId='(SELECT addressId FROM address WHERE address='%s' AND postalCode='%s' AND phone='%s')';", customerName, address, postal, phone));
//
//             result.last();
//             
//             // already in DB
//             if (result.getRow() > 0) {
//                 return 1;
//             }
//        
//        } catch (Exception exc) {
//            System.out.println(exc);
//        };

//        
//        // Insert into DB
//        try {
//             command = String.format("INSERT INTO customer (customerName, addressId, active, createDate, createdBy, lastUpdateBy) VALUES ('%s', (SELECT addressId FROM address WHERE address='%s' AND postalCode='%s' AND phone='%s'), NOW(), '%s', '%S');", customerName, address, postal, phone, username, username );
//             statement.executeUpdate(command);
//        
//        } catch (Exception exc) {
//            System.out.println(exc);
//        };
     
        return 0;
    };
      
    public int customerData(
            String customerName,
            String active,
            String address, 
            String address2,
            String postal, 
            String phone,
            String city,
            String country,
            String username
        ) {
        
        // Add Customer
        setCustomer(
                customerName,
                active,
                address, 
                address2,
                postal, 
                phone,
                city,
                country,
                username
        );
        
        return 0;
 
    };
    
    public int getAllCustomers(){
        
        try {
            ResultSet result = statement.executeQuery("SELECT * FROM address;");
            
            while (result.next()) {
                 // name of column
                 System.out.println(result.getString(1));
                 System.out.println(result.getString(2));
                 System.out.println(result.getString(3));
                 System.out.println(result.getString(4));
                 System.out.println(result.getString(5));
             };
             
        } catch (Exception err) {};
            
        return 0;
    };
    // Set country
//    public int setCountry() {
//        
//        String command;
//        
//        try {
//             
//             // insert country into db
//             command = String.format("INSERT INTO country (country, createdate, createdBy, lastUpdateBy) VALUES ('%s', NOW(), '%s', '%s');", "United States", username, username);
//             statement.executeUpdate(command);
//             command = String.format("INSERT INTO country (country, createdate, createdBy, lastUpdateBy) VALUES ('%s', NOW(), '%s', '%s');", "England", username, username);
//             statement.executeUpdate(command);
//             
//             
//             return 0;
//        
//             
//        } catch (Exception exc) {
//            System.out.println(exc);
//        };
//        
//        return 0;
//    };
    
//        // Set country
//    public int setCity() {
//        
//        String command;
//        
//        try {
//             
//             // insert country into db
//             command = String.format("INSERT INTO city (city, countryId, createdate, createdBy, lastUpdateBy) VALUES ('%s', (SELECT countryId FROM country WHERE country='United States'), NOW(), '%s', '%s');", "Phoenix", username, username);
//             statement.executeUpdate(command);
//             command = String.format("INSERT INTO city (city, countryId, createdate, createdBy, lastUpdateBy) VALUES ('%s', (SELECT countryId FROM country WHERE country='United States'), NOW(), '%s', '%s');", "New York", username, username);
//             statement.executeUpdate(command);
//             command = String.format("INSERT INTO city (city, countryId, createdate, createdBy, lastUpdateBy) VALUES ('%s', (SELECT countryId FROM country WHERE country='England'), NOW(), '%s', '%s');", "London", username, username);
//             statement.executeUpdate(command);
//             return 0;
//        
//             
//        } catch (Exception exc) {
//            System.out.println(exc);
//        };
//        
//        return 0;
//    };
    
    
    public void viewCity() {
        
        try {
            ResultSet result = statement.executeQuery("SELECT * FROM city;");
            
            while (result.next()) {
                 // name of column
                 System.out.println(result.getString(1));
                 System.out.println(result.getString(2));
                 System.out.println(result.getString(3));
                 System.out.println(result.getString(4));
                 System.out.println(result.getString(5));
             };
             
        } catch (Exception err) {};
            
    };
    
    
    // Check login creds
    public int login(String userName, String passwordUser) {
        
        int queryState = 0;
        
        try {
             ResultSet result = statement.executeQuery("SELECT username, password FROM user WHERE userName='" + userName + "' AND password='" + passwordUser + "';");
            
             result.last();
             if (result.getRow() > 0) {
                 queryState = 1;
             }
             
        } catch (Exception exc) {
            System.out.println(exc);
        };
        
        return queryState;
    };
    
    
    public void getData() {
        
        // Connect to DB
        try {
            
            Connection conn = DriverManager.getConnection(databaseURL, username, password);
             
             // QUERY
             Statement statement = conn.createStatement();
             // EXECUTE
             ResultSet result = statement.executeQuery("SELECT * FROM city;");
            // INSERT
//                int result = statement.executeUpdate("INSERT INTO user (userName, password, active, createDate, createdBy, lastUpdateBy) VALUES ('test', 'password', 1, NOW(), 'admin', 'admin')");
                  //System.out.println(result);
//           
            //System.out.println(result.getAsciiStream("TABLE_NAMES"));
             // PROCESS RESULT
             while (result.next()) {
                 // name of column
                 int numCol = result.getMetaData().getColumnCount();
                 
                 for (int i = 1; i < numCol + 1; i++) {
                     System.out.println(result.getMetaData().getColumnName(i));
                 };
                 
//                 System.out.println(result.getString(2));
//                 System.out.println(result.getString(3));
//                 System.out.println(result.getString(4));
//                 System.out.println(result.getString(5));
             };
            
        
        } catch (Exception exc) {
            System.out.println(exc);
        };

    };
    
    // CUSTOMER DB HANDLING
    public int addCustomer(){
        
        int success = 0;
        
        try {
            success = statement.executeUpdate("INSERT INTO customer (customerName, password, active, createDate, createdBy, lastUpdateBy) VALUES ('test', 'password', 1, NOW(), 'admin', 'admin')");
        } catch (Exception err) {
            System.out.println(err);
        };
        
        return success;
    };
    
    public int updateCustomer(){
        return 0;
    };
    
    public int deleteCustomer(){
        return 0;
    };
    
    // APPOINTMENT DB HANDLING
    public int addAppointment(){
        return 0;
    };
    
    public int updateAppointment(){
        return 0;
    };
    
    public int deleteAppointment(){
        return 0;
    };
    
}
