package fr.lumieretechnology.model;

import java.util.ArrayList;

import org.opencv.core.Core;

import fr.lumieretechnology.view.MainFrame;

public class App {
	public static void main(String[] args) {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		new MainFrame();

		// Reading Images from the file and storing it in to a Matrix object
		// IMAGES
		ArrayList<ImageMat> images = new ArrayList<ImageMat>();
		images.add(new ImageMat("1,3.tif"));
		images.add(new ImageMat("1,95.tif"));
		images.add(new ImageMat("2,6.tif"));
		images.add(new ImageMat("3,25.tif"));
		images.add(new ImageMat("3,9.tif"));
		images.add(new ImageMat("4,55.tif"));
		images.add(new ImageMat("5,2.tif"));
		images.add(new ImageMat("5,85.tif"));
		images.add(new ImageMat("6,5.tif"));
		images.add(new ImageMat("7,15.tif"));
		images.add(new ImageMat("7,8.tif"));
		images.add(new ImageMat("8,45.tif"));
		images.add(new ImageMat("9,1.tif"));
		images.add(new ImageMat("9,75.tif"));
		images.add(new ImageMat("10,4.tif"));
		images.add(new ImageMat("11,05.tif"));
		images.add(new ImageMat("11,7.tif"));
		images.add(new ImageMat("12,35.tif"));
		images.add(new ImageMat("13.tif"));

		System.out.println("Images Loaded ..........");

		// BLACK
		ArrayList<ImageMat> blackImages = new ArrayList<ImageMat>();
		blackImages.add(new ImageMat("noir/noir 1,3.tif"));
		blackImages.add(new ImageMat("noir/noir 1,95.tif"));
		blackImages.add(new ImageMat("noir/noir 2,6.tif"));
		blackImages.add(new ImageMat("noir/noir 3,25.tif"));
		blackImages.add(new ImageMat("noir/noir 3,9.tif"));
		blackImages.add(new ImageMat("noir/noir 4,55.tif"));
		blackImages.add(new ImageMat("noir/noir 5,2.tif"));
		blackImages.add(new ImageMat("noir/noir 5,85.tif"));
		blackImages.add(new ImageMat("noir/noir 6,5.tif"));
		blackImages.add(new ImageMat("noir/noir 7,15.tif"));
		blackImages.add(new ImageMat("noir/noir 7,8.tif"));
		blackImages.add(new ImageMat("noir/noir 8,45.tif"));
		blackImages.add(new ImageMat("noir/noir 9,1.tif"));
		blackImages.add(new ImageMat("noir/noir 9,75.tif"));
		blackImages.add(new ImageMat("noir/noir 10,4.tif"));
		blackImages.add(new ImageMat("noir/noir 11,05.tif"));
		blackImages.add(new ImageMat("noir/noir 11,7.tif"));
		blackImages.add(new ImageMat("noir/noir 12,35.tif"));
		blackImages.add(new ImageMat("noir/noir 13.tif"));

		System.out.println("Black Images Loaded ..........");

		Model.mean(images, blackImages);

	}
}
