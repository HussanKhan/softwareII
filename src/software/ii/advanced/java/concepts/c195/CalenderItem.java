/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.ii.advanced.java.concepts.c195;

import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 *
 * @author "Hussan Khan"
 */
public class CalenderItem {
    
    private String date;
    private int numAppointments = 0;
    private ObservableList<Appointment> todaysAppointments = FXCollections.observableArrayList();;
    
    public CalenderItem(String date, ObservableList<Appointment> currentMonthAppointments) {
        this.date = date;
        
        for (Appointment tempAppoint : currentMonthAppointments) {
            System.out.println(date +"item");
            if (tempAppoint.getStart().contains(date)) {
                todaysAppointments.add(tempAppoint);
                numAppointments++;
            };
        };
       
    };
    
    public VBox generateCard() {
        
        VBox container = new VBox(5);
        
        Label dateLabel = new Label(date.split("-")[2]);
        dateLabel.setStyle("-fx-font-weight: bold");
        
        Label appointments = new Label("Appointments: " + numAppointments);
        
        
        if (numAppointments > 0) {
            appointments.setStyle("-fx-font-weight: bold");
        };
        
        container.getChildren().addAll(dateLabel, appointments);
        
        container.setPadding(new javafx.geometry.Insets(0, 10, 0, 10));
        container.setMinHeight(75);
        container.setMaxHeight(75);
        return container;
    
    };
    
}
