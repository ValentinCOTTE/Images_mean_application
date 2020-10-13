package fr.lumieretechnology.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import com.sun.java.accessibility.util.GUIInitializedListener;

import fr.lumieretechnology.view.MainFrame;

public class BlackImageRemoveListener implements ActionListener {

	int indexSelectedButton;
	MainFrame gui;

	public BlackImageRemoveListener(int indexSelectedButton, MainFrame gui) {
		super();
		this.indexSelectedButton = indexSelectedButton;
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gui.blackImagesImageButtons.remove(indexSelectedButton);
		gui.blackImagesJPanel.removeAll();
		for (int i = 0; i < gui.blackImagesImageButtons.size(); i++) {
			gui.blackImagesImageButtons.get(i).setIndex(i);
		}

		gui.blackImagesJPanel.add(gui.blackImagesJLabel, gui.blackImagesJLabelConstraints);
		gui.blackImagesImageButtons.stream().forEach(button -> gui.blackImagesJPanel.add(button, gui.blackImagesButtonConstraints));

		gui.mainJFrame.revalidate();
		gui.mainJFrame.repaint();
	}

}
