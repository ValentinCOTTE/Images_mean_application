package fr.lumeiretechnology.model;

import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.SizeSequence;
import javax.swing.text.DefaultEditorKit.CutAction;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;


public class Model {
	public static void mean(ArrayList<ImageMat> images,ArrayList<ImageMat> blackImages) {

		// Loading the OpenCV core library
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		// Instantiating the imagecodecs class
		Imgcodecs imageCodecs = new Imgcodecs();



		// ERROR CHECK
		//their is as much images as blackImages
		if (images.size()<blackImages.size()) System.out.println("ERROR : too mush black images ");
		else if (images.size()>blackImages.size()) System.out.println("ERROR : too mush black images ");
		
		//images are same size between them
		for (int i=0; i<images.size() ; i++ ) {
			for (int j = i+1; j < images.size(); j++) {
				checkSizes(images.get(i), images.get(j));
			}
		}
		
		//images are same size with their own blackImage
		for (int i = 0; i < images.size(); i++) {
			checkSizes(images.get(i), blackImages.get(i));
		}
		
		
		
		//CHARGE IMAGES ATTRIBUT INTO LISTS
		
		//List all files
		ArrayList<String> files = new ArrayList<String>();
		for (ImageMat image : images) {
			files.add(image.getFileName());
		}
		
		//List all mats
		ArrayList<Mat> mats = new ArrayList<Mat>();
		for (ImageMat image : images) {
			mats.add(image.getMat());
		}
		
		
		//List all black files
		ArrayList<String> blackFiles = new ArrayList<String>();
		for (ImageMat blackImage : blackImages) {
			blackFiles.add(blackImage.getFileName());
		}
		
		//List all black mats
		ArrayList<Mat> blackMats = new ArrayList<Mat>();
		for (ImageMat blackImage : blackImages) {
			blackMats.add(blackImage.getMat());
		}



		// Noises substraction
		for (int i = 0; i < mats.size(); i++) {
			System.out.println(files.get(i) + " : " + mats.get(i).cols() + " x " + mats.get(i).cols());
			System.out.println(blackFiles.get(i) + " : " + blackMats.get(i).cols() + " x " + blackMats.get(i).cols());
			Core.subtract(mats.get(i), blackMats.get(i), mats.get(i));
		}

		System.out.println("Black Substraction Done ..........");

		// 32bits conversion
		beginto32bits(mats);
		System.out.println("32bits convertion Done ..........");

		// calcul the mean image
		System.out.println("Calcul mean.tif ............");
		Mat meanMat = meanOf(mats);
//		System.out.println(meanMat.submat(0, 6, 0, 5).dump());

		endto32bits(meanMat);
//		System.out.println(meanMat.submat(0, 6, 0, 5).dump());

		saveImage(meanMat, "mean.tif");

		System.out.println("DONE ............");

		System.out.println("Press \"ENTER\" to exit...");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();

	}

	/**
	 * Normalize a double x into a specific interval [top-bot]. The double value x
	 * is in a range from min to max.
	 * 
	 * @param x   normalized double
	 * @param max maximum value of the range from where x come from
	 * @param min minimum value of the range from where x come from
	 * @param top topside of the norm interval
	 * @param bot botside of the norm interval
	 * @return
	 */
	public double normalized(double x, double max, double min, double top, double bot) {
		return ((top - bot) * ((x - min) / (max - min))) + bot;
	}

	/**
	 * Convert multiple mats (16bits U) into their 32bits versions but let it in
	 * 64bits type in order to keep precision
	 * 
	 * @param mats list of mat (16bits Unsigned)
	 */
	public static void beginto32bits(ArrayList<Mat> mats) {
		for (Mat mat : mats) {
			beginto32bits(mat);
		}
	}

	/**
	 * Convert a mat into his 32bits version but let it in 64bits type in order to
	 * keep precision
	 * 
	 * @param mat (16bits U)
	 */
	public static void beginto32bits(Mat mat) {
//		System.out.println(mat.submat(0, 6, 0, 5).dump());

		// since in mat
		// we have values between 0 and
		// 65535 so all resulted values
		// will be between 0.0f and 1.0f
		mat.convertTo(mat, CvType.CV_64F, 1 / 65535.0d);

//		System.out.println(mat.submat(0, 6, 0, 5).dump());
		Core.multiply(mat, mat, mat);

//		System.out.println(mat.submat(0, 6, 0, 5).dump());
	}

	/**
	 * 2nd part to convert a list of mats into their 32bits versions
	 * 
	 * @param mats list of mats (64bits F)
	 */
	public static void endto32bits(ArrayList<Mat> mats) {
		for (Mat mat : mats) {
			endto32bits(mat);
		}
	}

	/**
	 * 2nd part to convert a mat (16bits U) into 32bits
	 * 
	 * @param mat (64bits F)
	 */
	public static void endto32bits(Mat mat) {
		// System.out.println(mat.submat(0, 6, 0, 5).dump());
		mat.convertTo(mat, CvType.CV_32F);
		// System.out.println(mat.submat(0, 6, 0, 5).dump());

	}

	/**
	 * Create a Mat which is the mean of a list of Mats
	 * 
	 * @param mats
	 * @return mean value
	 */
	public static Mat meanOf(ArrayList<Mat> mats) {
		
		Mat matrix = mats.get(0).clone();
		
		for (int i = 1; i < mats.size(); i++) {
			Core.add(matrix, mats.get(i), matrix);
		}
		
		double size = mats.size();
		Core.divide(matrix, Scalar.all(size), matrix);
		
//		System.out.println(mats.get(0).submat(0, 6, 0, 5).dump());
//		System.out.println(mats.get(1).submat(0, 6, 0, 5).dump());
//		System.out.println(mats.get(2).submat(0, 6, 0, 5).dump());
//		System.out.println(mats.get(3).submat(0, 6, 0, 5).dump());
//		System.out.println(mats.get(4).submat(0, 6, 0, 5).dump());
//		System.out.println(mats.get(5).submat(0, 6, 0, 5).dump());
//		System.out.println(mats.get(6).submat(0, 6, 0, 5).dump());
//		
//		System.out.println(matrix.submat(0, 6, 0, 5).dump());
		
		return matrix;
	}
	
	public static void checkSizes(ImageMat imageA, ImageMat imageB) {
		int xA = imageA.getMat().cols();
		int yA = imageA.getMat().rows();
		
		int xB = imageB.getMat().cols();
		int yB = imageB.getMat().rows();
		
		if(xA != xB || yA!=yB) {
			System.out.println("Error : Sizes doesn't match between " + imageA.getFileName() +" and " + imageB.getFileName());
			System.out.println(imageA.getFileName() + " : " + xA + " x " + yA);
			System.out.println(imageA.getFileName() + " : " + xB + " x " + yB);
		}
	}

	/**
	 * Load all images in an ArrayList of Mat.
	 * 
	 * @param files Names of files to load
	 * @return Mat of images
	 */
	public static ArrayList<Mat> loadImages(ArrayList<String> files) {
		// Instantiating the imagecodecs class
		Imgcodecs imageCodecs = new Imgcodecs();
		ArrayList<Mat> mats = new ArrayList<Mat>();
		for (String file : files) {
			mats.add(loadImage(file));
		}
		return mats;
	}

	/**
	 * Load a unique image file as a Mat.
	 * 
	 * @param mat
	 * @param fileName
	 */
	private static Mat loadImage(String fileName) {
		// Instantiating the imagecodecs class
		Imgcodecs imageCodecs = new Imgcodecs();
		Mat mat = imageCodecs.imread(fileName, CvType.CV_16U);

//		System.out.println(mat.submat(0, 6, 0, 5).dump());

		System.out.println(fileName + " Loaded ..........");

		return mat;
	}

	/**
	 * Save all Mats as files images.
	 * 
	 * @param mats  Mats to save
	 * @param files Names of files
	 */
	public static void saveImages(ArrayList<Mat> mats, ArrayList<String> files) {
		if (mats.size() == files.size()) {
			for (int i = 0; i < mats.size(); i++) {
				saveImage(mats.get(i), files.get(i));
			}
		} else {
			System.out.println(
					"Error in saveImages(): mats.size() = " + mats.size() + " and files.size() = " + files.size());
		}

	}

	/**
	 * Save a unique Mat as an image file.
	 * 
	 * @param mat
	 * @param fileName
	 */
	private static void saveImage(Mat mat, String fileName) {
		// Instantiating the imagecodecs class
		Imgcodecs imageCodecs = new Imgcodecs();
		imageCodecs.imwrite(fileName, mat);
		System.out.println(fileName + " Saved ..........");
	}
}
