package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import main.Main;

public class ChooseToLoadController implements Initializable {

	Stories_IRiverCrossingController mainCrossingController;
	 @FXML
	    private ComboBox<String> loadList;
	 
	public void back (ActionEvent event) throws IOException {
		Parent userPage = FXMLLoader.load(getClass().getResource("/view/NewLoadGame.fxml"));
		Scene userScene = new Scene(userPage);
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(userScene);
		window.show();
	}
	
	public void loadGame  (ActionEvent event) throws IOException, ParserConfigurationException, SAXException, TransformerException {
		int i = loadList.getSelectionModel().getSelectedIndex();
		mainCrossingController.loadProfileId = i+1;
		mainCrossingController.loadGame();
		
		if(mainCrossingController.state.whichStory == 1) {
			Parent userPage = FXMLLoader.load(getClass().getResource("/view/Story1.fxml"));
			Scene userScene = new Scene(userPage);
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			window.setScene(userScene);
			window.show();
		}else {
			Parent userPage = FXMLLoader.load(getClass().getResource("/view/Story2.fxml"));
			Scene userScene = new Scene(userPage);
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			window.setScene(userScene);
			window.show();
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mainCrossingController = Stories_IRiverCrossingController.getMainCrossingController();
		ArrayList<String> loadedGamesList = new ArrayList<String>();
		try {
			mainCrossingController.loadGamesList();
		} catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
			e.printStackTrace();
		}
		loadedGamesList = mainCrossingController.loadedGamesList;
		ObservableList<String> myList = FXCollections.observableArrayList();
		for( int i=0; i<loadedGamesList.size() ;i++) myList.add( loadedGamesList.get(i) );
		loadList.setItems(myList);
	}
}
