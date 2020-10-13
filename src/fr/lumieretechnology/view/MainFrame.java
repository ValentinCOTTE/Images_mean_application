package fr.lumieretechnology.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.CompoundControl;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import org.opencv.core.Core;

import fr.lumieretechnology.controller.BlackImageButtonListener;
import fr.lumieretechnology.controller.BlackImageButtonMouseListener;
import fr.lumieretechnology.controller.ImageButton;
import fr.lumieretechnology.controller.ImageButtonListener;
import fr.lumieretechnology.controller.ImageButtonMouseListener;
import fr.lumieretechnology.controller.ImageRemoveListener;
import fr.lumieretechnology.controller.SaveToListener;
import fr.lumieretechnology.model.ImageMat;
import fr.lumieretechnology.model.Model;

public class MainFrame {

	public JFrame mainJFrame = new JFrame("Images Mean App");
	public BorderLayout mainBorderLayout = new BorderLayout();
	public JPanel scrollJPanel = new JPanel();
	public JScrollPane scroll = new JScrollPane(scrollJPanel);
	public BorderLayout scrollBorderLayout = new BorderLayout();

	// NORTH
	public JMenuBar menuBar = new JMenuBar();
	public JMenu fileJMenu = new JMenu("File");
	public JMenuItem addItem = new JMenuItem("add...");
	public JMenuItem addBlackItem = new JMenuItem("add black...");
	public JMenuItem saveToItem = new JMenuItem("Save to...");
	public JMenu optionJMenu = new JMenu("Option");
	public JCheckBoxMenuItem blackSubstractCorrectionItem = new JCheckBoxMenuItem("Black substract correction", true);
	public JMenu helpJMenu = new JMenu("Help");
	public JMenuItem aboutItem = new JMenuItem("About");
	public JMenuItem noticeItem = new JMenuItem("Notice");

	// CENTER-WEST
	public JPanel imagesJPanel = new JPanel();
	public GridBagLayout imagesBagLayout = new GridBagLayout();
	public JLabel imagesJLabel = new JLabel("Images");
	public ArrayList<ImageButton> imagesImageButtons = new ArrayList<ImageButton>();
	public GridBagConstraints imagesJLabelConstraints = new GridBagConstraints();
	public GridBagConstraints imagesButtonConstraints = new GridBagConstraints();

	// CENTER-EAST
	public JPanel blackImagesJPanel = new JPanel();
	public GridBagLayout blackImagesBagLayout = new GridBagLayout();
	public JLabel blackImagesJLabel = new JLabel("Black Images");
	public ArrayList<ImageButton> blackImagesImageButtons = new ArrayList<ImageButton>();
	public GridBagConstraints blackImagesJLabelConstraints = new GridBagConstraints();
	public GridBagConstraints blackImagesButtonConstraints = new GridBagConstraints();

	// EAST
	public JPanel resultJPanel = new JPanel();
	public GridBagLayout resultBagLayout = new GridBagLayout();
	public JLabel meanLocationJLabel = new JLabel("Mean Location");
	public JButton fileChooserJButton = new JButton("...");
	public JTextField meanLocationJTextField = new JTextField();
	public JButton saveJButton = new JButton("Save");

	public File currentDirectory = null;

	public MainFrame() {
		mainJFrame.setTitle("Images_mean_application");
		mainJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainJFrame.setLocation(
				(int) (GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().getX() * 0.25d),
				(int) (GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().getY() * 0.25d));
		mainJFrame.setSize((int) (GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().getX() * 1.5d),
				(int) (GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().getY() * 1.5d));

//		System.out.println(mainJFrame.getSize().height + " " + mainJFrame.getSize().getWidth());
//		System.out.println(mainJFrame.getLocation().getX() + " " + mainJFrame.getLocation().getY());

		mainJFrame.setLayout(mainBorderLayout);

		// NORTH
		initMenuBar();

		// CENTER
		mainJFrame.add(scroll, BorderLayout.CENTER);
		scrollJPanel.setLayout(scrollBorderLayout);

		initImagesContent();
		initBlackImagesContent();

		// EAST
		initResultContent();

		mainJFrame.setVisible(true);
	}

	/**
	 * Initialize the Menu bar in the NORTH
	 */
	private void initMenuBar() {
		mainJFrame.add(menuBar, BorderLayout.NORTH);

		// FILE
		menuBar.add(fileJMenu);
		fileJMenu.add(addItem);
		addItem.addActionListener(new ImageButtonListener(this));

		fileJMenu.add(addBlackItem);
		addBlackItem.addActionListener(new BlackImageButtonListener(this));

		fileJMenu.add(saveToItem);
		saveToItem.addActionListener(new SaveToListener(this));

		// OPTION
		menuBar.add(optionJMenu);
		optionJMenu.add(blackSubstractCorrectionItem);
		blackSubstractCorrectionItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for (ImageButton blackImageButton : blackImagesImageButtons) {
					blackImageButton.setEnabled(blackSubstractCorrectionItem.isSelected());
				}

			}
		});

		// HELP
		menuBar.add(helpJMenu);
		helpJMenu.add(noticeItem);
		helpJMenu.add(aboutItem);
	}

	/**
	 * Initialize the Images side in the WEST
	 */
	private void initImagesContent() {
		// JPanel configuration
		scrollJPanel.add(imagesJPanel, BorderLayout.WEST);
		imagesJPanel.setBorder(new EmptyBorder(100, 100, 100, 100));
		imagesJPanel.setLayout(imagesBagLayout);

		// Label

		imagesJLabelConstraints.gridx = 0;
		imagesJLabelConstraints.fill = GridBagConstraints.BOTH;
		imagesJLabelConstraints.anchor = GridBagConstraints.CENTER;
		imagesJLabelConstraints.insets = new Insets(0, 0, 10, 0);

		imagesJPanel.add(imagesJLabel, imagesJLabelConstraints);

		// Button initialization
		imagesButtonConstraints.gridx = 0;
		imagesButtonConstraints.anchor = GridBagConstraints.CENTER;
		imagesButtonConstraints.insets = new Insets(10, 0, 10, 0);

		imagesImageButtons.add(new ImageButton("+", 0));
		imagesJPanel.add(imagesImageButtons.get(0), imagesButtonConstraints);

		imagesImageButtons.get(0).addActionListener(new ImageButtonListener(this));
		imagesImageButtons.get(0).addMouseListener(new ImageButtonMouseListener(this));

	}

	/**
	 * Initialize the Black Images side in the CENTER
	 */
	private void initBlackImagesContent() {

		// JPanel configuration
		scrollJPanel.add(blackImagesJPanel, BorderLayout.EAST);
		blackImagesJPanel.setBorder(new EmptyBorder(100, 100, 100, 100));
		blackImagesJPanel.setLayout(blackImagesBagLayout);

		// Label
		blackImagesJLabelConstraints.gridx = 0;
		blackImagesJLabelConstraints.fill = GridBagConstraints.BOTH;
		blackImagesJLabelConstraints.anchor = GridBagConstraints.CENTER;
		blackImagesJLabelConstraints.insets = new Insets(0, 0, 10, 0);

		blackImagesJPanel.add(blackImagesJLabel, blackImagesJLabelConstraints);

		// Button initialization
		blackImagesButtonConstraints.gridx = 0;
		blackImagesButtonConstraints.anchor = GridBagConstraints.CENTER;
		blackImagesButtonConstraints.insets = new Insets(10, 0, 10, 0);

		blackImagesImageButtons.add(new ImageButton("+", 0));
		blackImagesJPanel.add(blackImagesImageButtons.get(0), blackImagesButtonConstraints);

		blackImagesImageButtons.get(0).addActionListener(new BlackImageButtonListener(this));
		blackImagesImageButtons.get(0).addMouseListener(new BlackImageButtonMouseListener(this));
		
		blackImagesImageButtons.get(0).setEnabled(blackSubstractCorrectionItem.isSelected());

	}

	/**
	 * Initialize the result side in the EAST
	 */
	private void initResultContent() {
		mainJFrame.add(resultJPanel, BorderLayout.EAST);
		resultJPanel.setBorder(new EmptyBorder(100, 100, 100, 100));
		resultJPanel.setLayout(resultBagLayout);

		GridBagConstraints meanLocationJLabelConstraints = new GridBagConstraints();
		meanLocationJLabelConstraints.gridx = 1;
		resultJPanel.add(meanLocationJLabel, meanLocationJLabelConstraints);

		GridBagConstraints fileChooserJButtonConstraints = new GridBagConstraints();
		fileChooserJButtonConstraints.gridx = 0;
		fileChooserJButtonConstraints.gridy = 1;
		fileChooserJButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fChooser = new JFileChooser(currentDirectory);
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
				int returnValue = fChooser.showSaveDialog(mainJFrame);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File file = fChooser.getSelectedFile();
					meanLocationJTextField.setText(file.getAbsolutePath());
				}

			}
		});
		resultJPanel.add(fileChooserJButton, fileChooserJButtonConstraints);

		GridBagConstraints meanLocationJTextFieldConstraints = new GridBagConstraints();
		meanLocationJTextFieldConstraints.gridx = 1;
		meanLocationJTextFieldConstraints.gridy = 1;

		meanLocationJTextFieldConstraints.fill = GridBagConstraints.BOTH;
		resultJPanel.add(meanLocationJTextField, meanLocationJTextFieldConstraints);
		meanLocationJTextField.setPreferredSize(new Dimension(400, 20));

		GridBagConstraints saveButtonConstraints = new GridBagConstraints();
		saveButtonConstraints.gridx = 1;
		saveButtonConstraints.anchor = GridBagConstraints.EAST;
		resultJPanel.add(saveJButton, saveButtonConstraints);

		saveJButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (blackSubstractCorrectionItem.isSelected()) {
					ArrayList<ImageMat> images = new ArrayList<ImageMat>();
					imagesImageButtons.stream().filter(imgButton -> imgButton.getImageMat() != null)
							.forEach(imgButton -> images.add(imgButton.getImageMat()));

					ArrayList<ImageMat> blackImages = new ArrayList<ImageMat>();
					blackImagesImageButtons.stream().filter(imgButton -> imgButton.getImageMat() != null)
							.forEach(imgButton -> blackImages.add(imgButton.getImageMat()));

					new Thread() {
						public void run() {
							System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

							Model.mean(images, blackImages, meanLocationJTextField.getText());
						}
					}.start();
				} else {
					ArrayList<ImageMat> images = new ArrayList<ImageMat>();
					imagesImageButtons.stream().filter(imgButton -> imgButton.getImageMat() != null)
							.forEach(imgButton -> images.add(imgButton.getImageMat()));

					new Thread() {
						public void run() {
							System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

							Model.meanWithoutBlackSubstraction(images, meanLocationJTextField.getText());
						}
					}.start();
				}

			}
		});

	}
}
