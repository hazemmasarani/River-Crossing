package main;

import model.*;
import java.util.*;

public interface ICrossingStrategy {

	public boolean isValid(ArrayList<ICrosser2> rightBankCrossers, ArrayList<ICrosser2> leftBankCrossers, ArrayList<ICrosser2> boatRiders);
	public ArrayList<ICrosser2> getInitialCrossers();
	public String[] getInstructions();

}