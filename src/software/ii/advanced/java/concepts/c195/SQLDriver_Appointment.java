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
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author "Hussan Khan"
 */


// CUSTOM EXCEPTION
// CHECKS APPOINTMENT BIZ HOURS
class InvalidAppointmentTime extends Exception {
    InvalidAppointmentTime(String msg) {
        super(msg);
    };
};

// CHECK OVERLAP APPOINTMENT
class OverlapAppointmentTime extends Exception {
    OverlapAppointmentTime(String msg) {
        super(msg);
    };
};


public class SQLDriver_Appointment{
    
    private String serverName = "3.227.166.251";
    private String username = "U05UOc";
    private String password = "53688609167";
    private String databaseURL = "jdbc:mysql://" + serverName + "/U05UOc";
    
    // Handling DB 
    private Connection connection;
    private Statement statement;
    
    // Establish connection at init
    public SQLDriver_Appointment() {
        
        super();
        
        try {
            connection = DriverManager.getConnection(databaseURL, username, password);
            statement = connection.createStatement();
        } catch ( Exception err ) {
            System.out.println(err);
        };
    };
    
    // CUSTOM ERROR
    public void checkHour(ZonedDateTime localUserTimeStart) throws InvalidAppointmentTime{
        
    // ERROR CHECK - CONVERTING UTC TO USER TIME TO CHECK BIZ HOURS
        LocalDateTime savedStartTimeUser = LocalDateTime.parse( localUserTimeStart.toString().replace('Z', ' ').trim());
        ZonedDateTime utcUserTimeStart = ZonedDateTime.of(savedStartTimeUser, ZoneId.of("UTC") );
        utcUserTimeStart = utcUserTimeStart.withZoneSameInstant( ZoneId.of( TimeZone.getDefault().getID() ).getRules().getOffset(Instant.now()) );
        String savedTimeCalculated = utcUserTimeStart.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss"));
        
        System.out.println("YOUR TIME: " + savedTimeCalculated);
            
        int savedHour = Integer.parseInt(savedTimeCalculated.split(" ")[1].split(":")[0]);
        String userDeviceTime;
        
        // reformatting time to standard from military
        if (savedHour > 11) {
            savedHour = savedHour - 12;
            userDeviceTime = savedHour + "PM";
        } else {
            userDeviceTime = savedHour + "AM";
        };
        
        // BIZ HOUR CHECK 9am-4pm
        if (userDeviceTime.contains("AM")) {
            int stripped = Integer.parseInt(userDeviceTime.replace("AM", ""));
            
            if (!(stripped >= 9)) {
                throw new InvalidAppointmentTime("Not during business hours");
            };
            
        };
        
        if (userDeviceTime.contains("PM")) {
            int stripped = Integer.parseInt(userDeviceTime.replace("PM", ""));
            
            if (!(stripped < 5)) {
                throw new InvalidAppointmentTime("Not during business hours");
            };
            
        };
    };
    
    // checks if appointment overlap
    public int appointmentOverlapCheck(String start, String end) throws OverlapAppointmentTime{
        
        int status = 1;
        
        try {
            ResultSet result = statement.executeQuery(String.format("SELECT * FROM appointment WHERE start <= '%s' and end >= '%s'", start, end));
            
            while (result.next()) {
                
                status = 0;
                
            };
                        
        } catch (Exception err) {
            System.out.println(err);
            System.out.println("from api");
        };
        
        if (status == 0) {
            throw new OverlapAppointmentTime("Appoitnment Overlapping");
        };
        
        
        return 1;
    };
    
    // Add appointment
    public int addAppointment(
            String customerId,
            String title,
            String description,
            String location,
            String contact,
            String type,
            String url,
            String start,
            String end,
            String username,
            ZonedDateTime localUserTimeStart
    ){
        try {
            // CUSTOM EXCEPTION
            checkHour(localUserTimeStart);
            
            // check overlapping
            appointmentOverlapCheck(start, end);
            
        } catch (Exception err) {
            new ErrorPopup().displayError("Appointment not within business hours");
            return 0;
        };
                
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
        
        return 1;
        
    };
    
    // delete appointment
    public void deleteAppointment(String id) {
        
        try {
            statement.executeUpdate(String.format("DELETE FROM appointment WHERE appointmentId = '%s';", id));
        } catch (Exception exc) {
            System.out.println(exc);
        };
        
    };
    
    // GET number of appointments by type
    public String appointmentTypeReport() {
        
        String report = "Month Type_Count\n";
    
        try {
            ResultSet result = statement.executeQuery("SELECT DATE_FORMAT(start, '%Y-%m'), COUNT(type) FROM appointment GROUP BY DATE_FORMAT(start, '%m') ORDER BY DATE_FORMAT(start, '%m') ASC;");
            
            while (result.next()) {
                
                report += result.getString(1) + " " + result.getString(2) + "\n";                
            };
                        
        } catch (Exception err) {
            System.out.println(err);
            System.out.println("from api");
        };
        
        
        return report;
    };
    
    // GET number of appointments by type
    public String consultantReport() {
        
        ObservableList<String> consultants = FXCollections.observableArrayList();
        
        try {
            ResultSet result = statement.executeQuery("SELECT userId, userName FROM user;");
            
            while (result.next()) {
                
                consultants.add(result.getString(1) + " " + result.getString(2));
            };
                        
        } catch (Exception err) {
            System.out.println(err);
            System.out.println("from api");
        };
        
        String report = "";
    
        try {
            
            for (String temp: consultants) {
                report +=  temp + "\n" + "Start \t\t\t End\n";
                ResultSet result = statement.executeQuery(String.format("SELECT start, end FROM appointment WHERE userId = '%s' GROUP BY start ORDER BY start ASC;", temp.split(" ")[0]));
            
                while (result.next()) {
                    report += result.getString(1) + " " + result.getString(2) + "\n";                
                };
                
                report += "-------------------------------------\n";
            };
            
                        
        } catch (Exception err) {
            System.out.println(err);
            System.out.println("from api");
        };
        
        return report;
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
            System.out.println("from api");
        };
            
       return matches;
        
    };
    
    
    // get all Appointments
    public ObservableList<Appointment> AppointmentByMonth(String year, String month){
    
        ObservableList<Appointment> matches = FXCollections.observableArrayList();
        
        if (Integer.parseInt(month) < 10) {
            month = "0" + month;
        };
        
        try {
            ResultSet result = statement.executeQuery(String.format("SELECT * FROM appointment WHERE start LIKE '%s-%s-%s';", year, month, "%"));
            
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
                
                System.out.println(result.getString("description"));
                System.out.println("got it");
                
            };
                        
        } catch (Exception err) {
            System.out.println(err);
            System.out.println("from api");
        };
            
       return matches;
        
    };
    

}
