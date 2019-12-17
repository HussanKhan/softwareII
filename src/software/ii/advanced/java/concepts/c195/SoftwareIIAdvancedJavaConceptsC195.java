/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.ii.advanced.java.concepts.c195;

import javafx.scene.paint.Color;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author "Hussan Khan"
 */
public class SoftwareIIAdvancedJavaConceptsC195 extends Application {
    
    private Locale locale = Locale.getDefault();
    private SQLDriver apiDB = new SQLDriver();
    
    private String langCode = "es";
    private String countryCode = "US";
    
    @Override
    public void start(Stage primaryStage) {
        
        langCode = new LanguageWindow().displayLangWindow();
        
        // customer data
        ObservableList<Customer> temp = apiDB.getAllCustomers();

        // HEADER
        Label sceneHeader = new Label( getLangKey(langCode, countryCode, "promptCat") );
        sceneHeader.setStyle("-fx-font-weight: bold");
        Label scenePrompt = new Label( getLangKey(langCode, countryCode, "prompt") );
        
        GridPane grid = new GridPane(); // content all the way tot he edge of the screen
        grid.setPadding(new javafx.geometry.Insets(20, 20, 20, 20)); // border padding
        grid.setVgap(30); // spacing bewteen all objects
        grid.setHgap(30); // spacing bewteen all objects
        
        // Username Input
        Label username = new Label( getLangKey(langCode, countryCode, "username") + ": ");
        GridPane.setConstraints(username, 0, 1); // COLUMN X ROW CONFIG, Y, X
        TextField userInput = new TextField();
        userInput.setPromptText( getLangKey(langCode, countryCode, "username") );
        GridPane.setConstraints(userInput, 1, 1);
        
        // Password Input
        Label password = new Label( getLangKey(langCode, countryCode, "password") + ": ");
        GridPane.setConstraints(password, 0, 2); // COLUMN X ROW CONFIG, Y, X
        TextField passwordInput = new TextField();
        passwordInput.setPromptText( getLangKey(langCode, countryCode, "password") );
        GridPane.setConstraints(passwordInput, 1, 2);
        
        // Location
        System.out.println(locale.getDisplayCountry());
        System.out.println(locale.getDisplayLanguage()); 
        
        Label country = new Label( getLangKey(langCode, countryCode, "country") + ": ");
        GridPane.setConstraints(country, 0, 3);
        Label countryName = new Label(locale.getDisplayCountry());
        GridPane.setConstraints(countryName, 1, 3);
        
        Label language = new Label( getLangKey(langCode, countryCode, "language") + ": ");
        GridPane.setConstraints(language, 0, 4);
        Label languageName = new Label(locale.getDisplayLanguage());
        GridPane.setConstraints(languageName, 1, 4);
        
        // ERROR
        Label errorCat = new Label("");
        errorCat.setStyle("-fx-font-weight: bold");
        errorCat.setStyle("-fx-font: 13 arial;");
        errorCat.setTextFill(Color.web("#ff0000", 0.8));
        GridPane.setConstraints(errorCat, 0, 5);
        
        Label errorMessage = new Label("");
        errorMessage.setStyle("-fx-font-weight: bold");
        errorMessage.setStyle("-fx-font: 13 arial;");
        errorMessage.setTextFill(Color.web("#ff0000", 0.8));
        GridPane.setConstraints(errorMessage, 1, 5);
        
        // Login button
        Button loginButton = new Button( getLangKey(langCode, countryCode, "promptCat") );
        loginButton.setOnAction(e -> {
            System.out.println(userInput.getText());
            System.out.println(passwordInput.getText());
            
            if (apiDB.login(userInput.getText(), passwordInput.getText()) == 0) {
                errorCat.setText( getLangKey(langCode, countryCode, "errorCat") + ": ");
                errorMessage.setText( getLangKey(langCode, countryCode, "errorMsg") );
            }
            
            primaryStage.setScene( new NAV_SCENE(null, primaryStage).generateNavScene() );
            
            
        });
        
        loginButton.setMinWidth(200);
        loginButton.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        GridPane.setConstraints( loginButton, 1, 6);
        
        GridPane.setConstraints(sceneHeader, 0, 0);
        sceneHeader.setStyle("-fx-font: 32 arial;");
        GridPane.setConstraints(scenePrompt, 1, 0);
        
        
        // Add values to grid
        grid.getChildren().addAll(
                sceneHeader,
                scenePrompt,
                username,
                userInput,
                password,
                passwordInput,
                country,
                countryName,
                language,
                languageName,
                errorCat,
                errorMessage,
                loginButton
        );
        
        // Align layout
        grid.setAlignment( Pos.CENTER );
        
        StackPane root = new StackPane();
        root.getChildren().addAll(grid);
        
        
        Scene scene = new Scene(root, 1280, 720);
        
        primaryStage.setTitle( getLangKey(langCode, countryCode, "promptCat") );
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public String getLangKey(String lang, String countryCode, String key) {
        Locale.setDefault(new Locale(lang, countryCode));
        
        ResourceBundle bundle = ResourceBundle.getBundle("software.ii.advanced.java.concepts.c195/MessageBundle", Locale.getDefault());
        
        return bundle.getString(key);
    };

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
