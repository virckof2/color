package edu.ualberta.ebjohnso.results;

import java.util.ArrayList;
import java.util.HashMap;

public class DirectoryResult {
	
	private String dirID;
	
	private ArrayList<ImageResult> imageResults;

	public String getDirID() {
		return dirID;
	}

	public void setDirID(String dirID) {
		this.dirID = dirID;
	}

	public ArrayList<ImageResult> getImageResults() {
		return imageResults;
	}

	public void setImageResults(ArrayList<ImageResult> imageResults) {
		this.imageResults = imageResults;
	}
	
	public void printImageResults(){	
		HashMap<String, ShadeResult> consolidated = new HashMap<>();
		for(ImageResult ir : imageResults){
			ArrayList<ShadeResult> shadeResults = ir.getShadesResults();
			for(ShadeResult sr : shadeResults){
				if(consolidated.containsKey(sr.getShadeID())){
					ShadeResult shadeConsolidated = consolidated.get(sr.getShadeID());
					int tai = shadeConsolidated.getTotalAreaImage()+sr.getTotalAreaImage();
					int tas = shadeConsolidated.getTotalAreaShade()+sr.getTotalAreaShade();
					shadeConsolidated.setTotalAreaImage(tai);
					shadeConsolidated.setTotalAreaShade(tas);
					shadeConsolidated.recalculateTotalAreaPercentage();
				}
				else{
					consolidated.put(sr.getShadeID(), sr);
				}
				
			}
		}
		System.out.println(this.dirID);
		if(!consolidated.isEmpty()){
			System.out.println(consolidated.values());
		}
	}
	
	

}
