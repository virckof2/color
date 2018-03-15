package edu.ualberta.ebjonhso.gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.JFrame;


public class Display extends JFrame {

	private ImagePanel imagePanel;
	
	// Location of the original image for dual screen.
	public static Integer ORG_LOCATION_X = -900;
	public static Integer ORG_LOCATION_Y = 200;
	
	// Location of the modified image for dual screen
	public static Integer MOD_LOCATION_X = -1600;
	public static Integer MOD_LOCATION_Y = 200;
	
	public Display(Image image, int height,int width){
		super("Erika's Image Analyzer");
		setLayout( new FlowLayout() ); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		imagePanel = new ImagePanel(image, height, width);
		setSize(width/2+15, height/2+40);
		
        setLayout(new GridLayout(1,1));
        
        add(imagePanel);
        setVisible(true);
        setResizable(false);
	}
	
	public Display(Image image, int height,int width, int locX, int locY, String title){
		super(title);
		setLayout( new FlowLayout() ); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		imagePanel = new ImagePanel(image, height, width);
		setSize(width/2+15, height/2+35);
        setLayout(new GridLayout(1,1));
        
        this.setLocation(locX, locY);
        add(imagePanel);
        setVisible(true);
        
	}
}
