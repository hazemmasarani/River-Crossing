package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;

public class HowToPlayController {

	Stories_IRiverCrossingController mainCrossingController;
    @FXML
    private Label instructions;

    @FXML
    void back(ActionEvent event) throws IOException {
		Parent userPage = FXMLLoader.load(getClass().getResource("/view/NewLoadGame.fxml"));
		Scene userScene = new Scene(userPage);
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(userScene);
		window.show();
    }

    @FXML
    void initialize() {
		mainCrossingController = Stories_IRiverCrossingController.getMainCrossingController();
    	String[] instructionsGot = mainCrossingController.mainStory.getInstructions();
    	for( int i=0; i<3 ;i++ ) {
    		instructions.setText( instructions.getText()+instructionsGot[i]+System.lineSeparator() );
    	}
    }
}
