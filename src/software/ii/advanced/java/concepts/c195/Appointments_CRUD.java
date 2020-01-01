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
public class Appointments_CRUD {
    
    private Scene lastScene;
    private Stage lastStage;
    private SQLDriver_Appointment apiDB = new SQLDriver_Appointment();
    private String username;
    
    TableView<Appointment> appointmentTable;
    
    Appointment selectedAppointment;
    
    Customer selectedCustomer;
    
    public Appointments_CRUD(Scene prevScene, Stage mainStage, String username) {
        
        this.username = username;
        
        // Table Columns
        TableColumn<Appointment, String> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        TableColumn<Appointment, String> start = new TableColumn<>("Start");
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        
        TableColumn<Appointment, String> end = new TableColumn<>("End");
        end.setCellValueFactory(new PropertyValueFactory<>("end"));       
        
        TableColumn<Appointment, String> type = new TableColumn<>("Type");
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        
        TableColumn<Appointment, String> title = new TableColumn<>("Title");
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        
        TableColumn<Appointment, String> link = new TableColumn<>("Link");
        link.setCellValueFactory(new PropertyValueFactory<>("url"));
        
        // Init Table
        appointmentTable = new TableView<>();        
        appointmentTable.setItems(apiDB.getAllAppointments());
        appointmentTable.getColumns().addAll(id, start, end, type, title, link);
        appointmentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        appointmentTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                 selectedAppointment = appointmentTable.getSelectionModel().getSelectedItems().get(0);
                 System.out.println(appointmentTable.getSelectionModel().getSelectedItems().get(0).getTitle());
            }
        });
        
        if (prevScene != null) {
            lastScene = prevScene;
        };
        
        lastStage = mainStage;
    };
    
    // makes customer scene
    public Scene generateAppointmentCRUD () {
        
        VBox layout = new VBox();
        layout.setPadding(new javafx.geometry.Insets(20, 20, 20, 20));
        HBox buttonHolder = new HBox(7);
        buttonHolder.setPadding(new javafx.geometry.Insets(20, 0, 20, 20));
        buttonHolder.setAlignment(Pos.BASELINE_RIGHT);
        
        buttonHolder.getChildren().addAll(backButton(), addButton(), updateButton(), deleteButton());
        layout.getChildren().addAll(appointmentTable, buttonHolder);
                        
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
            
           addAppointment();
           
        });

        return addButton;
    };
    
    public Button updateButton() {

        Button updateButton = createButton("UPDATE");
        updateButton.setOnAction(e -> {
            
//           updateCustomer(selectedAppointment);
           
        });

        return updateButton;
    };
    
    public Button deleteButton() {

        Button deleteButton = createButton("DELETE");
        deleteButton.setOnAction(e -> {
            
             apiDB.deleteAppointment(selectedAppointment.getId());
             appointmentTable.setItems(apiDB.getAllAppointments());
             
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
    
    public void addAppointment() {
        // set stage - window
        Stage window = new Stage();
        
        // Layout
        GridPane grid = new GridPane(); // content all the way tot he edge of the screen
        grid.setPadding(new javafx.geometry.Insets(20, 20, 20, 20)); // border padding
        grid.setVgap(30); // spacing bewteen all objects
        grid.setHgap(30); // spacing bewteen all objects
        
        // field titles
        Label customerId = new Label("Customer ID");
        Label title = new Label("Title");
        Label description = new Label("Description");
        Label location = new Label("Location");
        Label contact = new Label("Contact");
        Label type = new Label("Type");
        Label url = new Label("URL");
        Label start = new Label("Start");
        Label end = new Label("End");
        
        // Opens customer selection window
        selectCustomer();
        
        // field inputs
        TextField customerIdInput = new TextField( selectedCustomer.getCustomerId() );
        TextField titleInput = new TextField();
        TextField descriptionInput = new TextField();
        TextField locationInput = new TextField( selectedCustomer.getCity() );
        TextField contactInput = new TextField( selectedCustomer.getPhone() );
        TextField typeInput = new TextField();
        TextField urlInput = new TextField();
        TextField startInput = new TextField();
        TextField endInput = new TextField();
        
        // Add Button
        Button addButton = new Button("Add Appointment");
        addButton.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        addButton.setMinWidth(200);
        
        addButton.setOnAction(e -> {
           
            
        apiDB.addAppointment(
                customerIdInput.getText(),
                titleInput.getText(),
                descriptionInput.getText(),
                locationInput.getText(),
                contactInput.getText(),
                typeInput.getText(),
                urlInput.getText(),
                "2019-12-30 12:00:00",
                "2019-12-30 01:00:00",
                username
        );
        
        appointmentTable.setItems(apiDB.getAllAppointments());
                     
        window.close();
        
        });
        
        // Mappings
        GridPane.setConstraints(customerId, 0, 0);
        GridPane.setConstraints(customerIdInput, 1, 0);
        
        GridPane.setConstraints(title, 0, 1);
        GridPane.setConstraints(titleInput, 1, 1);
        
        GridPane.setConstraints(description, 0, 2);
        GridPane.setConstraints(descriptionInput, 1, 2);
        
        GridPane.setConstraints(location, 0, 3);
        GridPane.setConstraints(locationInput, 1, 3);
        
        GridPane.setConstraints(contact, 0, 4);
        GridPane.setConstraints(contactInput, 1, 4);
        
        GridPane.setConstraints(type, 0, 5);
        GridPane.setConstraints(typeInput, 1, 5);
        
        GridPane.setConstraints(url, 0, 6);
        GridPane.setConstraints(urlInput, 1, 6);
        
        GridPane.setConstraints(start, 0, 7);   
        GridPane.setConstraints(startInput, 1, 7);       
        
        GridPane.setConstraints(end, 0, 8);   
        GridPane.setConstraints(endInput, 1, 8);       
        
        GridPane.setConstraints(addButton, 1, 9);
        
        // Add values to grid
        grid.getChildren().addAll(
        customerId,
        title,
        description,
        location,
        contact,
        type,
        url,
        start,
        end,
        customerIdInput,
        titleInput,
        descriptionInput,
        locationInput,
        contactInput,
        typeInput,
        urlInput,
        startInput,
        endInput,
        addButton
                
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
        window.setTitle("Add Appointment");
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
                   
//            apiDB.updateCustomer(
//                    customer.getCustomerId(),
//                    nameInput.getText(),
//                    activeState,
//                    addressInput.getText(),
//                    address2Input.getText(),
//                    postalInput.getText(),
//                    phoneInput.getText(),
//                    cityInput.getText(),
//                    countryInput.getText(),
//                    username
//            );
//            
//            customerTable.setItems(apiDB.getAllCustomers());
            
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
    
    
    // select customer
    public void selectCustomer() {
        
        SQLDriver_Customer apiDB = new SQLDriver_Customer();
        
        TableView<Customer> customerTable;
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
        // set stage - window
        Stage window = new Stage();
        
        Button selectButton = new Button("Select Customer");
        
        selectButton.setOnAction(e -> {
            window.close();
        });
        
        // layout - add objects
        VBox layout = new VBox(20);
        layout.getChildren().addAll(customerTable, selectButton);
        layout.setAlignment(Pos.CENTER); // CENTER EVERYTHING
        layout.setPadding(new javafx.geometry.Insets(20,20,20,20));
        
        // scene - add layout to scene
        Scene scene = new Scene(layout);

        // don;t allow user to click anything else until they deal with window
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Select Customer");
        window.setMinWidth(400); // 250px min width
        window.setScene(scene);
        window.showAndWait(); // special way to show, and wait for close to reurn to caller
    };
    
}
