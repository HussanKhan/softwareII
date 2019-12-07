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
public class CustomerRecordsDB {
    
    private String serverName = "3.227.166.251";
    private String databaseName = "U05UOc";
    private String username = "U05UOc";
    private String password = "53688609167";
    private String databaseURL = "jdbc:mysql://" + serverName + "/U05UOc";
    
    
    public int addCustomer(){
        return 0;
    };
    
    public int updateCustomer(){
        return 0;
    };
    
    public int deleteCustomer(){
        return 0;
    };
    
    
    public void getData() {
        
        // Connect to DB
        try {
            
            Connection conn = DriverManager.getConnection(databaseURL, username, password);
             
             // QUERY
             Statement statement = conn.createStatement();
             // EXECUTE
             ResultSet result = statement.executeQuery("SELECT * FROM user;");
            // INSERT
//                int result = statement.executeUpdate("INSERT INTO user (userName, password, active, createDate, createdBy, lastUpdateBy) VALUES ('test', 'password', 1, NOW(), 'admin', 'admin')");
                  //System.out.println(result);
//           
            //System.out.println(result.getAsciiStream("TABLE_NAMES"));
             // PROCESS RESULT
             while (result.next()) {
                 // name of column
                 System.out.println(result.getString(1));
                 System.out.println(result.getString(2));
                 System.out.println(result.getString(3));
                 System.out.println(result.getString(4));
                 System.out.println(result.getString(5));
             };
            
        
        } catch (Exception exc) {
            System.out.println(exc);
        };
        
        
    
    };
    
}
