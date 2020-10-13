package fr.lumieretechnology.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import com.sun.java.accessibility.util.GUIInitializedListener;

import fr.lumieretechnology.view.MainFrame;

public class ImageRemoveListener implements ActionListener {

	int indexSelectedButton;
	MainFrame gui;

	public ImageRemoveListener(int indexSelectedButton, MainFrame gui) {
		super();
		this.indexSelectedButton = indexSelectedButton;
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gui.imagesImageButtons.remove(indexSelectedButton);
		gui.imagesJPanel.removeAll();
		for (int i = 0; i < gui.imagesImageButtons.size(); i++) {
			gui.imagesImageButtons.get(i).setIndex(i);
		}

		gui.imagesJPanel.add(gui.imagesJLabel, gui.imagesJLabelConstraints);
		gui.imagesImageButtons.stream().forEach(button -> gui.imagesJPanel.add(button, gui.imagesButtonConstraints));

		gui.mainJFrame.revalidate();
		gui.mainJFrame.repaint();
	}

}
