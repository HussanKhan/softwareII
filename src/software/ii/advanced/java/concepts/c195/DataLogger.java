/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.ii.advanced.java.concepts.c195;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

/**
 *
 * @author "Hussan Khan"
 */

// LOGS DATA
public class DataLogger {
    
    private SQLDriver_Appointment apiDB = new SQLDriver_Appointment();
    private Formatter writerType;
    private Formatter writerSch;
    private Formatter writerLogin;
    private Formatter writerTime;
    
    public DataLogger() {
        
        
        
        // CHECK IF LOG FILES EXIST
        File monthAppType = new File("AppointmentTypeByMonth.txt");
        File consultantSch = new File("ConsultantSchedule.txt");
        // report of my choice - logs failed login attempts
        File invalidLoginInfo = new File("InvalidLogins.txt");
        
        File userTimeStamps = new File("UserTimeStamps.txt");
        
        // Create file if it does not exist
        if ( !(monthAppType.exists()) ) {
            
            try {
                writerType = new Formatter("AppointmentTypeByMonth.txt");
            } catch (Exception err) {
                System.out.println(err);
            };
            
        };
        
        if ( !(consultantSch.exists()) ) {
            try {
                writerSch = new Formatter("ConsultantSchedule.txt");
            } catch (Exception err) {
                System.out.println(err);
            };
        };
        
        if ( !(invalidLoginInfo.exists()) ) {
            try {
                writerLogin = new Formatter("InvalidLogins.txt");
            } catch (Exception err) {
                System.out.println(err);
            };
        };
        
        if ( !(userTimeStamps.exists()) ) {
            try {
                writerTime = new Formatter("UserTimeStamps.txt");
            } catch (Exception err) {
                System.out.println(err);
            };
        };
        
        // WRITE DATA
        writeAppointmentTypeByMonth();
        writeConsultantReport();
        
    };
    
    // number of appointment types by month
    public void writeAppointmentTypeByMonth() {
        try {
            writerType = new Formatter("AppointmentTypeByMonth.txt");
            writerType.format(apiDB.appointmentTypeReport());
                writerType.close();
        } catch (Exception err) {
            System.out.println(err);
        };
        
    };
    
    //  the schedule for each consultant/user
    public void writeConsultantReport() {
        try {
            writerSch = new Formatter("ConsultantSchedule.txt");
            writerSch.format(apiDB.consultantReport());
            writerSch.close();
        } catch (Exception err) {
            System.out.println(err);
        };
        
    };
    
    // additional report of my choice - failed login
    public void logFailedLogin(String username) {
        
        try {
            
            BufferedWriter writer = new BufferedWriter( 
                   new FileWriter("InvalidLogins.txt", true)); 
            writer.write("Username: " + username + "\t" + "Timestamp: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n"); 
            writer.close();
            
        } catch (Exception err) {
            System.out.println(err);
        };
        
    };
    
    //  track user activity by recording timestamps
    public void trackuser(String username) {
        
        try {
            
            BufferedWriter writer = new BufferedWriter( 
                   new FileWriter("UserTimeStamps.txt", true)); 
            writer.write("Username: " + username + "\t" + "Timestamp: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n"); 
            writer.close();
            
        } catch (Exception err) {
            System.out.println(err);
        };
        
    };
    
    
}
