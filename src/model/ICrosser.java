package model;

import java.awt.image.BufferedImage;

public interface ICrosser {

	public boolean canSail();
	public double getWeight();
	public int getEatingRank();
	public BufferedImage[] getImages();
	public ICrosser2 makeCopy();
	public void setLabelToBeShown(String label);
	public String getLabelToBeShown();

}
