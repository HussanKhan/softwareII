/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.ii.advanced.java.concepts.c195;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author "Hussan Khan"
 */
public class NAV_SCENE {
    
    
    private Scene lastScene;
    private Stage lastStage;
    
    public NAV_SCENE(Scene prevScene, Stage mainStage) {
        
        if (prevScene != null) {
            lastScene = prevScene;
        };
        lastStage = mainStage;
    };
    
    public Scene generateNavScene () {
        
        // layout
        GridPane grid = new GridPane(); // content all the way tot he edge of the screen
        grid.setPadding(new javafx.geometry.Insets(20, 20, 20, 20)); // border padding
        grid.setVgap(30); // spacing bewteen all objects
        grid.setHgap(30); // spacing bewteen all objects
        
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 1280, 720);
        
        Button customerButton = customerButton(scene);
        Button appointmentsButton = appointmentsButton(scene);
        Button calenderButton = calenderButton(scene);
        
        GridPane.setConstraints( customerButton, 0, 0);  
        GridPane.setConstraints( appointmentsButton, 0, 1);  
        GridPane.setConstraints( calenderButton, 0, 2);  
        
        // Add values to grid
        grid.getChildren().addAll(
                customerButton,
                appointmentsButton,
                calenderButton
        );
        
        grid.setAlignment( Pos.CENTER );
        root.getChildren().addAll(grid);        
        
        return scene;
    };
    
    public Button createButton(String title) {
        Button newButton = new Button( title );

        newButton.setMinWidth(200);
        newButton.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        
        return newButton;
    };
    
    public Button customerButton(Scene currentScene) {

        Button customerButton = createButton("Customer Data");
        customerButton.setOnAction(e -> {
            
           System.out.println("CUSTOMER DATA CLICKED");
           
           lastStage.setScene( new Customer_CRUD(currentScene, lastStage).generateCustomer());
           
        });

        return customerButton;
    };
    
    public Button appointmentsButton(Scene currentScene) {

        Button appointmentsButton = createButton("Appointments Data");
        appointmentsButton.setOnAction(e -> {
            
           lastStage.setScene( new Appointments_CRUD(currentScene, lastStage).generateAppointmentCRUD());
           
        });

        return appointmentsButton;
    };
    
    public Button calenderButton(Scene currentScene) {

        Button calenderButton = createButton("Calender Data");
        calenderButton.setOnAction(e -> {
            
           lastStage.setScene( new Calender_CRUD(currentScene, lastStage).generateCalender());
           
        });
        
        return calenderButton;
    };
        
}
