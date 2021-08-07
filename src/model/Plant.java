package model;

import java.awt.image.BufferedImage;
import javafx.scene.control.Label;

public class Plant implements ICrosser2 {

	String CrosserType;
	double weight;
	Label shownLabel;

	public Plant() {
		CrosserType = "plant";
		weight = 20;
	}

	public boolean canSail() {
		return false;
	}

	public double getWeight() {
		return weight;
	}

	public int getEatingRank() {
		return -1;
	}

	public BufferedImage[] getImages() {
		return null;
	}

	public ICrosser2 makeCopy() {
		ICrosser2 copy = new Plant();
		copy = this;
		return copy;
	}

	public void setLabelToBeShown(String label) {
		shownLabel.setText(label);
	}

	public String getLabelToBeShown() {
		return shownLabel.getText();
	}
	
	public String getCrosserType() {
		return CrosserType;
	}
	
	public  void setWeight(double weight) {
		this.weight = weight;
	}

}
