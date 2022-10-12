/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.distanceFinder.ui;

import edu.vanier.distanceFinder.controllers.PostalCodeController;
import edu.vanier.distanceFinder.models.PostalCode;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Mervin
 */
public class NearbyLocationsForm extends Stage{
    PostalCodeController pcc;
    String fromPostalCode;
    Double range;
    
     public NearbyLocationsForm(Stage mainWindow, PostalCodeController pcc) {
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(mainWindow);
        this.pcc = pcc;
        
        windowProperties(mainWindow);      
    }

    private void windowProperties(Stage mainWindow) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 700, 400);
        mainWindow.setScene(scene);
        
        Text scenetitle = new Text("Enter a set of 3 Characters (First 3 Digits of A Canadian Postal Code)\n              and the range (radius in km)");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label postalCodeInput = new Label("Your Input:");
        grid.add(postalCodeInput, 0, 1);

        TextField postalCodeInputTextField = new TextField();
        grid.add(postalCodeInputTextField, 1, 1);
        
        Label rangeLabel = new Label("Range set:");
        grid.add(rangeLabel, 0, 2);

        TextField rangeTextField = new TextField();
        grid.add(rangeTextField, 1, 2);


        Button btn = new Button("Find Locations");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);
        
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
           public void handle(ActionEvent e) {
               try{
               fromPostalCode = postalCodeInputTextField.getText().strip().toUpperCase();
               range = Double.parseDouble(rangeTextField.getText().strip().toUpperCase());
               }
               catch(NullPointerException|NumberFormatException ex){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ALERT");
                    alert.setHeaderText("Invalid Input!");
                    alert.setResizable(false);
                    alert.setContentText("Returning to main menu");
                    alert.showAndWait();
                    
                }
               mainWindow.hide();  
            }
        });
        


   
    mainWindow.setScene(scene);
    this.setScene(scene);
    this.setTitle("Nearby Location Form");
    
    

    }

    public String getFromPostalCode() {
        return fromPostalCode;
    }


    public Double getRange() {
        if (this.range==null){
            return -1.;
        }else{
            return range;    
        }
        
    }
    
    
}
