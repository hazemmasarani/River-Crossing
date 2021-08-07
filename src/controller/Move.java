package controller;

import java.util.ArrayList;
import model.*;
import crossersFactory.ICrosserFactory;
import main.*;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.Carni;
import model.Farmer;
import model.Herbi;
import model.ICrosser2;
import model.Plant;

public class Move {
	
	int[] RbankPos = {584,152};
	int[] LbankPos = {0,152};
	int[] LboatPos = {221,224};
	int[] RboatPos = {454,224};
	int Trans = 60;
	int PosCount;
	boolean currentlySailing;
	boolean currentlyAutoSolving;
	boolean[] RbankTaken = {false,false,false,false,false};
	boolean[] LbankTaken = {true,true,true,true,true};
	boolean[] boatTaken = {false,false};
	SequentialTransition sequentialTransition;
	TranslateTransition[] Pseq;
	int PseqCounter;
	
	boolean isBoatLeft;
	
	public Move (int whichStory) {
		currentlySailing = false;
		currentlyAutoSolving = false;
		isBoatLeft = true;
		if ( whichStory==1 ) PosCount=4;
		else PosCount=5;
	}
	
	public int getFreeBoatPos(boolean[] ObjTaken) {
		for( int i=0; i<2 ;i++ ) if ( !ObjTaken[i] ) return i;
		return -1;
	}
	
	public int getFreeBankPos(boolean[] ObjTaken) {
		for( int i=0; i<PosCount ;i++ ) if ( !ObjTaken[i] ) return i;
		return -1;
	}
	
	public void movePlayer(PlayerImage playerImage ) {
		TranslateTransition transition= new TranslateTransition();
		transition.setDuration(Duration.seconds(0.5));
		if( currentlySailing ) {
			if ( playerImage.isOnBoat ) {
				int FreePos = playerImage.position;
				if( isBoatLeft ) {
					transition.setToX( RboatPos[0]+FreePos*Trans );
				}
				else transition.setToX( LboatPos[0]+FreePos*Trans );
				playerImage.moveBoat();
			}
		}else if( isBoatLeft && playerImage.isInLeft ) {
			if ( playerImage.isOnBoat ) {
				int FreePos = getFreeBankPos( LbankTaken );
				if ( FreePos == -1 ) return;
				transition.setToX( LbankPos[0]+FreePos*Trans );
				transition.setToY( LbankPos[1] );
				boatTaken[ playerImage.position ] = false;
				playerImage.position = FreePos;
				LbankTaken[ playerImage.position ] = true;
			}else{
				int FreePos = getFreeBoatPos( boatTaken );
				if ( FreePos == -1 ) return;
				transition.setToX( LboatPos[0]+FreePos*Trans );
				transition.setToY( LboatPos[1] );
				LbankTaken[ playerImage.position ] = false;
				playerImage.position = FreePos;
				boatTaken[ playerImage.position ] = true;
			}
			playerImage.movePlayer();
		}else if( !isBoatLeft && !playerImage.isInLeft ) {
			if ( playerImage.isOnBoat ) {
				int FreePos = getFreeBankPos( RbankTaken );
				if ( FreePos == -1 ) return;
				transition.setToX( RbankPos[0]+FreePos*Trans );
				transition.setToY( RbankPos[1] );
				boatTaken[ playerImage.position ] = false;
				playerImage.position = FreePos;
				RbankTaken[ playerImage.position ] = true;
			}else{
				int FreePos = getFreeBoatPos( boatTaken );
				if ( FreePos == -1 ) return;
				transition.setToX( RboatPos[0]+FreePos*Trans );
				transition.setToY( RboatPos[1] );
				RbankTaken[ playerImage.position ] = false;
				playerImage.position = FreePos;
				boatTaken[ playerImage.position ] = true;
			}
			playerImage.movePlayer();
		}
		transition.setNode( playerImage.player );
		if ( !currentlyAutoSolving ) transition.play();
		else {
			if ( !currentlySailing ) {
				sequentialTransition.getChildren().add( transition );
			}else {
				Pseq[PseqCounter] = transition;
				PseqCounter++;
			}
		}
	}
	
	public void moveLoadedPlayer(PlayerImage playerImage ) {
		TranslateTransition transition= new TranslateTransition();
		transition.setDuration(Duration.seconds(0.0001));
		
		int FreePos;
		if ( isBoatLeft ) {
			FreePos = getFreeBankPos(RbankTaken);
			if( playerImage.isOnBoat ) {
				boatTaken[ playerImage.position ] = false;
				playerImage.isOnBoat = false;
			}else {
				LbankTaken[ playerImage.position ] = false;
			}
			RbankTaken[ FreePos ] = true;
			transition.setToX( RbankPos[0]+FreePos*Trans );
			transition.setToY( RbankPos[1] );
		}else {
			FreePos = getFreeBankPos(LbankTaken);
			if( playerImage.isOnBoat ) {
				boatTaken[ playerImage.position ] = false;
				playerImage.isOnBoat = false;
			}else {
				RbankTaken[ playerImage.position ] = false;
			}
			LbankTaken[ FreePos ] = true;
			transition.setToX( LbankPos[0]+FreePos*Trans );
			transition.setToY( LbankPos[1] );
		}
		playerImage.position = FreePos;
		
		playerImage.moveBoat();
		transition.setNode( playerImage.player );
		transition.play();
	}
	
	public ArrayList <ICrosser2> getICrossersFromPlayers( ArrayList <PlayerImage> groupOfPlayers ) {
		ICrosser2 tempCrosser;
		ArrayList <ICrosser2> crossers = new ArrayList<ICrosser2>();
		PlayerImage tempPlayerImage;
		ICrosserFactory crossersFactory = new ICrosserFactory();
		
		for( int i=0; i<groupOfPlayers.size() ;i++ ) {
			tempPlayerImage = groupOfPlayers.get(i);
			if ( ! tempPlayerImage.isOnBoat ) continue;
			if ( tempPlayerImage.eatingRank == 2 ) {
				tempCrosser = crossersFactory.createICrosser("farmer");
				tempCrosser.setWeight(tempPlayerImage.weight);
			}
			else if ( tempPlayerImage.eatingRank == 1 ) tempCrosser = crossersFactory.createICrosser("carni");
			else if ( tempPlayerImage.eatingRank == 0 ) {
				tempCrosser = crossersFactory.createICrosser("herbi");
				tempCrosser.setWeight(tempPlayerImage.weight);
			}
			else tempCrosser = crossersFactory.createICrosser("plant");
			crossers.add( tempCrosser );
		}
		return crossers;
	}
	
	public void sail (ImageView boat) {
		TranslateTransition transition= new TranslateTransition();
		transition.setDuration(Duration.seconds(0.5));
		if ( isBoatLeft ) {
			transition.setToX( 230 );
			isBoatLeft = false;
		}else{
			transition.setToX( 0 );
			isBoatLeft = true;
		}
		transition.setNode( boat );
		if ( !currentlyAutoSolving ) transition.play();
		else {
			Pseq[PseqCounter] = transition;
			PseqCounter++;
		}
	}
	
	public void passSequentialTransition( SequentialTransition sequentialTransition ) {
		this.sequentialTransition = sequentialTransition;
	}
	
	public void passParallelTransition( TranslateTransition[] Pseq ) {
		PseqCounter =0;
		this.Pseq = Pseq;
	}
}
