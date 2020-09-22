package fr.lumieretechnology.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import fr.lumieretechnology.view.MainFrame;

public class ImageButtonListener implements ActionListener {

	MainFrame gui;

	public ImageButtonListener(MainFrame gui) {
		super();
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

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
		fChooser.setMultiSelectionEnabled(true);

		int returnVal = fChooser.showOpenDialog(gui.mainJFrame);

		File[] files = fChooser.getSelectedFiles();

		if (returnVal == JFileChooser.APPROVE_OPTION) {

			gui.currentDirectory = files[0].getParentFile();
			int index = 0;

			if (e.getSource() instanceof ImageButton) {
				ImageButton button = (ImageButton) e.getSource();
				button.addImage(files[index].getAbsolutePath());
				button.setText(files[index].getName());

				if (button.getIndex() == gui.imagesImageButtons.size() - 1) {
					ImageButton newLastButton = new ImageButton("+", gui.imagesImageButtons.size());
					newLastButton.addActionListener(this);
					gui.imagesImageButtons.add(newLastButton);
					gui.imagesJPanel.add(gui.imagesImageButtons.get(gui.imagesImageButtons.size() - 1),
							gui.imagesButtonConstraints);
				}
				index++;
			}

			for (int i = index; i < files.length; i++) {
				ImageButton lastButton = gui.imagesImageButtons.get(gui.imagesImageButtons.size() - 1);
				lastButton.addImage(files[i].getAbsolutePath());
				lastButton.setText(files[i].getName());

				if (lastButton.getIndex() == gui.imagesImageButtons.size() - 1) {
					ImageButton newLastButton = new ImageButton("+", gui.imagesImageButtons.size());
					newLastButton.addActionListener(this);
					gui.imagesImageButtons.add(newLastButton);
					gui.imagesJPanel.add(gui.imagesImageButtons.get(gui.imagesImageButtons.size() - 1),
							gui.imagesButtonConstraints);
				}
			}

		}

		gui.mainJFrame.repaint();
		gui.mainJFrame.revalidate();
	}
}
