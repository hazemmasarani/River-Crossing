package model;

import java.awt.image.BufferedImage;
import javafx.scene.control.Label;

public class Farmer implements ICrosser2 {

	String CrosserType;
	double weight;
	Label shownLabel;

	public Farmer() {
		CrosserType = "farmer";
		weight = 20;
	}

	public boolean canSail() {
		return true;
	}

	public double getWeight() {
		return weight;
	}

	public int getEatingRank() {
		return 2;
	}

	public BufferedImage[] getImages() {
		return null;
	}

	public ICrosser2 makeCopy() {
		ICrosser2 copy = new Farmer();
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
