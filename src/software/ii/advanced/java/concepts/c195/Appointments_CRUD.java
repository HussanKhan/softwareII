/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.ii.advanced.java.concepts.c195;

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
public class Appointments_CRUD {
    
        
    private Scene lastScene;
    private Stage lastStage;
    
    public Appointments_CRUD(Scene prevScene, Stage mainStage) {
        
        if (prevScene != null) {
            lastScene = prevScene;
        };
        
        lastStage = mainStage;
    };
    
    
    public Scene generateAppointmentCRUD () {
        
        // layout
        GridPane grid = new GridPane(); // content all the way tot he edge of the screen
        grid.setPadding(new javafx.geometry.Insets(20, 20, 20, 20)); // border padding
        grid.setVgap(30); // spacing bewteen all objects
        grid.setHgap(30); // spacing bewteen all objects
        
        Button addButton = addButton();
        Button updateButton = updateButton();
        Button deleteButton = deleteButton();
        Button backButton = backButton();
        Label sceneTitle = new Label("Appointment Options");
        
        GridPane.setConstraints( sceneTitle, 0, 0);  
        GridPane.setConstraints( addButton, 0, 1);  
        GridPane.setConstraints( updateButton, 0, 2);  
        GridPane.setConstraints( deleteButton, 0, 3);  
        GridPane.setConstraints( backButton, 0, 4);  
        
        // Add values to grid
        grid.getChildren().addAll(
                addButton,
                updateButton,
                deleteButton,
                backButton,
                sceneTitle
        );
        
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
