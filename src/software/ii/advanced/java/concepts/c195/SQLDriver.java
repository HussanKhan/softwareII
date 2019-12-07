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
