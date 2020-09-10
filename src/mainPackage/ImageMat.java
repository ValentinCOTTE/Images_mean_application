package mainPackage;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class ImageMat {

	private String fileName;
	private Mat mat;
	

	public ImageMat(String fileName, Mat mat) {
		this.fileName = fileName;
		this.mat = mat;
	}
	
	public ImageMat(String fileName) {
		this.fileName = fileName;
		this.mat = loadImage(fileName);
	}

	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Mat getMat() {
		return mat;
	}

	public void setMat(Mat mat) {
		this.mat = mat;
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
	
	
	
}
