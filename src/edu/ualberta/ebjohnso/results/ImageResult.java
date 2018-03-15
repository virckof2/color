package edu.ualberta.ebjohnso.results;

import java.util.ArrayList;

public class ImageResult {
	
	private String imageName;
	
	private ArrayList<ShadeResult> shadesResults;

	public ArrayList<ShadeResult> getShadesResults() {
		return shadesResults;
	}

	public void setShadesResults(ArrayList<ShadeResult> shadesResults) {
		this.shadesResults = shadesResults;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	@Override
	public String toString() {
		return "ImageResults [imageName=" + imageName + ", shadesResults=" + shadesResults + "]";
	}
	
	
}
