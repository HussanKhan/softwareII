/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.ii.advanced.java.concepts.c195;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author "Hussan Khan"
 */
public class Calender_CRUD {
    
    private Scene lastScene;
    private Stage lastStage;
    private String username;
    private SQLDriver_Appointment apiDB = new SQLDriver_Appointment();
    
    private Calendar mainCalender = Calendar.getInstance();
    
    private int year = mainCalender.get(Calendar.YEAR);
    private int month = mainCalender.get(Calendar.MONTH);
    private String monthName;
    
    private GridPane calenderGrid = new GridPane();
    private HBox header = new HBox(7);
    private ObservableList<Appointment> currentMonthAppointments;
    
    
    private String calMode = "Month";
    private int weeksInCurrentMonth;
    private int currentWeekSelected = 0;
    
    public Calender_CRUD(Scene prevScene, Stage mainStage, String usernam) {
        
        if (prevScene != null) {
            lastScene = prevScene;
        };
        
        this.username = username;
        lastStage = mainStage;
        calenderGrid.setVgap(40); // spacing bewteen all objects
        
        System.out.println(month+1);
        System.out.println(year);
        
        // Set current appointments
        currentMonthAppointments = apiDB.AppointmentByMonth(Integer.toString(year), Integer.toString(month+1));
        
        // Set init values
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        
        // Month name
        mainCalender.set(year, month, 1);
        monthName = months[month];
        calenderGrid.setStyle("-fx-background-color: #FFFFFF;");
         
    };
    

    public int renderCalender(int weekIncr) {
        
        // incr month if too many weeks forward
        if ((weekIncr - 1 > weeksInCurrentMonth) && weekIncr != 100) {
            updateCalender("nextMonth");
            updateCalender("initWeek");
            return 0;
        };
        
        if (weekIncr - 1 < 0) {
            updateCalender("lastMonth");
            updateCalender("initWeek");
            return 0;
        };
        
        ObservableList<String> daysInMonth = FXCollections.observableArrayList();
        
        System.out.println("Month: " + month);
        System.out.println("IMonth: " + mainCalender.get(Calendar.MONTH));
        
        while (month==mainCalender.get(Calendar.MONTH)) {
            System.out.println("ran caledner " + month);
            daysInMonth.add( String.format("%s %s", mainCalender.getTime().getDay(), mainCalender.getTime().toString().split(" ")[2] ));           
            mainCalender.add(Calendar.DAY_OF_MONTH, 1);
        };
        
        ObservableList<String> daysInWeek = FXCollections.observableArrayList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
        
        for (int i = 0; i < daysInWeek.size(); i++) {
            Label calenderValue = new Label( daysInWeek.get(i) );
            
            calenderValue.setMinWidth(160.0);
            calenderValue.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
            calenderValue.setStyle("-fx-background-color: #bdc3c7;");
            GridPane.setConstraints( calenderValue, i, 0);  
            calenderGrid.getChildren().add(calenderValue);
        };
                
        int weekNum = 1;
        weeksInCurrentMonth = 0;
        
        if (weekIncr == 100) {
            
            // Fill calender
            for (int j = 0; j < daysInMonth.size(); j++) {
                
                if (Integer.parseInt(daysInMonth.get(j).split(" ")[0]) == 0) {
                    weekNum += 1;
                    weeksInCurrentMonth++;
                };
            
                String formatMonth;
            
                if (month < 10) {
                    formatMonth = String.format("0%s", month+1);
                } else {
                    formatMonth = String.format("%s", month+1);
                };

                // Create calender object
                String date = String.format("%s-%s-%s", year, formatMonth, daysInMonth.get(j).split(" ")[1]);
                System.out.println(date + "inner ");
                VBox item = new CalenderItem(date, currentMonthAppointments).generateCard();
                GridPane.setConstraints( item, Integer.parseInt(daysInMonth.get(j).split(" ")[0]), weekNum);  
                calenderGrid.getChildren().add(item);
                
                // set calender back to start of month
                mainCalender.add(Calendar.MONTH, -1);
        
            };
            
        } else {
            
            // Fill calender
            for (int j = 0; j < daysInMonth.size(); j++) {
                
                if (Integer.parseInt(daysInMonth.get(j).split(" ")[0]) == 0) {
                    weekNum += 1;
                    weeksInCurrentMonth++;
                };
                
                String formatMonth;
            
                if (month < 10) {
                    formatMonth = String.format("0%s", month+1);
                } else {
                    formatMonth = String.format("%s", month+1);
                };
                
                // Create calender object
                String date = String.format("%s-%s-%s", year, formatMonth, daysInMonth.get(j).split(" ")[1]);
                System.out.println(date + "inner ");
                VBox item = new CalenderItem(date, currentMonthAppointments).generateCard();
                VBox emptyCal = new VBox(5);
                emptyCal.setMinHeight(75);
                emptyCal.setPadding(new javafx.geometry.Insets(0, 10, 0, 10));
                
                if (weekNum == weekIncr) {
                    GridPane.setConstraints( item, Integer.parseInt(daysInMonth.get(j).split(" ")[0]), weekNum);
                    calenderGrid.getChildren().add(item);
                } else {
                    GridPane.setConstraints( emptyCal, Integer.parseInt(daysInMonth.get(j).split(" ")[0]), weekNum);
                    calenderGrid.getChildren().add(emptyCal);
                };
                
            };
            
        };
        
        return 0;
       
    };
  
  
    // handles calander object and returns month name
    public String updateCalender(String action){
        
        // Set init values
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        
        // Month name
        mainCalender.set(year, month, 1);
        monthName = months[month];
        
        int weekControl = 100;
        
        switch(action) {
            case "nextMonth":
                currentWeekSelected = 0;
                mainCalender.add(Calendar.MONTH, 1);
                weekControl = 100;
                break;
            case "lastMonth":
                mainCalender.add(Calendar.MONTH, -1);
                weekControl = 100;
                break;
            case "nextWeek":
                currentWeekSelected = currentWeekSelected + 1;
                weekControl = currentWeekSelected + 1;
                
                break;
            case "lastWeek":
                currentWeekSelected = currentWeekSelected - 1;
                weekControl = currentWeekSelected + 1;
                
                break;
            case "initWeek":
                currentWeekSelected = 0;
                weekControl = 1;
                break;
            default:
            // code block
        }
        
        calenderGrid.getChildren().clear();
        year = mainCalender.get(Calendar.YEAR);
        month = mainCalender.get(Calendar.MONTH);
        monthName = months[month];
        currentMonthAppointments = apiDB.AppointmentByMonth(Integer.toString(year), Integer.toString(month+1));
        renderCalender(weekControl);
        
        return monthName;
        
    };
    
    
    public Scene generateCalender () {
        
        
        // month controls
        Label monthTitle = new Label(monthName);
        monthTitle.setMinWidth(250);
        monthTitle.setStyle("-fx-font: 24 arial;");
        
        // All buttons
        Button forwardMonthBtn = new Button("Next");
        forwardMonthBtn.setOnAction(e -> {
            
            if (calMode == "Week") {
                String month = updateCalender("nextWeek");
                int weekNum = currentWeekSelected + 1;
                monthTitle.setText(month + " Week: " + weekNum);
            } else {
                monthTitle.setText(updateCalender("nextMonth"));
            }
            
        });
        forwardMonthBtn.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        forwardMonthBtn.setMinWidth(200);
        
        Button backwardMonth = new Button("Last");
        backwardMonth.setOnAction(e -> {
            
            if (calMode == "Week") {
                String month = updateCalender("lastWeek");
                int weekNum = currentWeekSelected + 1;
                monthTitle.setText(month  + " Week: " + weekNum);
            } else {
                monthTitle.setText(updateCalender("lastMonth"));
            }
        });
        backwardMonth.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        backwardMonth.setMinWidth(200);
        
        Button backButton = new Button("Go Back");
        backButton.setOnAction(e -> {
            
           lastStage.setScene(lastScene);
           
        });
        backButton.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        backButton.setMinWidth(200);
        
        // Creating a ToggleGroup 
        ToggleGroup group = new ToggleGroup(); 
  
        // Creating new Toggle buttons. 
        ToggleButton weekButton = new ToggleButton("Week Mode"); 
        ToggleButton monthButton = new ToggleButton("Month Mode"); 
        
        weekButton.setMinWidth(100);
        weekButton.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        weekButton.setMinWidth(100);
        monthButton.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        
        weekButton.setToggleGroup(group); 
        monthButton.setToggleGroup(group); 
        
        weekButton.setUserData("Week");
        monthButton.setUserData("Month"); 
        
        monthButton.setSelected(true);
        
        weekButton.setOnAction(e -> {
            if (weekButton.isSelected()) {
                int weekNum = currentWeekSelected + 1;
                calMode = "Week";
                updateCalender("initWeek");
                monthTitle.setText(monthName + " Week: " + weekNum);
                System.out.println("Week Mode");
            };
        });
        
        monthButton.setOnAction(e -> {
            if (monthButton.isSelected()) {
                calMode = "Month";
                monthTitle.setText(monthName);
                System.out.println("Month Mode");
            };
        });
        
        header.getChildren().addAll(monthTitle, monthButton, weekButton, backButton, backwardMonth, forwardMonthBtn);
                       
        VBox root = new VBox(20);
        root.setFillWidth(true);
        
        calenderGrid.setMinHeight(600);
        calenderGrid.setMaxHeight(600);
        calenderGrid.setAlignment(Pos.TOP_CENTER);

        header.setPadding(new javafx.geometry.Insets(20, 0, 20, 20));
        header.setAlignment(Pos.BASELINE_RIGHT);
        root.setPadding(new javafx.geometry.Insets(20, 20, 20, 20));
        root.setAlignment(Pos.TOP_CENTER);
        renderCalender(100);

        root.getChildren().addAll(calenderGrid, header );
        Scene scene = new Scene(root, 1280, 720);
        
        return scene;
    };

}
