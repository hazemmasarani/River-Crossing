package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.ICrosser2;

public class Story1Controller {

	Stories_IRiverCrossingController mainCrossingController;
	Move move;
	ArrayList <PlayerImage> groupOfPlayers;
	SequentialTransition sequentialTransition;
	TranslateTransition[] Pseq;
	
	@FXML
	private ImageView farmer;
	@FXML
	private ImageView plant;
	@FXML
	private ImageView sheep;
	@FXML
	private ImageView fox;
	@FXML
	private ImageView boat;
	
    @FXML
    private Label success;
	
	PlayerImage farmerImage;
	PlayerImage plantImage;
	PlayerImage sheepImage;
	PlayerImage foxImage;
	
	public void farmer () {
		move.movePlayer(farmerImage);
	}
	
	public void plant () {
		move.movePlayer(plantImage);
	}
	
	public void sheep () {
		move.movePlayer(sheepImage);
	}
	
	public void fox () {
		move.movePlayer(foxImage);
	}
	
	public void sail () {
		if ( !mainCrossingController.canMove( move.getICrossersFromPlayers(groupOfPlayers) , move.isBoatLeft ) ) {
			return;
		}
		move.currentlySailing =true;
		this.farmer();
		this.plant();
		this.sheep();
		this.fox();
		move.sail(boat);
		move.currentlySailing =false;
		mainCrossingController.doMove( move.getICrossersFromPlayers(groupOfPlayers) , !move.isBoatLeft );
		
		if( mainCrossingController.state.currentRightBankCrossers.size() == 4 ) {
			success.setText("Congratulations!");
		} else {
			success.setText("");
		}
	}
	
	public void back (ActionEvent event) throws IOException {
		Parent userPage = FXMLLoader.load(getClass().getResource("/view/NewLoadGame.fxml"));
		Scene userScene = new Scene(userPage);
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(userScene);
		window.show();
	}
	
	public void save () throws ParserConfigurationException, SAXException, IOException, TransformerException {
		mainCrossingController.saveGame();
	}
	
	public void solve() throws InterruptedException {
		this.reset();
		move.currentlyAutoSolving = true;
    	sequentialTransition = new SequentialTransition();
		move.passSequentialTransition( sequentialTransition );
    	Pseq = new TranslateTransition[5];
		move.passParallelTransition( Pseq );
		ArrayList<ArrayList<ICrosser2>> transferredBoatRiders = mainCrossingController.solveGame();

		for( int i=0; i<transferredBoatRiders.size()  ;i++ ) {
			for( int j=0; j<transferredBoatRiders.get(i).size()  ;j++ ) {
				for( int k=0; k<4  ;k++ ) {
						if( transferredBoatRiders.get(i).get(j).getEatingRank() == groupOfPlayers.get(k).eatingRank  ) {
							move.movePlayer( groupOfPlayers.get(k) );
							break;
						}
				}
			}
			this.sail ();
			ParallelTransition parallelTransition = new ParallelTransition( Pseq[0],Pseq[1],Pseq[2],Pseq[3],Pseq[4] );
			sequentialTransition.getChildren().add( parallelTransition );
	    	Pseq = new TranslateTransition[5];
			move.passParallelTransition( Pseq );
			for( int j=0; j<transferredBoatRiders.get(i).size()  ;j++ ) {
				for( int k=0; k<4  ;k++ ) {
						if( transferredBoatRiders.get(i).get(j).getEatingRank() == groupOfPlayers.get(k).eatingRank  ) {
							move.movePlayer( groupOfPlayers.get(k) );
							break;
						}
				}
			}
		}
	    sequentialTransition.play();
		move.currentlyAutoSolving = false;
	}
	
	public void undo() {
		if( !mainCrossingController.canUndo() ) {
			return;
		}
		else {
			mainCrossingController.undo();
			ArrayList<ICrosser2> lastTransferredBoatRiders = mainCrossingController.state.transferredBoatRiders.get( mainCrossingController.state.currentSailCount );
			ArrayList<ICrosser2> lastOtherBankCrossers;
			if( !mainCrossingController.state.boatPosition.equals("Left") ) lastOtherBankCrossers = mainCrossingController.state.currentLeftBankCrossers;
			else lastOtherBankCrossers = mainCrossingController.state.currentRightBankCrossers;
			for( int i=0; i<lastTransferredBoatRiders.size()  ;i++ ) {
				for( int j=0; j<groupOfPlayers.size()  ;j++ ) {
					if( lastTransferredBoatRiders.get(i).getEatingRank() == groupOfPlayers.get(j).eatingRank  ) {
						move.moveLoadedPlayer(  groupOfPlayers.get(j) );
						break;
					}
				}
			}
			move.sail(boat);
			for( int i=0; i<lastOtherBankCrossers.size()  ;i++ ) {
				for( int j=0; j<groupOfPlayers.size()  ;j++ ) {
					if( lastOtherBankCrossers.get(i).getEatingRank() == groupOfPlayers.get(j).eatingRank  ) {
						if( groupOfPlayers.get(j).isOnBoat ) {
							move.moveLoadedPlayer(  groupOfPlayers.get(j) );
							groupOfPlayers.get(j).moveBoat();
						}
						break;
					}
				}
			}
		}
	}
	
	public void redo() {
		if( !mainCrossingController.canRedo() ) {
			return;
		}
		else {
			ArrayList<ICrosser2> nextTransferredBoatRiders = mainCrossingController.state.transferredBoatRiders.get( mainCrossingController.state.currentSailCount );
			ArrayList<ICrosser2> nextOtherBankCrossers;
			mainCrossingController.redo();
			if( !mainCrossingController.state.boatPosition.equals("Left") ) nextOtherBankCrossers = mainCrossingController.state.currentLeftBankCrossers;
			else nextOtherBankCrossers = mainCrossingController.state.currentRightBankCrossers;
			for( int i=0; i<nextTransferredBoatRiders.size()  ;i++ ) {
				for( int j=0; j<groupOfPlayers.size()  ;j++ ) {
					if( nextTransferredBoatRiders.get(i).getEatingRank() == groupOfPlayers.get(j).eatingRank  ) {
						move.moveLoadedPlayer(  groupOfPlayers.get(j) );
						break;
					}
				}
			}
			move.sail(boat);
			for( int i=0; i<nextOtherBankCrossers.size()  ;i++ ) {
				for( int j=0; j<groupOfPlayers.size()  ;j++ ) {
					if( nextOtherBankCrossers.get(i).getEatingRank() == groupOfPlayers.get(j).eatingRank  ) {
						if( groupOfPlayers.get(j).isOnBoat ) {
							move.moveLoadedPlayer(  groupOfPlayers.get(j) );
							groupOfPlayers.get(j).moveBoat();
						}
						break;
					}
				}
			}
		}
	}
	
	public void reset() {
		mainCrossingController.state.loadedPastSailsCount = 0;
		mainCrossingController.resetGame();
		this.initialize();
	}
	
	public void loadGame() {
		ArrayList<ICrosser2> rightBankCrossers = mainCrossingController.state.currentRightBankCrossers;
		for( int i=0; i<rightBankCrossers.size()  ;i++ ) {
			for( int j=0; j<groupOfPlayers.size()  ;j++ ) {
				if( rightBankCrossers.get(i).getEatingRank() == groupOfPlayers.get(j).eatingRank  ) {
					move.moveLoadedPlayer(  groupOfPlayers.get(j) );
					break;
				}
			}
		}
		if( !mainCrossingController.state.boatPosition.equals("Left") ) {
			move.sail(boat);
		}
	}

    @FXML
    void initialize() {
		mainCrossingController = Stories_IRiverCrossingController.getMainCrossingController();
    	move = new Move(1);
    	
		farmerImage = new PlayerImage( farmer,0,2,20 );
		plantImage = new PlayerImage( plant,1,-1,20 );
		sheepImage = new PlayerImage( sheep,2,0,20 );
		foxImage = new PlayerImage( fox,3,1,20 );
		
		groupOfPlayers = new ArrayList <PlayerImage>();
		groupOfPlayers.add( farmerImage );
		groupOfPlayers.add( plantImage );
		groupOfPlayers.add( sheepImage );
		groupOfPlayers.add( foxImage );
		
		for( int i=0; i<4 ;i++ ) {
			TranslateTransition transition= new TranslateTransition();
			transition.setDuration(Duration.seconds(0.0001));
			transition.setToX( move.LbankPos[0]+i*move.Trans );
			transition.setToY( move.LbankPos[1] );
			transition.setNode( groupOfPlayers.get(i).player );
			transition.play();
		}
		
		TranslateTransition transition= new TranslateTransition();
		transition.setDuration(Duration.seconds(0.0001));
		transition.setToX( 0 );
		transition.setToY( 0 );
		transition.setNode( boat );
		transition.play();
		
		if( mainCrossingController.state.loadedPastSailsCount > 0 ) loadGame();
	}
	
}