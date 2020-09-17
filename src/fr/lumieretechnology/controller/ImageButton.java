package fr.lumieretechnology.controller;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import fr.lumieretechnology.model.ImageMat;

public class ImageButton extends JButton {
	private int index;
	private ImageMat imageMat;

	public ImageButton(int index) {
		super();
		this.index = index;
	}

	public ImageMat getImageMat() {
		return imageMat;
	}

	public void setImageMat(ImageMat imageMat) {
		this.imageMat = imageMat;
	}

	public ImageButton(String title, int index) {
		super(title);
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void addImage(String fileName) {
		this.imageMat = new ImageMat(fileName);
	}

	public void setScaledIcon(String file, int w, int h) {
		ImageIcon icon = new ImageIcon(file);
		Image srcImg = icon.getImage();
		
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();

		ImageIcon resizedIcon = new ImageIcon(resizedImg);
		
		this.setIcon(resizedIcon);

	}
}
