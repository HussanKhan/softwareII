/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.ii.advanced.java.concepts.c195;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author "Hussan Khan"
 */
public class ErrorPopup {

    public ErrorPopup() {};
    
    public void displayError(String errMsg, String title) {
        
        
        // set stage - window
        Stage window = new Stage();
        
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> {
            window.close();
        });
        closeButton.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        closeButton.setMinWidth(40);
        
        VBox container = new VBox(10);
        Label msg = new Label(errMsg);
        msg.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        
        container.setAlignment(Pos.CENTER);
        container.setPadding(new javafx.geometry.Insets(15, 15, 15, 15));
        container.getChildren().addAll(msg, closeButton);
        
        // scene - add layout to scene
        Scene scene = new Scene(container);

        // don;t allow user to click anything else until they deal with window
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250); // 250px min width
        window.setScene(scene);
        window.showAndWait(); // special way to show, and wait for close to reurn to caller
             
    };
}