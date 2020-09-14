package fr.lumieretechnology.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import fr.lumieretechnology.controller.ImageButton;

public class MainFrame extends JFrame {

	private BorderLayout mainBorderLayout = new BorderLayout();

	// NORTH
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileJMenu = new JMenu("File");
	private JMenuItem addItem = new JMenuItem("add...");
	private JMenuItem addBlackItem = new JMenuItem("add black...");
	private JMenuItem saveToItem = new JMenuItem("Save to...");
	private JMenu optionJMenu = new JMenu("Option");
	private JCheckBoxMenuItem blackSubstractCorrectionItem = new JCheckBoxMenuItem("Black substract correction", false);
	private JMenu helpJMenu = new JMenu("Help");
	private JMenuItem aboutItem = new JMenuItem("About");
	private JMenuItem noticeItem = new JMenuItem("Notice");

	// WEST
	private JPanel imagesJPanel = new JPanel();
	private GridBagLayout imagesBagLayout = new GridBagLayout();
	private JLabel imagesJLabel = new JLabel("Images");
	private ArrayList<ImageButton> imagesImageButtons = new ArrayList<ImageButton>();

	// CENTER
	private JPanel blackImagesJPanel = new JPanel();
	private GridBagLayout blackImagesBagLayout = new GridBagLayout();
	private JLabel blackImagesJLabel = new JLabel("Black Images");
	private ArrayList<ImageButton> blackImagesImageButtons = new ArrayList<ImageButton>();

	// EAST
	private JPanel resultJPanel = new JPanel();
	private GridBagLayout resultBagLayout = new GridBagLayout();
	private JLabel meanLocationJLabel = new JLabel("Mean Location");
	private JTextField meanLocationJTextField = new JTextField();
	private JButton saveJButton = new JButton("Save");

	public MainFrame() {
		this.setTitle("Images_mean_application");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocation((int) (GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().getX() * 0.25d),
				(int) (GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().getY() * 0.25d));
		this.setSize((int) (GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().getX() * 1.5d),
				(int) (GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().getY() * 1.5d));

		System.out.println(this.getSize().height + " " + this.getSize().getWidth());
		System.out.println(this.getLocation().getX() + " " + this.getLocation().getY());

		this.setLayout(mainBorderLayout);

		// NORTH
		initMenuBar();
		// WEST
		initImagesContent();
		// CENTER
		initBlackImagesContent();
		// EAST
		initResultContent();

		this.setVisible(true);
	}

	/**
	 * Initialize the Menu bar in the NORTH
	 */
	private void initMenuBar() {
		this.add(menuBar, BorderLayout.NORTH);

		menuBar.add(fileJMenu);
		fileJMenu.add(addItem);
		fileJMenu.add(addBlackItem);
		fileJMenu.add(saveToItem);

		menuBar.add(optionJMenu);
		optionJMenu.add(blackSubstractCorrectionItem);

		menuBar.add(helpJMenu);
		helpJMenu.add(noticeItem);
		helpJMenu.add(aboutItem);
	}

	/**
	 * Initialize the Images side in the WEST
	 */
	private void initImagesContent() {
		// JPanel configuration
		this.add(imagesJPanel, BorderLayout.WEST);
		imagesJPanel.setBorder(new EmptyBorder(100, 100, 100, 100));
		imagesJPanel.setLayout(imagesBagLayout);

		// Label
		GridBagConstraints imagesJLabelConstraints = new GridBagConstraints();
		imagesJLabelConstraints.gridx = 0;
		imagesJLabelConstraints.fill = GridBagConstraints.BOTH;
		imagesJLabelConstraints.anchor = GridBagConstraints.CENTER;
		imagesJLabelConstraints.ipady = 20;

		imagesJPanel.add(imagesJLabel, imagesJLabelConstraints);

		// Button initialization
		imagesImageButtons.add(new ImageButton("+", 0));

		for (ImageButton imageButton : imagesImageButtons) {
			GridBagConstraints imagesButtonConstraints = new GridBagConstraints();
			imagesButtonConstraints.gridx = 0;
			imagesButtonConstraints.anchor = GridBagConstraints.CENTER;

			imagesJPanel.add(imageButton, imagesButtonConstraints);
		}

	}

	/**
	 * Initialize the Black Images side in the CENTER
	 */
	private void initBlackImagesContent() {

		// JPanel configuration
		this.add(blackImagesJPanel, BorderLayout.CENTER);
		blackImagesJPanel.setBorder(new EmptyBorder(100, 100, 100, 100));
		blackImagesJPanel.setLayout(blackImagesBagLayout);

		// Label
		GridBagConstraints blackImagesJLabelConstraints = new GridBagConstraints();
		blackImagesJLabelConstraints.gridx = 0;
		blackImagesJLabelConstraints.fill = GridBagConstraints.BOTH;
		blackImagesJLabelConstraints.anchor = GridBagConstraints.CENTER;
		blackImagesJLabelConstraints.ipady = 20;

		blackImagesJPanel.add(blackImagesJLabel, blackImagesJLabelConstraints);

		// Button initialization
		blackImagesImageButtons.add(new ImageButton("+", 0));

		for (ImageButton imageButton : blackImagesImageButtons) {
			GridBagConstraints blackImagesButtonConstraints = new GridBagConstraints();
			blackImagesButtonConstraints.gridx = 0;
			blackImagesButtonConstraints.anchor = GridBagConstraints.CENTER;

			blackImagesJPanel.add(imageButton, blackImagesButtonConstraints);
		}

		if (blackSubstractCorrectionItem.isSelected()) {
			blackImagesJPanel.setEnabled(true);
			for (ImageButton imageButton : blackImagesImageButtons) {
				imageButton.setEnabled(true);
			}
			blackImagesJLabel.setEnabled(true);

		} else {
			blackImagesJPanel.setEnabled(false);
			for (ImageButton imageButton : blackImagesImageButtons) {
				imageButton.setEnabled(false);
			}
			blackImagesJLabel.setEnabled(false);
		}

	}

	/**
	 * Initialize the result side in the EAST
	 */
	private void initResultContent() {
		this.add(resultJPanel, BorderLayout.EAST);
		resultJPanel.setBorder(new EmptyBorder(100, 100, 100, 100));
		resultJPanel.setLayout(resultBagLayout);

		GridBagConstraints meanLocationJLabelConstraints = new GridBagConstraints();
		meanLocationJLabelConstraints.gridx = 0;
		resultJPanel.add(meanLocationJLabel, meanLocationJLabelConstraints);

		GridBagConstraints meanLocationJTextFieldConstraints = new GridBagConstraints();
		meanLocationJTextFieldConstraints.gridx = 0;
		meanLocationJTextFieldConstraints.fill = GridBagConstraints.BOTH;
		resultJPanel.add(meanLocationJTextField, meanLocationJTextFieldConstraints);
		meanLocationJTextField.setPreferredSize(new Dimension(200, 20));

		GridBagConstraints saveButtonConstraints = new GridBagConstraints();
		saveButtonConstraints.gridx = 0;
		saveButtonConstraints.anchor = GridBagConstraints.EAST;
		resultJPanel.add(saveJButton, saveButtonConstraints);

	}
}
