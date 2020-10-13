package fr.lumieretechnology.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import fr.lumieretechnology.view.MainFrame;

public class ImageButtonMouseListener implements MouseListener{

	MainFrame gui;
	
	public ImageButtonMouseListener(MainFrame gui) {
		super();
		this.gui=gui;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.isPopupTrigger() && !((ImageButton) e.getSource()).getText().equals("+")){
			
			JPopupMenu menu = new JPopupMenu();
			JMenuItem removeItem = new JMenuItem("Remove");
			removeItem.addActionListener(new ImageRemoveListener(((ImageButton) e.getSource()).getIndex(), gui));
			
			menu.add(removeItem);
			menu.show(e.getComponent(), e.getX(), e.getY());
		}
			
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
