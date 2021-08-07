package main;

import model.*;
import java.util.*;
import crossersFactory.ICrosserFactory;

import crossersFactory.ICrosserFactory;

public class Story2_ICrossingStrategy implements ICrossingStrategy2 {
	
	ArrayList<ICrosser2> initialCrossers;
	
	public Story2_ICrossingStrategy() {
		
		ICrosserFactory crossersFactory = new ICrosserFactory();
		
		ICrosser2 farmer1 = crossersFactory.createICrosser("farmer");
		ICrosser2 farmer2 = crossersFactory.createICrosser("farmer");
		ICrosser2 farmer3 = crossersFactory.createICrosser("farmer");
		ICrosser2 farmer4 = crossersFactory.createICrosser("farmer");
		ICrosser2 sheep = crossersFactory.createICrosser("herbi");
		
		farmer1.setWeight( 40 );
		farmer2.setWeight( 60 );
		farmer3.setWeight( 80 );
		farmer4.setWeight( 90 );
		
		initialCrossers = new ArrayList<ICrosser2>();
		initialCrossers.add(farmer1);
		initialCrossers.add(farmer2);
		initialCrossers.add(farmer3);
		initialCrossers.add(farmer4);
		initialCrossers.add(sheep);
	}
	
	public boolean isValid(ArrayList<ICrosser2> rightBankCrossers, ArrayList<ICrosser2> leftBankCrossers,ArrayList<ICrosser2> boatRiders) {
		double totalWeight = 0;
		for( int i=0; i<boatRiders.size()  ;i++ ) {
			totalWeight += boatRiders.get(i).getWeight();
		}
		if ( totalWeight > 100 ) return false;
		for( int i=0; i<boatRiders.size()  ;i++ ) {
			if (boatRiders.get(i).canSail() ) return true;
		}
		return false;
	}

	public ArrayList<ICrosser2> getInitialCrossers(){
		return initialCrossers;
	}

	public String[] getInstructions() {
		String[] instructions = new String[3];
		instructions[0] = "Four farmers and their animal need to cross a river in a small boat.";
		instructions[1] = "The weights of the farmers are 90 kg, 80 kg,60 kg and 40 kg respectively";
		instructions[2] = "and the weight of the animal is 20 kg.";
		return instructions;
	}
	
	public ArrayList<ArrayList<ICrosser2>> solve() {
		State state = new State();
		state.currentLeftBankCrossers = this.getInitialCrossers();
		ArrayList<ICrosser2> crossers;

		while( state.currentLeftBankCrossers.size() > 0 ) {
			crossers = new ArrayList<ICrosser2>();
			
			if( state.currentSailCount == 0 ) {
				for( int i=0; i<state.currentLeftBankCrossers.size() ;i++ ) {
					if( state.currentLeftBankCrossers.get(i).getCrosserType().equals("herbi") ||
							state.currentLeftBankCrossers.get(i).getWeight() == 40 ) {
						crossers.add(  state.currentLeftBankCrossers.get(i) );
					}
				}
			}
			else if( state.currentSailCount == 1 ) {
				for( int i=0; i<state.currentRightBankCrossers.size() ;i++ ) {
					if( state.currentRightBankCrossers.get(i).getWeight() == 40 ) {
						crossers.add(  state.currentRightBankCrossers.get(i) );
						break;
					}
				}
			}
			else if( state.boatPosition.equals("Left") ){
				int counter = 0;
				for( int i=0; i<state.currentLeftBankCrossers.size() ;i++ ) {
					if( state.currentLeftBankCrossers.get(i).getWeight() == 40 ) counter++;
					if( state.currentLeftBankCrossers.get(i).getWeight() == 60 ) counter++;
				}
				if( counter > 1 ) {
					for( int i=0; i<state.currentLeftBankCrossers.size() ;i++ ) {
						if( state.currentLeftBankCrossers.get(i).getWeight() == 40 ) crossers.add(  state.currentLeftBankCrossers.get(i) );
						if( state.currentLeftBankCrossers.get(i).getWeight() == 60 ) crossers.add(  state.currentLeftBankCrossers.get(i) );
					}
				}
				else {
					for( int i=0; i<state.currentLeftBankCrossers.size() ;i++ ) {
						if( state.currentLeftBankCrossers.get(i).getWeight() != 40 ) {
							crossers.add(  state.currentLeftBankCrossers.get(i) );
							break;
						}
					}
				}
			}
			else {
				int counter = 0;
				for( int i=0; i<state.currentRightBankCrossers.size() ;i++ ) {
					if( state.currentRightBankCrossers.get(i).getWeight() == 40 ) counter++;
					if( state.currentRightBankCrossers.get(i).getWeight() == 60 ) counter++;
				}
				if( counter > 1 ) {
					for( int i=0; i<state.currentRightBankCrossers.size() ;i++ ) {
						if( state.currentRightBankCrossers.get(i).getWeight() == 40 ) crossers.add(  state.currentRightBankCrossers.get(i) );
					}
				}
				else {
					for( int i=0; i<state.currentRightBankCrossers.size() ;i++ ) {
						if( state.currentRightBankCrossers.get(i).getWeight() == 60 ) {
							crossers.add(  state.currentRightBankCrossers.get(i) );
							break;
						}
					}
				}
			}
			state.transferredBoatRiders.add( crossers );
			state.move( crossers, state.boatPosition.equals("Left") );
			state.sail();
			state.testDebug();
			}		
		return state.transferredBoatRiders;
	}
	
}
