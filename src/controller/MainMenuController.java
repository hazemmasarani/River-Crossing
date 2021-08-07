package controller;

import java.io.IOException;
import main.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController {

	Stories_IRiverCrossingController mainCrossingController;
	
	public void story1 (ActionEvent event) throws IOException {
		mainCrossingController.state.whichStory = 1;
		Story1_ICrossingStrategy gameStrategy = new Story1_ICrossingStrategy();
		mainCrossingController.newGame( gameStrategy );

		
		Parent userPage = FXMLLoader.load(getClass().getResource("/view/NewLoadGame.fxml"));
		Scene userScene = new Scene(userPage);
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(userScene);
		window.show();
	}
	
	public void story2 (ActionEvent event) throws IOException {
		mainCrossingController.state.whichStory = 2;
		Story2_ICrossingStrategy gameStrategy = new Story2_ICrossingStrategy();
		mainCrossingController.newGame( gameStrategy );
		
		Parent userPage = FXMLLoader.load(getClass().getResource("/view/NewLoadGame.fxml"));
		Scene userScene = new Scene(userPage);
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(userScene);
		window.show();
	}
	
    @FXML
    void initialize() {
		mainCrossingController = Stories_IRiverCrossingController.getMainCrossingController();
    }
}
