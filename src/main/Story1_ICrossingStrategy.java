package main;

import controller.*;
import crossersFactory.ICrosserFactory;
import model.*;
import java.util.*;

public class Story1_ICrossingStrategy implements ICrossingStrategy2 {

	Stories_IRiverCrossingController mainCrossingController;
	ArrayList<ICrosser2> initialCrossers;

	public Story1_ICrossingStrategy() {
		mainCrossingController = Stories_IRiverCrossingController.getMainCrossingController();
		
		ICrosserFactory crossersFactory = new ICrosserFactory();
		
		ICrosser2 farmer = crossersFactory.createICrosser("farmer");
		ICrosser2 fox = crossersFactory.createICrosser("carni");
		ICrosser2 sheep = crossersFactory.createICrosser("herbi");
		ICrosser2 plant = crossersFactory.createICrosser("plant");

		initialCrossers = new ArrayList<ICrosser2>();
		initialCrossers.add(farmer);
		initialCrossers.add(fox);
		initialCrossers.add(sheep);
		initialCrossers.add(plant);
	}

	public boolean isValid(ArrayList<ICrosser2> rightBankCrossers, ArrayList<ICrosser2> leftBankCrossers, ArrayList<ICrosser2> boatRiders) {
		int SumEatingRank=0;
		boolean boatInLeft= mainCrossingController.state.boatPosition.equals("Left");
		if( leftBankCrossers.size()-boatRiders.size() > 1 && boatInLeft ) {
			for( int i=0; i<leftBankCrossers.size()  ;i++ ) {
				SumEatingRank += leftBankCrossers.get(i).getEatingRank();
			}
			for( int i=0; i<boatRiders.size()  ;i++ ) SumEatingRank -= boatRiders.get(i).getEatingRank();
			if( SumEatingRank != 0 || leftBankCrossers.size()-boatRiders.size() == 3 ) return false;
		}
		
		if( rightBankCrossers.size()-boatRiders.size() > 1 && !boatInLeft ) {
			for( int i=0; i<rightBankCrossers.size()  ;i++ ) {
				SumEatingRank += rightBankCrossers.get(i).getEatingRank();
			}
			for( int i=0; i<boatRiders.size()  ;i++ ) SumEatingRank -= boatRiders.get(i).getEatingRank();
			if( SumEatingRank != 0 || rightBankCrossers.size()-boatRiders.size() == 3 ) return false;
		}
		
		for( int i=0; i<boatRiders.size()  ;i++ ) {
			if ( boatRiders.get(i).canSail() ) return true;
		}
		return false;
	}

	public ArrayList<ICrosser2> getInitialCrossers() {
		return initialCrossers;
	}
	
	public String[] getInstructions() {
		String[] instructions = new String[3];
		instructions[0] = "You need to move all the entities on the left riverbank to the right one";
		instructions[1] = "Only the Farmer can ride the boat, You cant leave an entity that can eat";
		instructions[2] = "another entity on the same riverbank without the farmers watch";
		return instructions;
	}
	
	public ArrayList<ArrayList<ICrosser2>> solve() {
		State state = new State();
		state.currentLeftBankCrossers = this.getInitialCrossers();
		ArrayList<ICrosser2> crossers;
		String lastCrosserType = "none";

		while( state.currentLeftBankCrossers.size() > 0 ) {
			crossers = new ArrayList<ICrosser2>();
			
			if( state.currentLeftBankCrossers.size() == 4 ) {
				for( int i=0; i<state.currentLeftBankCrossers.size() ;i++ ) {
					if( state.currentLeftBankCrossers.get(i).getCrosserType().equals("herbi") ||
							state.currentLeftBankCrossers.get(i).getCrosserType().equals("farmer") ) {
						crossers.add(  state.currentLeftBankCrossers.get(i) );
					}
					lastCrosserType = "herbi";
				}
			}
			else{
				ArrayList<ICrosser2> currentSideCrossers = new ArrayList<ICrosser2>();
				if( state.boatPosition.equals("Left") ) currentSideCrossers = state.currentLeftBankCrossers;
				else {
					if ( state.currentRightBankCrossers.size() == 3 &&
							state.currentLeftBankCrossers.get(0).getCrosserType().equals("herbi") ) {
						for( int i=0; i<state.currentRightBankCrossers.size() ;i++ ) {
							if( state.currentRightBankCrossers.get(i).getCrosserType().equals("farmer") ) {
								crossers.add(  state.currentRightBankCrossers.get(i) );
								break;
							}
						}
					}
					else currentSideCrossers = state.currentRightBankCrossers;
				}

				for( int i=0; i<currentSideCrossers.size() ;i++ ) {
					if( !currentSideCrossers.get(i).getCrosserType().equals(lastCrosserType) &&
							!currentSideCrossers.get(i).getCrosserType().equals("farmer")) {
						crossers.add(  currentSideCrossers.get(i) );
						lastCrosserType = currentSideCrossers.get(i).getCrosserType();
						break;
					}
				}
				for( int i=0; i<currentSideCrossers.size() ;i++ ) {
					if( currentSideCrossers.get(i).getCrosserType().equals("farmer") ) {
						crossers.add(  currentSideCrossers.get(i) );
						break;
					}
				}
			}
			state.transferredBoatRiders.add( crossers );
			state.move( crossers, state.boatPosition.equals("Left") );
			state.sail();
			}		
		return state.transferredBoatRiders;
	}

}
