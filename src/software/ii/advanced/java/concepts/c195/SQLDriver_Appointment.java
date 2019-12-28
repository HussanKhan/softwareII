/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.ii.advanced.java.concepts.c195;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author "Hussan Khan"
 */
public class SQLDriver_Appointment {
    
    private String serverName = "3.227.166.251";
    private String username = "U05UOc";
    private String password = "53688609167";
    private String databaseURL = "jdbc:mysql://" + serverName + "/U05UOc";
    
    // Handling DB 
    private Connection connection;
    private Statement statement;
    
    // Establish connection at init
    public SQLDriver_Appointment() {
        
        try {
            connection = DriverManager.getConnection(databaseURL, username, password);
            statement = connection.createStatement();
        } catch ( Exception err ) {
            System.out.println(err);
        };
    };
    
    // returns all data from result set
    private void printAllData(ResultSet result) {
        
        try {
        
            int colNumbers = result.getMetaData().getColumnCount() + 1;
            
            System.out.println("---------------------------------------------------------------------------------------");
            
            while (result.next()) {
                
                for (int i = 1; i < colNumbers; i++) {
                    
                    System.out.println(result.getMetaData().getColumnName(i) + ": " + result.getString(i));
                
                };
                
                System.out.println("---------------------------------------------------------------------------------------");
             };
             
        } catch (Exception err) {};
        
    };
    
    //
    

}
