/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.distanceFinder.ui;

import edu.vanier.distanceFinder.controllers.PostalCodeController;
import edu.vanier.distanceFinder.models.PostalCode;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import static javafx.geometry.Pos.CENTER;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Mervin
 */
public class MainApp extends Application {
    public String fromPC;
    public String toPC;
    public String fromPostalCode;
    public double range;
    PostalCodeController pcc = new PostalCodeController("src\\main\\resources\\zipcodes.csv");

    @Override
    public void start(Stage primaryStage) throws Exception {
        pcc.setPostalCodes(pcc.parse());

        VBox root = new VBox();
        root.setAlignment(CENTER);
        Scene scene = new Scene(root, 300, 300);
        //--> Step 3) Load the scene into stage (window)
        primaryStage.setScene(scene);

        Button btnOpenDistanceTo = new Button("Compute Distance");

        btnOpenDistanceTo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                DistanceToForm pcf = new DistanceToForm(primaryStage);
                pcf.showAndWait();

                fromPC = pcf.getFromPostalCode().getPostalCode();
                toPC = pcf.getToPostalCode().getPostalCode();

                System.out.println(fromPC + " " + toPC);

                pcf.close();

                if (pcc.distanceTo(fromPC, toPC) == -1) {
                    showError();
                    try {
                        start(primaryStage);
                    } catch (Exception ex) {
                        Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    showConfirmation(pcc.distanceTo(fromPC, toPC), fromPC, toPC);
                    try {
                        start(primaryStage);
                    } catch (Exception ex) {
                        Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                
            }
        });

        Button btnOpenNearbyLocations = new Button("Open Nearby location Calculator");

        btnOpenNearbyLocations.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                NearbyLocationsForm nlf = new NearbyLocationsForm(primaryStage, pcc);
                nlf.showAndWait();

                fromPostalCode = nlf.getFromPostalCode();
                range = nlf.getRange();
                
                if (pcc.getPostalCodes().get(fromPostalCode)==null) {
                    try {
                        showError();
                        start(primaryStage);
                    } catch (Exception ex) {
                        Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    showTable();
                    nlf.close();
                }
            }
        });

        root.getChildren().add(btnOpenDistanceTo);
        root.getChildren().add(btnOpenNearbyLocations);

        primaryStage.setTitle("Distance Finder");
        // Resize the stage so the size matches the scene
        primaryStage.sizeToScene();
        //--> Step 4) Show the window.
        primaryStage.show();
    }

    /**
     * shows the distance from a point to another with an information box
     * @param distance
     * @param fromPC
     * @param toPC
     */
    public static void showConfirmation(double distance, String fromPC, String toPC) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Distance Between");
        alert.setHeaderText("Distance Has Been Calculated Successfuly!");
        alert.setResizable(false);
        alert.setContentText("Distance between " + fromPC + ", and " + toPC + " is: " + distance + " KM.");
        alert.showAndWait();
    }

    /**
     * creates an alert box
     */
    public static void showError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ALERT");
        alert.setHeaderText("Invalid Input!");
        alert.setResizable(false);
        alert.setContentText("Returning to main menu");
        alert.showAndWait();
    }

    /**
     * displays a table of nearby locations 
     */
    public void showTable() {
        TableView<PostalCode> tableView = new TableView<>();
        //Id Column
        TableColumn<PostalCode, String> idColumn = new TableColumn<>("Id");
        idColumn.setMinWidth(200);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        //country Column
        TableColumn<PostalCode, String> countryColumn = new TableColumn<>("Country");
        countryColumn.setMinWidth(200);
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));

        //postalCode Column
        TableColumn<PostalCode, String> postalCodeColumn = new TableColumn<>("Postal Code");
        postalCodeColumn.setMinWidth(200);
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        //province Column
        TableColumn<PostalCode, String> provinceColumn = new TableColumn<>("Province");
        provinceColumn.setMinWidth(200);
        provinceColumn.setCellValueFactory(new PropertyValueFactory<>("province"));

        //Id Column
        TableColumn<PostalCode, String> latitudeColumn = new TableColumn<>("Latitude");
        latitudeColumn.setMinWidth(200);
        latitudeColumn.setCellValueFactory(new PropertyValueFactory<>("latitude"));

        //Id Column
        TableColumn<PostalCode, String> longitudeColumn = new TableColumn<>("Longitude");
        longitudeColumn.setMinWidth(200);
        longitudeColumn.setCellValueFactory(new PropertyValueFactory<>("longitude"));

        tableView.setItems(getNearbyLocations());
        tableView.getColumns().addAll(idColumn, postalCodeColumn, countryColumn, provinceColumn, latitudeColumn, longitudeColumn);

        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle("Computation");
        dialog.setHeaderText("Following the calculations. Here is your answer !");
        dialog.getDialogPane().setContent(tableView);
        dialog.showAndWait();

    }

    /**
     * turns the HashMap of nearby locations into a list 
     * @return an observable list
     */
    public ObservableList<PostalCode> getNearbyLocations() {
        ObservableList<PostalCode> nearbyLocations = FXCollections.observableArrayList();

        HashMap<String, PostalCode> nearbyPCodesHolder = pcc.nearbyLocations(fromPostalCode, range);
            for (HashMap.Entry<String, PostalCode> m : nearbyPCodesHolder.entrySet()) {
                nearbyLocations.add(m.getValue());
        }
        return nearbyLocations;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
