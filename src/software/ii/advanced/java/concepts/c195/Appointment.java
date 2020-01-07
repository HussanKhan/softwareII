/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.ii.advanced.java.concepts.c195;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author "Hussan Khan"
 */
public class Appointment {
    
    private String id;
    private String customerId;
    private String userId;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private Hyperlink url = new Hyperlink();
    private String start;
    private String end;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Hyperlink getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url.setText(url);
        this.url.setOnAction( e -> {
            customerDetails();
        });
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
    
    // Customer information
    public void customerDetails() {
        
        SQLDriver_Customer apiDB = new SQLDriver_Customer();
        
        Customer customer = apiDB.getCustomer(this.customerId);
        
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
        
        // field inputs
        Label nameInput = new Label( customer.getCustomerName() );
        Label addressInput = new Label( customer.getAddress() );
        Label address2Input = new Label( customer.getAddress2() );
        Label cityInput = new Label( customer.getCity() );
        Label postalInput = new Label( customer.getPostal() );
        Label countryInput = new Label( customer.getCountry() );
        Label phoneInput = new Label( customer.getPhone() );
        
        nameInput.setStyle("-fx-font-weight: bold");
        addressInput.setStyle("-fx-font-weight: bold");
        address2Input.setStyle("-fx-font-weight: bold");
        cityInput.setStyle("-fx-font-weight: bold");
        postalInput.setStyle("-fx-font-weight: bold");
        countryInput.setStyle("-fx-font-weight: bold");
        phoneInput.setStyle("-fx-font-weight: bold");
               
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
        
        // Add values to grid
        grid.getChildren().addAll(
                name, address, address2, city, postal, country, phone,
                nameInput, addressInput, address2Input, cityInput, postalInput, countryInput, phoneInput
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
        window.setTitle("Customer Record");
        window.setMinWidth(250); // 250px min width
        window.setScene(scene);
        window.showAndWait(); // special way to show, and wait for close to reurn to caller
    };
    
    
    
}
