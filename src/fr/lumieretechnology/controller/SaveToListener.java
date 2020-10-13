package fr.lumieretechnology.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.opencv.core.Core;

import fr.lumieretechnology.model.ImageMat;
import fr.lumieretechnology.model.Model;
import fr.lumieretechnology.view.MainFrame;

public class SaveToListener implements ActionListener {
	MainFrame gui;

	public SaveToListener(MainFrame gui) {
		super();
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// SELECT
		JFileChooser fChooser = new JFileChooser(gui.currentDirectory);
		fChooser.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return "TIF images (.tif)";
			}

			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				} else {
					String fileName = f.getName().toLowerCase();
					return fileName.endsWith(".tif") || fileName.endsWith(".tiff");
				}
			}
		});
		fChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fChooser.setDialogTitle("Save location");
		fChooser.setSelectedFile(new File("mean.tif"));
		int returnValue = fChooser.showSaveDialog(gui.mainJFrame);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File file = fChooser.getSelectedFile();
			gui.meanLocationJTextField.setText(file.getAbsolutePath());
		}

		// CALCUL AND EXPORT
		if (gui.blackSubstractCorrectionItem.isSelected()) {
			ArrayList<ImageMat> images = new ArrayList<ImageMat>();
			gui.imagesImageButtons.stream().filter(imgButton -> imgButton.getImageMat() != null)
					.forEach(imgButton -> images.add(imgButton.getImageMat()));

			ArrayList<ImageMat> blackImages = new ArrayList<ImageMat>();
			gui.blackImagesImageButtons.stream().filter(imgButton -> imgButton.getImageMat() != null)
					.forEach(imgButton -> blackImages.add(imgButton.getImageMat()));

			new Thread() {
				public void run() {
					System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

					Model.mean(images, blackImages, gui.meanLocationJTextField.getText());
				}
			}.start();
		} else {
			ArrayList<ImageMat> images = new ArrayList<ImageMat>();
			gui.imagesImageButtons.stream().filter(imgButton -> imgButton.getImageMat() != null)
					.forEach(imgButton -> images.add(imgButton.getImageMat()));

			new Thread() {
				public void run() {
					System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

					Model.meanWithoutBlackSubstraction(images, gui.meanLocationJTextField.getText());
				}
			}.start();
		}
	}
}