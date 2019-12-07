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
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Hussan Khan
 */
public class LanguageWindow {
    
    private String selectedLang = null;
    
    public LanguageWindow() {};
    
    public String displayLangWindow() {
        
        
        
        // set stage - window
        Stage window = new Stage();
        
        GridPane grid = new GridPane(); // content all the way tot he edge of the screen
        grid.setPadding(new javafx.geometry.Insets(20, 20, 20, 20)); // border padding
        grid.setVgap(30); // spacing bewteen all objects
        grid.setHgap(30); // spacing bewteen all objects
        
        
        // objects  
        Label label = new Label("Select Language (Seleccione el idioma): ");
        GridPane.setConstraints(label, 0, 0); // COLUMN X ROW CONFIG, Y, X
        
        RadioButton en_lang = new RadioButton("English (Inglés)");
        RadioButton es_lang = new RadioButton("Spanish (Español)");
        
        // RADIO BUTTON LISTENERS
        en_lang.setOnAction((ActionEvent e) -> {
            if (en_lang.isSelected()) {
                es_lang.setSelected(false);
                selectedLang = "en";
                window.close();
            }
        });
        
        es_lang.setOnAction(e -> {
            if (es_lang.isSelected()) {
                en_lang.setSelected(false);
                selectedLang = "es";
                window.close();
            }
        });
        
        
        GridPane.setConstraints(en_lang, 0, 1);
        GridPane.setConstraints(es_lang, 1, 1); 
        
        // Add values to grid
        grid.getChildren().addAll(
                label,
                en_lang,
                es_lang
        );
        
        // scene - add layout to scene
        Scene scene = new Scene(grid);

        // don;t allow user to click anything else until they deal with window
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Language Selection (Selección de idioma)");
        window.setMinWidth(250); // 250px min width
        window.setScene(scene);
        window.showAndWait(); // special way to show, and wait for close to reurn to caller
        
        return selectedLang;
        
    };
}
