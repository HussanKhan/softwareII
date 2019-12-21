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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author "Hussan Khan"
 */
public class Customer_CRUD {
    
    private Scene lastScene;
    private Stage lastStage;
    private SQLDriver apiDB = new SQLDriver();
    
    TableView<Customer> customerTable;
    
    public Customer_CRUD(Scene prevScene, Stage mainStage) {
        
        // Table Columns
        TableColumn<Customer, String> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        
        TableColumn<Customer, String> name = new TableColumn<>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        
        TableColumn<Customer, String> address = new TableColumn<>("Address");
        address.setCellValueFactory(new PropertyValueFactory<>("address"));       
        
        TableColumn<Customer, String> address2 = new TableColumn<>("Address 2");
        address2.setCellValueFactory(new PropertyValueFactory<>("address2"));
        
        TableColumn<Customer, String> city = new TableColumn<>("City");
        city.setCellValueFactory(new PropertyValueFactory<>("city"));
        
        TableColumn<Customer, String> postal = new TableColumn<>("Postal");
        postal.setCellValueFactory(new PropertyValueFactory<>("postal"));
        
        TableColumn<Customer, String> country = new TableColumn<>("Country");
        country.setCellValueFactory(new PropertyValueFactory<>("country"));
        
        TableColumn<Customer, String> phone = new TableColumn<>("Phone");
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        
        // Init Table
        customerTable = new TableView<>();
        customerTable.setItems(apiDB.getAllCustomers());
        customerTable.getColumns().addAll(id, name, address, address2, city, postal, country, phone);
        customerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        customerTable.setMaxHeight(150);
//        customerTable.setMinWidth(370);
        
        if (prevScene != null) {
            lastScene = prevScene;
        };
        
        lastStage = mainStage;
    };
    
    // makes customer scene
    public Scene generateCustomer () {
        
        VBox layout = new VBox();
        layout.setPadding(new javafx.geometry.Insets(20, 20, 20, 20));
        HBox buttonHolder = new HBox(7);
        buttonHolder.setPadding(new javafx.geometry.Insets(20, 0, 20, 20));
        buttonHolder.setAlignment(Pos.BASELINE_RIGHT);
        
        
//        // layout
//        GridPane grid = new GridPane(); // content all the way tot he edge of the screen
//        grid.setPadding(new javafx.geometry.Insets(20, 20, 20, 20)); // border padding
//        grid.setVgap(30); // spacing bewteen all objects
//        grid.setHgap(30); // spacing bewteen all objects
//        
//        Button addButton = addButton();
//        Button updateButton = updateButton();
//        Button deleteButton = deleteButton();
//        Button backButton = backButton();
//        
//        Label sceneTitle = new Label("Customer Options");
//        
//        GridPane.setConstraints( customerTable, 0, 0);  
//        GridPane.setConstraints( addButton, 0, 1);  
//        GridPane.setConstraints( updateButton, 0, 2);  
//        GridPane.setConstraints( deleteButton, 0, 3);  
//        GridPane.setConstraints( backButton, 0, 4);  
//        
//        // Add values to grid
//        grid.getChildren().addAll(
//                customerTable,
//                addButton,
//                updateButton,
//                deleteButton,
//                backButton,
//                sceneTitle
//        );
//        
//        grid.setAlignment( Pos.CENTER );
        
        buttonHolder.getChildren().addAll(backButton(), addButton(), updateButton(), deleteButton());
        layout.getChildren().addAll(customerTable, buttonHolder);
        
                
        StackPane root = new StackPane();
        root.getChildren().addAll(layout);
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
