/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.ii.advanced.java.concepts.c195;

import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author "Hussan Khan"
 */
public class Calender_CRUD {
    
    private Scene lastScene;
    private Stage lastStage;
    private String username;
    
    public Calender_CRUD(Scene prevScene, Stage mainStage, String usernam) {
        
        if (prevScene != null) {
            lastScene = prevScene;
        };
        this.username = username;
        lastStage = mainStage;

    };
    
    
    public Scene generateCalender () {
        
        // layout
        GridPane grid = new GridPane(); // content all the way tot he edge of the screen
        grid.setPadding(new javafx.geometry.Insets(20, 20, 20, 20)); // border padding
        grid.setVgap(30); // spacing bewteen all objects
        grid.setHgap(30); // spacing bewteen all objects
        
        Button addButton = addButton();
        Button updateButton = updateButton();
        Button deleteButton = deleteButton();
        Button backButton = backButton();
        
        Label sceneTitle = new Label("Calender Options");
        
        // CREATE CALENDER FOR CURRENT MONTH
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1); 
        int myMonth=cal.get(Calendar.MONTH);
        
        ObservableList<String> daysInMonth = FXCollections.observableArrayList();
        
        int numOfWeeks = 1;
        
        while (myMonth==cal.get(Calendar.MONTH)) {
            System.out.println(cal.getTime());
            System.out.println();
            
            daysInMonth.add( String.format("%s %s", cal.getTime().getDay(), cal.getTime().toString().split(" ")[2] ));
            
            if ( cal.getTime().getDay() == 0) {
                numOfWeeks += 1;
            };
            
            cal.add(Calendar.DAY_OF_MONTH, 1);
        };
        
        int weekNum = 0;
        for (int j = 0; j < daysInMonth.size(); j++) {
                
            if (Integer.parseInt(daysInMonth.get(j).split(" ")[0]) == 0) {
                weekNum += 1;
            };

            Label calenderValue = new Label( daysInMonth.get(j).split(" ")[1] );
            GridPane.setConstraints( calenderValue, Integer.parseInt(daysInMonth.get(j).split(" ")[0]), weekNum);  
            grid.getChildren().add(calenderValue);
        };
        
//        GridPane.setConstraints( sceneTitle, 0, 0);  
//        GridPane.setConstraints( addButton, 0, 1);  
//        GridPane.setConstraints( updateButton, 0, 2);  
//        GridPane.setConstraints( deleteButton, 0, 3);  
//        GridPane.setConstraints( backButton, 0, 4);  
//        
//        // Add values to grid
//        grid.getChildren().addAll(
//                addButton,
//                updateButton,
//                deleteButton,
//                backButton,
//                sceneTitle
//        );
        
        grid.setAlignment( Pos.CENTER );
                
        StackPane root = new StackPane();
        root.getChildren().addAll(grid);
        Scene scene = new Scene(root, 1280, 720);
        
        return scene;
    };
    
    public Button createButton(String title) {
        Button newButton = new Button( title );

        newButton.setMinWidth(200);
        newButton.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        
        return newButton;
    };
    
    public Button addButton() {

        Button addButton = createButton("add Data");
        addButton.setOnAction(e -> {
            
           System.out.println("add DATA CLICKED");
           
        });

        return addButton;
    };
    
    public Button updateButton() {

        Button updateButton = createButton("update Data");
        updateButton.setOnAction(e -> {
            
           System.out.println("update DATA CLICKED");
           
        });

        return updateButton;
    };
    
    public Button deleteButton() {

        Button deleteButton = createButton("delete Data");
        deleteButton.setOnAction(e -> {
            
           System.out.println("delete DATA CLICKED");
           
        });
        
        return deleteButton;
    };
   
     public Button backButton() {

        Button backButton = createButton("Go Back");
        backButton.setOnAction(e -> {
            
           lastStage.setScene(lastScene);
           
        });
        
        return backButton;
    };
}
