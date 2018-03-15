package edu.ualberta.ebjohnso.analysis;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import edu.ualberta.ebjohnso.results.DirectoryResult;
import edu.ualberta.ebjohnso.results.ImageResult;

/**
* This class uses the ColorAnalyzer to drive the analysis of a collection
* of image samples in a specific directory structure. This structure is mostly
* ad-hoc and it is required because the images are stored by the pathologist using
* a naming standard for the clinic.
*/
public class MainController {

	public static String DATA_FOLDER = "./data/realdata/";
	private ColorAnalyzer analyzer;
	private ArrayList<DirectoryResult> results;

	public MainController(){
		analyzer = new ColorAnalyzer();
		results = new ArrayList<>();
	}

	public void analyzeImage(){
		processDirectory(DATA_FOLDER);
	}

	private void processDirectory(String dirPath){

		File folder = new File(dirPath);
		File[] listOfFiles = folder.listFiles();

		DirectoryResult dirResult = new DirectoryResult();
		ArrayList<ImageResult> dirImgResults = new ArrayList<>();
		dirResult.setDirID(dirPath);
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				if(listOfFiles[i].getName().toLowerCase().contains(".jpg") ||
						listOfFiles[i].getName().toLowerCase().contains(".png")){
					ImageResult result = analyzer.workflow(dirPath+"/"+listOfFiles[i].getName(), dirPath+"/analyzed/"+listOfFiles[i].getName());
					System.out.println(result);
					dirImgResults.add(result);
				}
			}
			else{
				if(!listOfFiles[i].getName().contains("analyzed")){
					 System.out.println("Directory " + listOfFiles[i].getName());
					processDirectory(dirPath+"/"+listOfFiles[i].getName());
				}
			}
		}
		dirResult.setImageResults(dirImgResults);
		dirResult.printImageResults();
		results.add(dirResult);
	}

	public static void main(String ... args){
		MainController mc = new MainController();
		mc.analyzeImage();
	}

}
