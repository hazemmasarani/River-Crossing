package controller;

import model.*;

import java.io.IOException;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import main.ICrossingStrategy;

public interface IRiverCrossingController {
	
	public void newGame(ICrossingStrategy gameStrategy);
	public void resetGame();
	public String[] getInstructions();
	public ArrayList<ICrosser2> getCrossersOnRightBank();
	public ArrayList<ICrosser2> getCrossersOnLeftBank();
	public boolean isBoatOnTheLeftBank();
	public int getNumberOfSails();
	public boolean canMove(ArrayList<ICrosser2> crossers, boolean fromLeftToRightBank);
	public void doMove(ArrayList<ICrosser2> crossers, boolean fromLeftToRightBank);
	public boolean canUndo();
	public boolean canRedo();
	public void undo();
	public void redo();
	public void saveGame() throws ParserConfigurationException, SAXException, IOException, TransformerException;
	public void loadGame() throws ParserConfigurationException, SAXException, IOException, TransformerException;
	public ArrayList<ArrayList<ICrosser2>> solveGame();

}
