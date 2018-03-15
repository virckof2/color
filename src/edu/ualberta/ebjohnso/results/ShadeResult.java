package edu.ualberta.ebjohnso.results;

public class ShadeResult {

	private String shadeID;
	private int totalAreaImage;
	private int totalAreaShade;
	double totalShadePercentage;
	
	public int getTotalAreaImage() {
		return totalAreaImage;
	}
	public void setTotalAreaImage(int totalAreaImage) {
		this.totalAreaImage = totalAreaImage;
	}
	public int getTotalAreaShade() {
		return totalAreaShade;
	}
	public void setTotalAreaShade(int totalAreaShade) {
		this.totalAreaShade = totalAreaShade;
	}
	public String getShadeID() {
		return shadeID;
	}
	public void setShadeID(String shadeID) {
		this.shadeID = shadeID;
	}
	public double getTotalShadePercentage() {
		return totalShadePercentage;
	}
	public void setTotalShadePercentage(double totalShadePercentage) {
		this.totalShadePercentage = totalShadePercentage;
	}
	
	public void recalculateTotalAreaPercentage(){
		totalShadePercentage = (double) (totalAreaShade*100)/totalAreaImage;
	}
	
	@Override
	public String toString() {
		return "\n [shadeID=" + shadeID + "," +"Image Area="+ totalAreaImage +","+"Total Area Shade=" + totalAreaShade+ ", TotalShade Percentage=" + totalShadePercentage + "%]";
	}
	
	
	
}
