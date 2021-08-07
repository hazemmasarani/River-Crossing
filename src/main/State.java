package main;

import model.*;
import java.util.*;

public class State {

	public int whichStory;

	public int  loadedPastSailsCount;
	public int allSailsCount;
	public int currentSailCount;
	public String boatPosition;

	public ArrayList<ArrayList<ICrosser2>> transferredBoatRiders;
	public ArrayList<ICrosser2> currentRightBankCrossers;
	public ArrayList<ICrosser2> currentLeftBankCrossers;

	public State() {
		whichStory = 0; // ie: none

		loadedPastSailsCount = 0;
		allSailsCount = 0;
		currentSailCount = 0;
		boatPosition = "Left";

		transferredBoatRiders = new ArrayList<ArrayList<ICrosser2>>();
		currentRightBankCrossers = new ArrayList<ICrosser2>();
		currentLeftBankCrossers = new ArrayList<ICrosser2>();
	}
	
	public void testDebug() {
		if ( currentSailCount == 0) return;
		System.out.println("============"+currentSailCount+"============");
		System.out.println("#Curr L:");
		for( int i=0; i<currentLeftBankCrossers.size()  ;i++ )
			System.out.println( ".  "+i+".  "+currentLeftBankCrossers.get(i).getCrosserType()+" ("+currentLeftBankCrossers.get(i).getWeight()+")" );
		System.out.println("#Prev B:");
		for( int i=0; i<transferredBoatRiders.get(currentSailCount-1).size()  ;i++ )
			System.out.println( ".  "+i+".  "+transferredBoatRiders.get(currentSailCount-1).get(i).getCrosserType()+" ("+transferredBoatRiders.get(currentSailCount-1).get(i).getWeight()+")" );
		System.out.println("#Curr R:");
		for( int i=0; i<currentRightBankCrossers.size()  ;i++ )
			System.out.println( ".  "+i+".  "+currentRightBankCrossers.get(i).getCrosserType()+" ("+currentRightBankCrossers.get(i).getWeight()+")" );
	}

	public void move(ArrayList<ICrosser2> crossers, boolean fromLeftToRightBank) {
		if ( fromLeftToRightBank ) {
			for( int i=crossers.size()-1; i>=0  ;i-- ) {
				currentRightBankCrossers.add( crossers.get(i) );
				for( int j=currentLeftBankCrossers.size()-1; j>=0  ;j-- ) {
					if ( currentLeftBankCrossers.get(j).getEatingRank() == crossers.get(i).getEatingRank() &&
							currentLeftBankCrossers.get(j).getWeight() == crossers.get(i).getWeight() ) {
						currentLeftBankCrossers.remove(j);
						break;
					}
				}
			}
		}
		else {
			for( int i=crossers.size()-1; i>=0  ;i-- ) {
				currentLeftBankCrossers.add( crossers.get(i) );
				for( int j=currentRightBankCrossers.size()-1; j>=0  ;j-- ) {
					if ( currentRightBankCrossers.get(j).getEatingRank() == crossers.get(i).getEatingRank() &&
							currentRightBankCrossers.get(j).getWeight() == crossers.get(i).getWeight() ) {
						currentRightBankCrossers.remove(j);
						break;
					}
				}
			}
		}
	}

	public void sail() {
		if( boatPosition.equals( "Left" ) ) boatPosition = "Right";
		else boatPosition = "Left";
		currentSailCount++;
		allSailsCount = currentSailCount;
	}
}
