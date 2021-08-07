package controller;

import java.io.IOException;
import main.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NewLoadGameController {

	Stories_IRiverCrossingController mainCrossingController;
	
	public void story (ActionEvent event) throws IOException {
		if(mainCrossingController.state.whichStory == 1) {
			Story1_ICrossingStrategy gameStrategy = new Story1_ICrossingStrategy();
			mainCrossingController.newGame( gameStrategy );
			
			Parent userPage = FXMLLoader.load(getClass().getResource("/view/Story1.fxml"));
			Scene userScene = new Scene(userPage);
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			window.setScene(userScene);
			window.show();
		}else {
			Story2_ICrossingStrategy gameStrategy = new Story2_ICrossingStrategy();
			mainCrossingController.newGame( gameStrategy );
			
			Parent userPage = FXMLLoader.load(getClass().getResource("/view/Story2.fxml"));
			Scene userScene = new Scene(userPage);
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			window.setScene(userScene);
			window.show();
		}
	}
	
	public void howToPlay(ActionEvent event) throws IOException {
		Parent userPage = FXMLLoader.load(getClass().getResource("/view/HowToPlay.fxml"));
		Scene userScene = new Scene(userPage);
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(userScene);
		window.show();
	}
	
	public void loadGame (ActionEvent event) throws IOException {
		Parent userPage = FXMLLoader.load(getClass().getResource("/view/ChooseToLoad.fxml"));
		Scene userScene = new Scene(userPage);
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(userScene);
		window.show();
	}
	
	public void back (ActionEvent event) throws IOException {
		Parent userPage = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
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
