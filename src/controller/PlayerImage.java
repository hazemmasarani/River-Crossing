package controller;

import javafx.scene.image.ImageView;

public class PlayerImage {
	
	boolean isOnBoat;
	boolean isInLeft;
	int position;
	ImageView player;
	int eatingRank;
	double weight;

	public PlayerImage(ImageView player, int position ,int eatingRank,double weight) {
		isOnBoat = false;
		isInLeft = true;
		this.player = player;
		this.position = position;
		this.eatingRank = eatingRank;
		this.weight = weight;
	}
	
	public void moveBoat() {
		if ( isInLeft ) isInLeft = false;
		else isInLeft = true;
	}
	
	public void movePlayer() {
		if ( isOnBoat ) isOnBoat = false;
		else isOnBoat = true;
	}
}
