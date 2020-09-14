package fr.lumieretechnology.controller;

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
}
