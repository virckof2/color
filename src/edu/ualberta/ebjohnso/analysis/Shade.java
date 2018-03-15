package edu.ualberta.ebjohnso.analysis;

import java.awt.Color;

/**
* This class represent the image shade filtering results
*/
public class Shade {

	// Filter Setup
	private String ID;
	private int totalCount;
	private Color highlighter;
	private int baseRed;
	private int baseBlue;
	private int baseGreen;
	private double upperWindow;
	private double lowerWindow;

	// shade TBG ranges.
	private int upperRed = (int) (baseRed + (baseRed*upperWindow));
	private int upperBlue = (int) (baseBlue + (baseBlue*upperWindow));
	private int upperGreen = (int) (baseGreen + (baseGreen*upperWindow));
	private int lowerRed = (int) (baseRed - (baseRed*lowerWindow));
	private int lowerBlue = (int) (baseBlue - (baseBlue*lowerWindow));
	private int lowerGreen = (int) (baseGreen - (baseGreen*lowerWindow));

	public Shade (int baseRed, int baseGreen, int baseBlue, double upperWindow, double lowerWindow, Color highlighter, String ID){
		this.baseRed = baseRed;
		this.baseBlue = baseBlue;
		this.baseGreen = baseGreen;
		this.upperWindow = upperWindow;
		this.lowerWindow = lowerWindow;
		this.highlighter = highlighter;

		upperRed = (int) (baseRed + (baseRed*upperWindow));
		upperBlue = (int) (baseBlue + (baseBlue*upperWindow));
		upperGreen = (int) (baseGreen + (baseGreen*upperWindow));

		lowerRed = (int) (baseRed - (baseRed*lowerWindow));
		lowerBlue = (int) (baseBlue - (baseBlue*lowerWindow));
		lowerGreen = (int) (baseGreen - (baseGreen*lowerWindow));

		this.ID = ID;
	}

	public int getBaseRed() {
		return baseRed;
	}

	public void setBaseRed(int baseRed) {
		this.baseRed = baseRed;
	}

	public int getBaseBlue() {
		return baseBlue;
	}

	public void setBaseBlue(int baseBlue) {
		this.baseBlue = baseBlue;
	}

	public int getBaseGreen() {
		return baseGreen;
	}

	public void setBaseGreen(int baseGreen) {
		this.baseGreen = baseGreen;
	}

	public double getUpperWindow() {
		return upperWindow;
	}

	public void setUpperWindow(double upperWindow) {
		this.upperWindow = upperWindow;
	}

	public double getLowerWindow() {
		return lowerWindow;
	}

	public void setLowerWindow(double lowerWindow) {
		this.lowerWindow = lowerWindow;
	}

	public int getUpperRed() {
		return upperRed;
	}

	public void setUpperRed(int upperRed) {
		this.upperRed = upperRed;
	}

	public int getUpperBlue() {
		return upperBlue;
	}

	public void setUpperBlue(int upperBlue) {
		this.upperBlue = upperBlue;
	}

	public int getUpperGreen() {
		return upperGreen;
	}

	public void setUpperGreen(int upperGreen) {
		this.upperGreen = upperGreen;
	}

	public int getLowerRed() {
		return lowerRed;
	}

	public void setLowerRed(int lowerRed) {
		this.lowerRed = lowerRed;
	}

	public int getLowerBlue() {
		return lowerBlue;
	}

	public void setLowerBlue(int lowerBlue) {
		this.lowerBlue = lowerBlue;
	}

	public int getLowerGreen() {
		return lowerGreen;
	}

	public void setLowerGreen(int lowerGreen) {
		this.lowerGreen = lowerGreen;
	}

	public Color getHighlighter() {
		return highlighter;
	}

	public void setHighlighter(Color highlighter) {
		this.highlighter = highlighter;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

}
