package fr.lumieretechnology.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import org.opencv.core.Core;

import fr.lumieretechnology.controller.ImageButton;
import fr.lumieretechnology.model.ImageMat;
import fr.lumieretechnology.model.Model;

public class MainFrame {
	private JFrame mainJFrame = new JFrame("Images Mean App");
	private BorderLayout mainBorderLayout = new BorderLayout();
	private JPanel scrollJPanel = new JPanel();
	private JScrollPane scroll = new JScrollPane(scrollJPanel);
	private BorderLayout scrollBorderLayout = new BorderLayout();

	// NORTH
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileJMenu = new JMenu("File");
	private JMenuItem addItem = new JMenuItem("add...");
	private JMenuItem addBlackItem = new JMenuItem("add black...");
	private JMenuItem saveToItem = new JMenuItem("Save to...");
	private JMenu optionJMenu = new JMenu("Option");
	private JCheckBoxMenuItem blackSubstractCorrectionItem = new JCheckBoxMenuItem("Black substract correction", true);
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
	private JButton fileChooserJButton = new JButton("...");
	private JTextField meanLocationJTextField = new JTextField();
	private JButton saveJButton = new JButton("Save");

	private File currentDirectory = null;

	public MainFrame() {
		mainJFrame.setTitle("Images_mean_application");
		mainJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainJFrame.setLocation(
				(int) (GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().getX() * 0.25d),
				(int) (GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().getY() * 0.25d));
		mainJFrame.setSize((int) (GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().getX() * 1.5d),
				(int) (GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().getY() * 1.5d));

		System.out.println(mainJFrame.getSize().height + " " + mainJFrame.getSize().getWidth());
		System.out.println(mainJFrame.getLocation().getX() + " " + mainJFrame.getLocation().getY());

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

		menuBar.add(fileJMenu);
		fileJMenu.add(addItem);
		fileJMenu.add(addBlackItem);
		fileJMenu.add(saveToItem);

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
		GridBagConstraints imagesJLabelConstraints = new GridBagConstraints();
		imagesJLabelConstraints.gridx = 0;
		imagesJLabelConstraints.fill = GridBagConstraints.BOTH;
		imagesJLabelConstraints.anchor = GridBagConstraints.CENTER;
		imagesJLabelConstraints.insets = new Insets(0, 0, 10, 0);

		imagesJPanel.add(imagesJLabel, imagesJLabelConstraints);

		// Button initialization
		GridBagConstraints imagesButtonConstraints = new GridBagConstraints();
		imagesButtonConstraints.gridx = 0;
		imagesButtonConstraints.anchor = GridBagConstraints.CENTER;
		imagesButtonConstraints.insets = new Insets(10, 0, 10, 0);

		imagesImageButtons.add(new ImageButton("+", 0));
		imagesJPanel.add(imagesImageButtons.get(0), imagesButtonConstraints);

		imagesImageButtons.get(0).addActionListener(new ActionListener() {
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
				int returnVal = fChooser.showOpenDialog(mainJFrame);
				File file = fChooser.getSelectedFile();
				currentDirectory = file.getParentFile();

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					ImageButton button = (ImageButton) e.getSource();
					button.addImage(file.getAbsolutePath());
					button.setText(file.getName());

					if (button.getIndex() == imagesImageButtons.size() - 1) {
						ImageButton newLastButton = new ImageButton("+", imagesImageButtons.size());
						newLastButton.addActionListener(this);
						imagesImageButtons.add(newLastButton);
						imagesJPanel.add(imagesImageButtons.get(imagesImageButtons.size() - 1),
								imagesButtonConstraints);
					}

				}
				mainJFrame.repaint();
				mainJFrame.revalidate();

			}
		});

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
		GridBagConstraints blackImagesJLabelConstraints = new GridBagConstraints();
		blackImagesJLabelConstraints.gridx = 0;
		blackImagesJLabelConstraints.fill = GridBagConstraints.BOTH;
		blackImagesJLabelConstraints.anchor = GridBagConstraints.CENTER;
		blackImagesJLabelConstraints.insets = new Insets(0, 0, 10, 0);

		blackImagesJPanel.add(blackImagesJLabel, blackImagesJLabelConstraints);

		// Button initialization
		GridBagConstraints blackImagesButtonConstraints = new GridBagConstraints();
		blackImagesButtonConstraints.gridx = 0;
		blackImagesButtonConstraints.anchor = GridBagConstraints.CENTER;
		blackImagesButtonConstraints.insets = new Insets(10, 0, 10, 0);

		blackImagesImageButtons.add(new ImageButton("+", 0));
		blackImagesJPanel.add(blackImagesImageButtons.get(0), blackImagesButtonConstraints);

		blackImagesImageButtons.get(0).addActionListener(new ActionListener() {
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
				int returnVal = fChooser.showOpenDialog(mainJFrame);
				File file = fChooser.getSelectedFile();
				currentDirectory = file.getParentFile();

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					ImageButton button = (ImageButton) e.getSource();
					button.addImage(file.getAbsolutePath());
					button.setText(file.getName());

					if (button.getIndex() == blackImagesImageButtons.size() - 1) {
						ImageButton newLastButton = new ImageButton("+", blackImagesImageButtons.size());
						newLastButton.addActionListener(this);
						blackImagesImageButtons.add(newLastButton);
						blackImagesJPanel.add(blackImagesImageButtons.get(blackImagesImageButtons.size() - 1),
								blackImagesButtonConstraints);
					}

				}
				mainJFrame.repaint();
				mainJFrame.revalidate();

			}
		});

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
				fChooser.setDialogTitle("Specify a file to save");
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
