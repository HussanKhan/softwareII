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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    
    // Add appointment
    public void addAppointment(
            String customerId,
            String title,
            String description,
            String location,
            String contact,
            String type,
            String url,
            String start,
            String end,
            String username
    ){
        String command;
        // '2007-01-01 10:00:00' date store
        try {
             command = String.format("INSERT INTO appointment "
                     + "(customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdateBy) "
                     + "VALUES ( (SELECT customerId FROM customer WHERE customerId = '%s') , (SELECT userId FROM user WHERE username = '%s'), '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', NOW(), '%s', '%s');"
                     , customerId, username, title, description, location, contact, type, url, start, end, username, username );
             statement.executeUpdate(command);
        
        } catch (Exception exc) {
            System.out.println(exc);
        };
        
    };
    
    // delete appointment
    public void deleteAppointment(String id) {
        
        try {
            statement.executeUpdate(String.format("DELETE FROM appointment WHERE appointmentId = '%s';", id));
        } catch (Exception exc) {
            System.out.println(exc);
        };
        
    };
    
    // get all Appointments
    public ObservableList<Appointment> getAllAppointments(){
    
        ObservableList<Appointment> matches = FXCollections.observableArrayList();
        
        try {
            ResultSet result = statement.executeQuery("SELECT * FROM appointment;");
            
            while (result.next()) {
                
                Appointment tempAppoint = new Appointment();
                
                tempAppoint.setId(result.getString("appointmentId"));
                tempAppoint.setCustomerId(result.getString("customerId"));
                tempAppoint.setUserId(result.getString("userId"));
                tempAppoint.setTitle(result.getString("title"));
                tempAppoint.setDescription(result.getString("description"));
                tempAppoint.setLocation(result.getString("location"));
                tempAppoint.setContact(result.getString("contact"));
                tempAppoint.setType(result.getString("type"));
                tempAppoint.setUrl(result.getString("url"));
                tempAppoint.setStart(result.getString("start"));
                tempAppoint.setEnd(result.getString("end"));
                
                matches.add(tempAppoint);
                
            };
                        
        } catch (Exception err) {
            System.out.println(err);
        };
            
       return matches;
        
    };
    

}
