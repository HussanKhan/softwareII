/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.ii.advanced.java.concepts.c195;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static jdk.nashorn.internal.objects.NativeMath.random;

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
        
        TableColumn<Appointment, String> start = new TableColumn<>("Start Customer Timezone");
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        
        TableColumn<Appointment, String> end = new TableColumn<>("End Customer Timezone");
        end.setCellValueFactory(new PropertyValueFactory<>("end"));       
        
        TableColumn<Appointment, String> type = new TableColumn<>("Type");
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        
        TableColumn<Appointment, String> title = new TableColumn<>("Title");
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        
        TableColumn<Appointment, Hyperlink> link = new TableColumn<>("Link");
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
            
            updateAppointment(selectedAppointment);
           
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
    
    public int addAppointment() {
                
        // set stage - window
        Stage window = new Stage();
        
        // Opens customer selection window
        selectCustomer();
        
        // close if customer not selected
        if ( selectedCustomer == null ) {
            window.close();
            return 0;
        };
        
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
        
        Label zone = new Label("Timezone/Time of Day");
        
        Label start = new Label("Date");
        Label startTime = new Label("Time");
        Label duration = new Label("Duration");
        
        // field inputs
        TextField customerIdInput = new TextField( selectedCustomer.getCustomerId() );
        TextField titleInput = new TextField();
        TextField descriptionInput = new TextField();
        TextField locationInput = new TextField( selectedCustomer.getCity() + ", " + selectedCustomer.getCountry() );
        TextField contactInput = new TextField( selectedCustomer.getPhone() );
        TextField typeInput = new TextField();
        
        // Time picker
        ObservableList<String> hour = 
        FXCollections.observableArrayList(
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10",
            "11",
            "12"
        );
        
        ObservableList<String> minute = 
        FXCollections.observableArrayList(
            "00",
            "10",
            "20",
            "30",
            "40",
            "50"
        );
        
        ObservableList<String> ampm = 
        FXCollections.observableArrayList(
            "AM",
            "PM"
        );
        
        ObservableList<String> durationOptions = 
        FXCollections.observableArrayList(
            "10 Minutes",
            "20 Minutes",
            "30 Minutes",
            "40 Minutes",
            "50 Minutes",
            "1 Hour",
            "2 Hours",
            "4 Hours",
            "5 Hours"
        );
        
        ObservableList<String> timeZones = FXCollections.observableArrayList();
        
        // Build timezones list
        String[] data = TimeZone.getAvailableIDs();
        
        for (String temp : data) {
            timeZones.add(temp);
        };
               
        // combo boxes
        ComboBox hourComboBoxStart = new ComboBox(hour);
        ComboBox minuteComboBoxStart = new ComboBox(minute);
        ComboBox ampmComboBox = new ComboBox(ampm);
        
        ComboBox customerTimeZone = new ComboBox(timeZones);
        
        ComboBox hourComboBoxEnd = new ComboBox(hour);
        ComboBox minuteComboBoxEnd = new ComboBox(minute);
        
        ComboBox durationComboBox = new ComboBox(durationOptions);
        
        // Make default timzezone selection based on customer address
        String customerCity = selectedCustomer.getCity();
        
        for (int i = 0; i < timeZones.size(); i++) {
            
            if (timeZones.get(i).toLowerCase().contains(customerCity.toLowerCase().replace(" ", "_"))) {
                customerTimeZone.getSelectionModel().select(i);
                break;
            };
      
        };
        
        // make default time of day PM
        ampmComboBox.getSelectionModel().select(1);
        
        // Make time menu
        HBox timeMenuStart = new HBox(5);
        HBox timeMenuEnd = new HBox(5);
        timeMenuStart.getChildren().addAll(hourComboBoxStart, minuteComboBoxStart);
        timeMenuEnd.getChildren().addAll(hourComboBoxEnd, minuteComboBoxEnd);
       
        // make url for user
        String importantValues = selectedCustomer.getCustomerId() + selectedCustomer.getCustomerName() + selectedCustomer.getCity() + selectedCustomer.getCountry() + selectedCustomer.getPhone();
        importantValues = importantValues.replaceAll("\\s+","");
        
        String cusUrl = "https://appoint.com/";
        
        int max = importantValues.length() - 1;
        
        for (int i = 0; i < 20; i++) {
            cusUrl += importantValues.charAt((int) ((Math.random() * ((max - 0) + 1)) + 0));
        };
        
//        System.out.println(LocalDate.now());
//        System.out.println(LocalTime.now());
//        System.out.println(LocalDateTime.now());
//        System.out.println(ZonedDateTime.now());
        
        TextField urlInput = new TextField( cusUrl );
        
        // Date picker
        DatePicker startDatePicker = new DatePicker();
        DatePicker endDatePicker = new DatePicker();
        
        // Add Button
        Button addButton = new Button("Add Appointment");
        addButton.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        addButton.setMinWidth(200);
                
        addButton.setOnAction(e -> {
            
            // CONVERT TIME TO 24-HOUR TIME
            String startData = startDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String[] dateArr = startData.split("-");
            String userHour = "";
           
            if ("PM".equals(ampmComboBox.getSelectionModel().getSelectedItem().toString())) {

                int tempHour = Integer.parseInt(hourComboBoxStart.getSelectionModel().getSelectedItem().toString());

                tempHour = tempHour + 12;

                userHour = Integer.toString(tempHour);

            } else {
                userHour = hourComboBoxStart.getSelectionModel().getSelectedItem().toString();
            };
            
            // CALCULATE END TIME
            LocalDateTime endTimeUser;
            
            if ( durationComboBox.getSelectionModel().getSelectedItem().toString().contains("Hour") ) {
            
                endTimeUser = LocalDateTime.of( 
                    Integer.parseInt(dateArr[0]) , 
                    Month.of(Integer.parseInt(dateArr[1])), 
                    Integer.parseInt(dateArr[2]), 
                    Integer.parseInt(userHour), 
                    Integer.parseInt(minuteComboBoxStart.getSelectionModel().getSelectedItem().toString())
                ).plusHours( Long.parseLong(durationComboBox.getSelectionModel().getSelectedItem().toString().split(" ")[0]) );
                
            } else { 
                endTimeUser = LocalDateTime.of( 
                    Integer.parseInt(dateArr[0]) , 
                    Month.of(Integer.parseInt(dateArr[1])), 
                    Integer.parseInt(dateArr[2]), 
                    Integer.parseInt(userHour), 
                    Integer.parseInt(minuteComboBoxStart.getSelectionModel().getSelectedItem().toString())
                ).plusMinutes( Long.parseLong(durationComboBox.getSelectionModel().getSelectedItem().toString().split(" ")[0]) );
                
            };
           
            // CONVETING END TIME TO UTC
            ZonedDateTime localUserTime = ZonedDateTime.of(endTimeUser, ZoneId.of(customerTimeZone.getSelectionModel().getSelectedItem().toString()) );
            localUserTime = localUserTime.withZoneSameInstant(ZoneOffset.UTC);
            String endTimeCalculated = localUserTime.toString().replace('T', ' ').replace('Z', ' ').trim();
            
            // CONVERTING START TIME TO UTC
            LocalDateTime startTimeUser = LocalDateTime.parse( String.format("%sT%s:%s:00", startData, userHour, minuteComboBoxStart.getSelectionModel().getSelectedItem().toString()) );
            ZonedDateTime localUserTimeStart = ZonedDateTime.of(startTimeUser, ZoneId.of(customerTimeZone.getSelectionModel().getSelectedItem().toString()) );
            localUserTimeStart = localUserTimeStart.withZoneSameInstant(ZoneOffset.UTC);
            String startTimeCalculated = localUserTimeStart.toString().replace('T', ' ').replace('Z', ' ').trim();

            apiDB.addAppointment(
                    customerIdInput.getText(),
                    titleInput.getText(),
                    descriptionInput.getText(),
                    locationInput.getText(),
                    contactInput.getText(),
                    typeInput.getText(),
                    urlInput.getText(),
                    startTimeCalculated,
                    endTimeCalculated,
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
        
        GridPane.setConstraints(zone, 0, 7);
        GridPane.setConstraints(customerTimeZone, 1, 7);
        GridPane.setConstraints(ampmComboBox, 2, 7);
        
        GridPane.setConstraints(start, 0, 8);   
        GridPane.setConstraints(startDatePicker, 1, 8);
        
        GridPane.setConstraints(startTime, 0, 9); 
        GridPane.setConstraints(timeMenuStart, 1, 9); 
        
        GridPane.setConstraints(duration, 0, 10);   
        GridPane.setConstraints(durationComboBox, 1, 10);
        
        GridPane.setConstraints(addButton, 1, 11);
        
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
            startTime,
            timeMenuStart,
            zone,
            duration,
            durationComboBox,
            customerTimeZone,
            ampmComboBox,
            customerIdInput,
            titleInput,
            descriptionInput,
            locationInput,
            contactInput,
            typeInput,
            urlInput,
            startDatePicker,
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
        
        // store empty
        selectedCustomer = null;
        
        // don;t allow user to click anything else until they deal with window
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add Appointment");
        window.setMinWidth(250); // 250px min width
        window.setScene(scene);
        window.showAndWait(); // special way to show, and wait for close to reurn to caller
        
        return 0;
    };
    
    
    public void updateAppointment(Appointment appointment) {
        
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
        
        // field inputs
        TextField customerIdInput = new TextField( appointment.getCustomerId() );
        TextField titleInput = new TextField( appointment.getTitle() );
        TextField descriptionInput = new TextField( appointment.getDescription() );
        TextField locationInput = new TextField( appointment.getLocation() );
        TextField contactInput = new TextField( appointment.getContact() );
        TextField typeInput = new TextField( appointment.getType() );
        Hyperlink urlInput = appointment.getUrl();
        TextField startInput = new TextField( appointment.getStart() );
        TextField endInput = new TextField( appointment.getEnd() );
        
        // Add Button
        Button updateButton = new Button("Update Appointment");
        updateButton.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        updateButton.setMinWidth(200);
        
        updateButton.setOnAction(e -> {
            
        apiDB.deleteAppointment(appointment.getId());
            
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
        
        GridPane.setConstraints(updateButton, 1, 9);
        
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
        updateButton
                
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
