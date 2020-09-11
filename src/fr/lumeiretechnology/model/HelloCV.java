package fr.lumeiretechnology.model;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class HelloCV {
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
		System.out.println("mat = " + mat.dump());

		// double[] tableau = {0.2d, 0.3d, 0.4d, 0.5d, 0.6d, 0.7d, 0.8d, 0.9d, 1.0d,
		// 1.1d, 1.2d, 1.3d};
		// double[] tableau = { 2d, 3d, 4d, 5d, 6d, 7d, 8d };
		double[] tableau = { 2d, 3d, 4d };

		double min = min(tableau);
		double max = max(tableau);

		double bot = 0.2d;
		double top = 0.9d;

		for (int i = 0; i < tableau.length; i++) {
			double x = tableau[i];

//			double a = x - min;
//
//			double b = max - min;
//			double c = a / b;
//
//			double d = ((top - bot) * c);
//			
//			double r = d + bot;
			
//			System.out.println(r + "\t" + a + "\t" + b + "\t" + c + "\t" + d);

			double r = ((top-bot)*((x-min)/(max-min)))+bot;
			System.out.println(r);
		}

	}

	public static double max(double[] t) {
		double max = t[0];
		for (int i = 0; i < t.length; i++) {
			max = Math.max(max, t[i]);
		}
		return max;
	}

	public static double min(double[] t) {
		double min = t[0];
		for (int i = 0; i < t.length; i++) {
			min = Math.min(min, t[i]);
		}
		return min;
	}
}
