/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.distanceFinder.ui;
import edu.vanier.distanceFinder.controllers.PostalCodeController;
import edu.vanier.distanceFinder.models.PostalCode;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import static javafx.geometry.Pos.CENTER;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Mervin
 */
public class MainApp extends Application{
    
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
                PostalCodeForm pcf = new PostalCodeForm(primaryStage);
                pcf.showAndWait();
                
                fromPC = pcf.getFromPostalCode().getPostalCode();
                toPC = pcf.getToPostalCode().getPostalCode();
                 
                System.out.println(fromPC+ " "+toPC);

                pcf.close();
                
                if(pcc.distanceTo(fromPC, toPC)== -1){
                    showError();
                    try {
                        start(primaryStage);
                    } catch (Exception ex) {
                        Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    showConfirmation(pcc.distanceTo(fromPC, toPC), fromPC, toPC);
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
                showTable();
                nlf.close();

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
    
    public static void showConfirmation( double distance, String fromPC,String toPC ){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Box");
        alert.setHeaderText("Distance Has Been Calculated Successfuly!");
        alert.setResizable(false);
        alert.setContentText("Distance between "+ fromPC+ ", and "+toPC+ " is: "+ distance+" KM.");
        alert.showAndWait();
    }
    public static void showError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ALERT");
        alert.setHeaderText("Invalid Input!");
        alert.setResizable(false);
        alert.setContentText("Returning to main menu");
        alert.showAndWait();
    }
    
    public void showTable(){
               TableView tableView = new TableView();
               String TableColumn;
        
               TableColumn<Map, String> firstNameColumn = new TableColumn<>("firstName");
                firstNameColumn.setCellValueFactory(new MapValueFactory<>("firstName"));

                TableColumn<Map, String> lastNameColumn = new TableColumn<>("lastName");
                lastNameColumn.setCellValueFactory(new MapValueFactory<>("lastName"));

                tableView.getColumns().add(firstNameColumn);
                tableView.getColumns().add(lastNameColumn);

               
               HashMap<String,PostalCode> nearbyPCodes = pcc.nearbyLocations(fromPostalCode, range);
               if(nearbyPCodes== null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ALERT");
                alert.setHeaderText("Invalid Input!");
                alert.setResizable(false);
                alert.setContentText("Returning to main menu");
                alert.showAndWait();

               }else{
                   
               }
               Stage stage = new Stage();
               Scene scene2 = new Scene(tableView,300,300 );
               stage.setScene(scene2);
               stage.show();
               
    }
    
    public static void main(String[] args) {
        Application.launch(args);
    }
}
