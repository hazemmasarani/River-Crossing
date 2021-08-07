package controller;

import remote.*;
import main.*;
import model.*;
import remote.RemoteControl;

import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import commands.*;

import javax.xml.parsers.*;
import java.io.*;
import java.text.SimpleDateFormat;

import controller.IRiverCrossingController2;

public class Stories_IRiverCrossingController implements IRiverCrossingController2 {

	private static Stories_IRiverCrossingController mainCrossingController;
	ICrossingStrategy2 mainStory;
	public State state;
	
	int loadProfileId;
	ArrayList<String> loadedGamesList;
	XmlParser xmlParser;
	RemoteControl remoteControl;
	Command command;
	
	private Stories_IRiverCrossingController() {
		state = new State();
		xmlParser = new XmlParser();
        remoteControl = new RemoteControl();
	}
	
    public static Stories_IRiverCrossingController getMainCrossingController() {
        if(mainCrossingController == null) {
            synchronized (Stories_IRiverCrossingController.class) {
                if(mainCrossingController == null) {
                	mainCrossingController = new Stories_IRiverCrossingController();
                }
            }
        }
        return mainCrossingController;
    }
	
	public void newGame(ICrossingStrategy gameStrategy) {
		//must pre set state.whichStory
		int tempwhichStory = state.whichStory;
		mainStory = (ICrossingStrategy2)gameStrategy;
		state = new State();
		state.whichStory = tempwhichStory;
		state.currentLeftBankCrossers = mainStory.getInitialCrossers();
	}
	
	public void resetGame() {
		if( state.whichStory == 1 ) mainStory = new Story1_ICrossingStrategy();
		else mainStory = new Story2_ICrossingStrategy();
		newGame( mainStory );
	}
	
	public String[] getInstructions() {
		return mainStory.getInstructions();
	}
	
	public ArrayList<ICrosser2> getCrossersOnRightBank(){
		return state.currentRightBankCrossers;
	}
	
	public ArrayList<ICrosser2> getCrossersOnLeftBank(){
		return state.currentLeftBankCrossers;
	}
	
	public boolean isBoatOnTheLeftBank() {
		if( state.boatPosition.equals("right") ) return false;
		else return true;
	}
	
	public int getNumberOfSails() {
		return state.allSailsCount+state.loadedPastSailsCount;
	}
	
	public boolean canMove(ArrayList<ICrosser2> crossers, boolean fromLeftToRightBank) {
		return mainStory.isValid( state.currentRightBankCrossers, state.currentLeftBankCrossers, crossers );
	}
	
	public void doMove(ArrayList<ICrosser2> crossers, boolean fromLeftToRightBank) {
		//must pre check this.canMove()
		for( int i=state.allSailsCount-1; i>=state.currentSailCount  ;i-- ) state.transferredBoatRiders.remove( i );
		state.transferredBoatRiders.add( crossers );
		state.move( crossers, fromLeftToRightBank );
		state.sail();
		state.testDebug();
	}
	
	public boolean canUndo() {
		if( state.currentSailCount > 0 ) return true;
		else return false;
	}
	
	public boolean canRedo() {
		if( state.allSailsCount  > state.currentSailCount  ) return true;
		else return false;
	}
	
	public void undo() {
		//must pre check this.canUndo()
		state.move( state.transferredBoatRiders.get( state.currentSailCount-1 ) , state.boatPosition.equals("Left") );
		if( state.boatPosition.equals( "Left" ) ) state.boatPosition = "Right";
		else state.boatPosition = "Left";
		state.currentSailCount--;
	}
	
	public void redo() {
		//must pre check this.canRedo()
		state.move( state.transferredBoatRiders.get( state.currentSailCount ) , state.boatPosition.equals("Left") );
		if( state.boatPosition.equals( "Left" ) ) state.boatPosition = "Right";
		else state.boatPosition = "Left";
		state.currentSailCount++;
	}
	
	public void saveGame() throws ParserConfigurationException, SAXException, IOException, TransformerException {
		command = new SaveCommand(xmlParser);
		remoteControl.addCommand(command);
        remoteControl.pushButton();
	}
	
	public void loadGame() throws ParserConfigurationException, SAXException, IOException, TransformerException {
		//must pre this.loadGamesList > set this.loadProfileId
		command = new LoadGameCommand(xmlParser);
		remoteControl.addCommand(command);
        remoteControl.pushButton();
	}
	
	public ArrayList<ArrayList<ICrosser2>> solveGame(){
		return mainStory.solve();
	}

	public void loadGamesList() throws ParserConfigurationException, SAXException, IOException, TransformerException {
		command = new LoadGameListCommand(xmlParser);
		remoteControl.addCommand(command);
        remoteControl.pushButton();
	}

}
