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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author "Hussan Khan"
 */
public class Customer_CRUD {
    
    private Scene lastScene;
    private Stage lastStage;
    private SQLDriver apiDB = new SQLDriver();
    private String username;
    
    TableView<Customer> customerTable;
    
    Customer selectedCustomer;
    
    public Customer_CRUD(Scene prevScene, Stage mainStage, String username) {
        
        this.username = username;
        
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
        customerTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                 selectedCustomer = customerTable.getSelectionModel().getSelectedItems().get(0);
                 System.out.println(customerTable.getSelectionModel().getSelectedItems().get(0).getCustomerName());
            }
        });
        
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

        Button addButton = createButton("ADD");
        addButton.setOnAction(e -> {
            
           addCustomer();
           
        });

        return addButton;
    };
    
    public Button updateButton() {

        Button updateButton = createButton("UPDATE");
        updateButton.setOnAction(e -> {
            
           updateCustomer(selectedCustomer);
           
        });

        return updateButton;
    };
    
    public Button deleteButton() {

        Button deleteButton = createButton("DELETE");
        deleteButton.setOnAction(e -> {
            
           System.out.println("delete DATA CLICKED");
           
           apiDB.deleteCustomer( Integer.parseInt(selectedCustomer.getCustomerId()) );
           customerTable.setItems(apiDB.getAllCustomers());
           
        });
        
        return deleteButton;
    };
   
    public Button backButton() {

        Button backButton = createButton("BACK");
        backButton.setOnAction(e -> {
            
           lastStage.setScene(lastScene);
           
        });
        
        return backButton;
    };
    
    public void addCustomer() {
        // set stage - window
        Stage window = new Stage();
        
        // Layout
        GridPane grid = new GridPane(); // content all the way tot he edge of the screen
        grid.setPadding(new javafx.geometry.Insets(20, 20, 20, 20)); // border padding
        grid.setVgap(30); // spacing bewteen all objects
        grid.setHgap(30); // spacing bewteen all objects
        
        // field titles
        Label name = new Label("Name");
        Label address = new Label("Address");
        Label address2 = new Label("Address 2");
        Label city = new Label("City");
        Label postal = new Label("Postal");
        Label country = new Label("Country");
        Label phone = new Label("Phone");
        Label active = new Label("Customer State: ");
        
        // field inputs
        TextField nameInput = new TextField();
        TextField addressInput = new TextField();
        TextField address2Input = new TextField();
        TextField cityInput = new TextField();
        TextField postalInput = new TextField();
        TextField countryInput = new TextField();
        TextField phoneInput = new TextField();

        ToggleButton toggleActive = new ToggleButton("Active");
        toggleActive.setSelected(true);
        
        // Add Button
        Button addButton = new Button("Add Customer");
        addButton.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        addButton.setMinWidth(200);
        
        addButton.setOnAction(e -> {
            
            String activeState;
            
            if (toggleActive.selectedProperty().get()) {
                activeState = "1";
            } else {
                activeState = "0";
            };
            
            System.out.println(activeState);
                   
            apiDB.setCustomer(
                    nameInput.getText(),
                    activeState,
                    addressInput.getText(),
                    address2Input.getText(),
                    postalInput.getText(),
                    phoneInput.getText(),
                    cityInput.getText(),
                    countryInput.getText(),
                    username
            );
            
            customerTable.setItems(apiDB.getAllCustomers());
            
            window.close();
     
        });
        
        // Mappings
        GridPane.setConstraints(name, 0, 0);
        GridPane.setConstraints(nameInput, 1, 0);
        
        GridPane.setConstraints(address, 0, 1);
        GridPane.setConstraints(addressInput, 1, 1);
        
        GridPane.setConstraints(address2, 0, 2);
        GridPane.setConstraints(address2Input, 1, 2);
        
        GridPane.setConstraints(city, 0, 3);
        GridPane.setConstraints(cityInput, 1, 3);
        
        GridPane.setConstraints(postal, 0, 4);
        GridPane.setConstraints(postalInput, 1, 4);
        
        GridPane.setConstraints(country, 0, 5);
        GridPane.setConstraints(countryInput, 1, 5);
        
        GridPane.setConstraints(phone, 0, 6);
        GridPane.setConstraints(phoneInput, 1, 6);
        
        GridPane.setConstraints(active, 0, 7);   
        GridPane.setConstraints(toggleActive, 1, 7);       
        
        GridPane.setConstraints(addButton, 1, 8);
        
        // Add values to grid
        grid.getChildren().addAll(
                name, address, address2, city, postal, country, phone,
                nameInput, addressInput, address2Input, cityInput, postalInput, countryInput, phoneInput, addButton,
                toggleActive, active
        );
        
        grid.setAlignment( Pos.CENTER );
        
        // layout - add objects
        VBox layout = new VBox(20);
        layout.getChildren().addAll(grid);
        layout.setAlignment(Pos.CENTER); // CENTER EVERYTHING
        layout.setPadding(new javafx.geometry.Insets(20,20,20,20));
        
        // scene - add layout to scene
        Scene scene = new Scene(layout);

        // don;t allow user to click anything else until they deal with window
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add Customer");
        window.setMinWidth(250); // 250px min width
        window.setScene(scene);
        window.showAndWait(); // special way to show, and wait for close to reurn to caller
    };
    
    
    public void updateCustomer(Customer customer) {
        // set stage - window
        Stage window = new Stage();
        
        // Layout
        GridPane grid = new GridPane(); // content all the way tot he edge of the screen
        grid.setPadding(new javafx.geometry.Insets(20, 20, 20, 20)); // border padding
        grid.setVgap(30); // spacing bewteen all objects
        grid.setHgap(30); // spacing bewteen all objects
        
        // field titles
        Label name = new Label("Name");
        Label address = new Label("Address");
        Label address2 = new Label("Address 2");
        Label city = new Label("City");
        Label postal = new Label("Postal");
        Label country = new Label("Country");
        Label phone = new Label("Phone");
        Label active = new Label("Customer State: ");
        
        // field inputs
        TextField nameInput = new TextField( customer.getCustomerName() );
        TextField addressInput = new TextField( customer.getAddress() );
        TextField address2Input = new TextField( customer.getAddress2() );
        TextField cityInput = new TextField( customer.getCity() );
        TextField postalInput = new TextField( customer.getPostal() );
        TextField countryInput = new TextField( customer.getCountry() );
        TextField phoneInput = new TextField( customer.getPhone() );
        
        // Add Button
        Button updateButton = new Button("Update Customer");
        updateButton.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        updateButton.setMinWidth(200);
        
        ToggleButton toggleActive = new ToggleButton("Active");
        
        System.out.println(customer.getActive());
        
        if ("1".equals(customer.getActive())) {
            toggleActive.setSelected(true);
        } else {
            toggleActive.setSelected(false);
        };
        
        
        updateButton.setOnAction(e -> {
            
            String activeState;
            
            if (toggleActive.selectedProperty().get()) {
                activeState = "1";
            } else {
                activeState = "0";
            };
                   
            apiDB.updateCustomer(
                    customer.getCustomerId(),
                    nameInput.getText(),
                    activeState,
                    addressInput.getText(),
                    address2Input.getText(),
                    postalInput.getText(),
                    phoneInput.getText(),
                    cityInput.getText(),
                    countryInput.getText(),
                    username
            );
            
            customerTable.setItems(apiDB.getAllCustomers());
            
            window.close();
     
        });
        
        // Mappings
        GridPane.setConstraints(name, 0, 0);
        GridPane.setConstraints(nameInput, 1, 0);
        
        GridPane.setConstraints(address, 0, 1);
        GridPane.setConstraints(addressInput, 1, 1);
        
        GridPane.setConstraints(address2, 0, 2);
        GridPane.setConstraints(address2Input, 1, 2);
        
        GridPane.setConstraints(city, 0, 3);
        GridPane.setConstraints(cityInput, 1, 3);
        
        GridPane.setConstraints(postal, 0, 4);
        GridPane.setConstraints(postalInput, 1, 4);
        
        GridPane.setConstraints(country, 0, 5);
        GridPane.setConstraints(countryInput, 1, 5);
        
        GridPane.setConstraints(phone, 0, 6);
        GridPane.setConstraints(phoneInput, 1, 6);
        
        GridPane.setConstraints(active, 0, 7);   
        GridPane.setConstraints(toggleActive, 1, 7);       
        
        GridPane.setConstraints(updateButton, 1, 8);
        
        // Add values to grid
        grid.getChildren().addAll(
                name, address, address2, city, postal, country, phone,
                nameInput, addressInput, address2Input, cityInput, postalInput, countryInput, phoneInput, updateButton,
                active, toggleActive
        );
        
        grid.setAlignment( Pos.CENTER );
        
        // layout - add objects
        VBox layout = new VBox(20);
        layout.getChildren().addAll(grid);
        layout.setAlignment(Pos.CENTER); // CENTER EVERYTHING
        layout.setPadding(new javafx.geometry.Insets(20,20,20,20));
        
        // scene - add layout to scene
        Scene scene = new Scene(layout);

        // don;t allow user to click anything else until they deal with window
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Update Customer");
        window.setMinWidth(250); // 250px min width
        window.setScene(scene);
        window.showAndWait(); // special way to show, and wait for close to reurn to caller
    };
}
