package edu.ualberta.ebjonhso.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{

    private Image image;

    public ImagePanel(Image toDisplay, int height, int width) {
    	image = toDisplay;
    	this.setPreferredSize(new Dimension(width/2, height/2));
    	image= image.getScaledInstance(width/2, height/2, 0);
    	repainter();
    }
    
    private void repainter(){
    	Thread repainter = new Thread(new Runnable() {
    	    @Override
    	    public void run() {
    	        while (true) {
    	            repaint();
    	            try {
    	                Thread.sleep(30);
    	            } catch (InterruptedException ignored) {
    	            }
    	        }
    	    }
    	});
    	repainter.setName("Image repaint");
    	repainter.setPriority(Thread.MIN_PRIORITY);
    	repainter.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);           
    }

}