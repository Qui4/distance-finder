/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.distanceFinder.ui;

import edu.vanier.distanceFinder.controllers.PostalCodeController;
import edu.vanier.distanceFinder.models.PostalCode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Mervin
 */
public class DistanceToForm extends Stage{
    private PostalCode fromPostalCode;
    private PostalCode toPostalCode;
    
    /**
     * constructor
     * @param mainWindow stage of the main class
     */
    public DistanceToForm(Stage mainWindow) {
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(mainWindow);
        
        windowProperties(mainWindow);
          
    }
    
    /**
     * Generates the ui for the Postal Code distance to form
     * @param mainWindow stage of the main class
     */
    public void windowProperties(Stage mainWindow){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 300, 275);
        mainWindow.setScene(scene);
        
        Text scenetitle = new Text("Enter two sets of 3 Characters (First 3 Digits of A Canadian Postal Code)");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("First set:");
        grid.add(userName, 0, 1);

        TextField fromPostalCodeTextField = new TextField();
        grid.add(fromPostalCodeTextField, 1, 1);

        Label pw = new Label("Second set:");
        grid.add(pw, 0, 2);

        TextField toPostalCodeTextField = new TextField();
        grid.add(toPostalCodeTextField, 1, 2);
        
        Button btn = new Button("Confirm");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);
        
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
           public void handle(ActionEvent e) {
                fromPostalCode = new PostalCode();
                fromPostalCode.setPostalCode(fromPostalCodeTextField.getText().strip().toUpperCase());
                
                toPostalCode = new PostalCode();
                toPostalCode.setPostalCode(toPostalCodeTextField.getText().strip().toUpperCase());
                
                
                mainWindow.hide();
                
            }
        });
        
        this.setScene(scene);
        this.setTitle("Postal Code Form");
    }

    /**
     * getter
     * @return user inputted postal code
     */
    public PostalCode getFromPostalCode() {
        return this.fromPostalCode;
    }

    /**
     * getter 
     * @return user inputted postal code 
     */
    public PostalCode getToPostalCode() {
        return this.toPostalCode;
    }
    
    
}
