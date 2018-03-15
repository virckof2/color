package edu.ualberta.ebjohnso.analysis;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import edu.ualberta.ebjohnso.results.ImageResult;
import edu.ualberta.ebjohnso.results.ShadeResult;
import edu.ualberta.ebjonhso.gui.Display;

/**
* Main controller class of the color analysis algorithm.
* It wraps the java pixel grabber and uses the pixel values to classify them
* into one of multiple shades that can be created, then guides the reporting
* process.
**/
public class ColorAnalyzer {

	private Image orgImage;
	private int width;
	private int height;
	private int[] pixels;

	private void loadImage(String inFile){
		 orgImage = Toolkit.getDefaultToolkit().getImage(inFile);
	}


	private void grabPixels(){
		try {

            PixelGrabber grabber = new PixelGrabber(orgImage, 0, 0, -1, -1, false);

            if (grabber.grabPixels()) {
                width = grabber.getWidth();
                height = grabber.getHeight();

                pixels = (int[]) grabber.getPixels();

            }
        }
        catch (InterruptedException e1) {
            e1.printStackTrace();
        }
	}

	/**
	* Classifies a collection of pizes into a shade
	*/
	private Image findShade(int pixels[], int width, int height, Shade[] shadeCollection){

		int totalPixels = width*height;

		int[] pixelsMod = pixels;

		for(int i=0; i < totalPixels; i++){
			int  c = pixelsMod[i];
         	int  red = (c & 0x00ff0000) >> 16;
        	int  green = (c & 0x0000ff00) >> 8;
         	int  blue = c & 0x000000ff;

         	for(Shade shade : shadeCollection){

     			if (shade(red, shade.getLowerRed(), shade.getUpperRed()) &&
     	       		shade(green, shade.getLowerGreen(), shade.getUpperGreen()) &&
     	       		shade(blue, shade.getLowerBlue(), shade.getUpperBlue())){

     				// Register hit for shade
     				shade.setTotalCount(shade.getTotalCount()+1);

     				// Highlighting
      				pixelsMod[i] = shade.getHighlighter().getRGB();
	         	}
    		}
         }
		for(Shade shade : shadeCollection){
			seeFilter(shade.getLowerRed(), shade.getLowerBlue(), shade.getLowerGreen(), shade.getUpperRed(), shade.getUpperBlue(), shade.getUpperGreen(), shade.getBaseRed(), shade.getBaseBlue(), shade.getBaseGreen());
		}
		return getImageFromArray(pixelsMod, width, height);

	}

	/**
	* Creates a shade filter
	*/
	private void seeFilter(int lowerR, int lowerB, int lowerG, int upperR, int upperB, int upperG, int orgR, int orgB, int orgG){
		Color colorLower = new Color(lowerR, lowerG, lowerB);
		Color colorUpper = new Color(upperR, upperG, upperB);
		Color colorOrg = new Color(orgR, orgG, orgB);

		int[] pixelSample = new int [90000];
		for(int i=0; i< pixelSample.length; i++){

			if(i<30000){
				pixelSample[i] = colorUpper.getRGB();
			}
			else if(i>30000 && i<60000){
				pixelSample[i] = colorOrg.getRGB();
			}
			else{
				pixelSample[i] = colorLower.getRGB();
			}
		}

		Image filter = getImageFromArray(pixelSample, 300, 300);
		displayImage(filter, 300, 300, Display.MOD_LOCATION_X-170, Display.MOD_LOCATION_Y, "Filter Palette");

	}

	private boolean shade(int value, int lower, int upper){
		if(value <= upper && value >= lower){
			return true;
		}
		return false;
	}

	private void saveImage(Image img, String modImagePath){
		File outputfile = new File(modImagePath);
		try {
			ImageIO.write(toBufferedImage(img), "jpg", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	* Main workflow controller, loads the images to analyze, grabs the pizels,
	* displays an image for empirical comparisons using a minimal gui component,
	* saves analyzed images with highlighters and then exports the text stats.
	*/
	public ImageResult workflow(String imagePath, String modImagePath){
		loadImage(imagePath);
		grabPixels();
		displayImage(orgImage, height, width, Display.ORG_LOCATION_X, Display.ORG_LOCATION_Y, "Original");

		// Highlighter
		Color lightBlueHighlight = new Color(0,204,0);
		Color brightBlueHighlight = new Color(255,42,42);
		Color darkBlueHighlight = new Color(233,235,35);

		// Shades
		Shade lightBlue = new Shade(137, 152, 153, 0.35, 0.00, lightBlueHighlight, "LB");
		Shade brightBlue = new Shade(71, 121, 148, 0.40, 0.25, brightBlueHighlight, "BB");
		Shade brightBlueBaby = new Shade(118,149,183, 0.25, 0.25, brightBlueHighlight, "BB");
		Shade darkBluePurple = new Shade(63,47,93, 0.5, 0.80, darkBlueHighlight, "DB");
		Shade darkBlue = new Shade(19,44,57, 1.8, 0.8, darkBlueHighlight, "DB");
		Shade  darkBlueGrey= new Shade(71,88,101, 0.12, 0.7, darkBlueHighlight, "DB");

		Shade [] shades = {lightBlue, brightBlue, brightBlueBaby, darkBlue, darkBluePurple, darkBlueGrey};

		// Analyzing shades
		Image filtered = findShade(pixels, width, height, shades);
		displayImage(filtered, height, width, Display.MOD_LOCATION_X, Display.MOD_LOCATION_Y,  "Shade Filtered");

		// Saving Analyzed Image
		saveImage(filtered, modImagePath);

		// Reporting
		int totalAreaImage = width*height;
		int totalLightBlue = lightBlue.getTotalCount();
		int totalBrightBlue = brightBlue.getTotalCount()+brightBlueBaby.getTotalCount();
		int totalDarkBlue = darkBlue.getTotalCount()+darkBluePurple.getTotalCount()+darkBlueGrey.getTotalCount();

		double perLightBlue =  (double)(totalLightBlue*100)/totalAreaImage;
		double perBrightBlue= (double) (totalBrightBlue*100)/totalAreaImage;
		double perDarkBlue = (double) (totalDarkBlue*100)/totalAreaImage;

		ImageResult imageResults = new ImageResult();
		imageResults.setImageName(imagePath);

		ArrayList<ShadeResult> shadeResults = new ArrayList<>();

		ShadeResult shadeResultLB = new ShadeResult();
		shadeResultLB.setShadeID(lightBlue.getID());
		shadeResultLB.setTotalAreaImage(totalAreaImage);
		shadeResultLB.setTotalAreaShade(totalLightBlue);
		shadeResultLB.setTotalShadePercentage(perLightBlue);
		shadeResults.add(shadeResultLB);

		ShadeResult shadeResultBB = new ShadeResult();
		shadeResultBB.setShadeID(brightBlue.getID());
		shadeResultBB.setTotalAreaImage(totalAreaImage);
		shadeResultBB.setTotalAreaShade(totalBrightBlue);
		shadeResultBB.setTotalShadePercentage(perBrightBlue);
		shadeResults.add(shadeResultBB);

		ShadeResult shadeResultDB = new ShadeResult();
		shadeResultDB.setShadeID(darkBlue.getID());
		shadeResultDB.setTotalAreaImage(totalAreaImage);
		shadeResultDB.setTotalAreaShade(totalDarkBlue);
		shadeResultDB.setTotalShadePercentage(perDarkBlue);
		shadeResults.add(shadeResultDB);

		imageResults.setShadesResults(shadeResults);

		return imageResults;

	}


	public static Image getImageFromArray(int[] pixelsP, int widthP, int heightP) {
		BufferedImage pixelImage = new BufferedImage(widthP, heightP, BufferedImage.TYPE_INT_RGB);
	    pixelImage.setRGB(0, 0, widthP, heightP, pixelsP, 0, widthP);
	    return pixelImage;
    }

	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}

}
