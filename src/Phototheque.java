import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JScrollPane;

public class Phototheque extends JFrame{
	
	private JMenuBar mb;
	private JMenu file;
	private JMenu view;
	private JMenuItem itemimport, delete, quit;
	private ButtonGroup menuGroup, toolBarGroup;
	private JRadioButtonMenuItem photoViewer, browser;
	private JLabel statusBar;
	private JToolBar toolBar;
	private JToggleButton people, places, school, travels, family, color, selectedColor;
	private PhotoComponent photocomp;
	private JScrollPane scrollPane;
	private JPanel mainPanel;
	private Boolean thereIsPhoto;
	
	public Phototheque () {
		super("Phototheque");
		this.setPreferredSize(new Dimension(800,800));
		this.setMinimumSize(new Dimension(500, 500));
		this.thereIsPhoto = false;
		
		setUpUI();
		this.pack();
		this.setVisible(true);
	}
	
	private void setUpUI() {
		
		setUpMenuBar();
		setUpToolBar();
		
		
		scrollPane = new JScrollPane();
		
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.BLACK);
		
		//photocomp = new PhotoComponent ("/Users/paulamanzano/Desktop/folder.png");
	//	mainPanel.add(photocomp);
		scrollPane.setViewportView(mainPanel);
		
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		
		
		
		setUpStatusBar();
		
	}
	
	private void setUpStatusBar() {
		statusBar = new JLabel("Status Bar");
		statusBar.setHorizontalAlignment(JLabel.CENTER);
		this.getContentPane().add(statusBar, BorderLayout.SOUTH);
		
	}
	
	private void setUpToolBar() {
		
		toolBar = new JToolBar("My ToolBar");
		toolBar.setOrientation(SwingConstants.VERTICAL);
		toolBarGroup = new ButtonGroup();
		
		people = new JToggleButton("People");
		toolBarGroup.add(people);
		toolBar.add(people);
		
		places = new JToggleButton ("Places");
		toolBarGroup.add(places);
		toolBar.add(places);
		
		school = new JToggleButton("School");
		toolBarGroup.add(school);
		toolBar.add(school);
		
		travels = new JToggleButton("Travels");
		toolBarGroup.add(travels);
		toolBar.add(travels);
		
		family = new JToggleButton("Family");
		toolBarGroup.add(family);
		toolBar.add(family);
		
		color = new JToggleButton("Choose a color");
		toolBarGroup.add(color);
		toolBar.add(color);
		
		selectedColor = new JToggleButton("");
		toolBarGroup.add(selectedColor);
		toolBar.add(selectedColor);
		
		color.addActionListener(event -> colorChooserMethod());
		
		
		this.getContentPane().add(toolBar, BorderLayout.WEST);
		
		
	}
	
	private void colorChooserMethod() {
		Color c=JColorChooser.showDialog(this,"Choose",Color.CYAN);
		//mainPanel.setBackground(c);
		if (photocomp != null) {
			photocomp.changeColor(c);
			
		}
		selectedColor.setBackground(c);
	}
	private void setUpMenuBar() {
		
		
		mb = new JMenuBar();
		setJMenuBar(mb);
		
		file = new JMenu("File");
		mb.add(file);
		
		itemimport=new JMenuItem("Import");
        file.add(itemimport);
        
        itemimport.addActionListener(event -> openFileChooser());
        
        delete = new JMenuItem("Delete");
        file.add(delete);
        delete.addActionListener(event -> deleteImage());
        
        quit = new JMenuItem ("Quit");
        file.add(quit);
        quit.addActionListener(event-> System.exit(0));
        
        
        view = new JMenu ("View");
        mb.add(view);
                        	
        menuGroup = new ButtonGroup();
        
        photoViewer = new JRadioButtonMenuItem("Photo Viewer");
        photoViewer.addActionListener(event -> statusBar.setText("You selected the Photo Viewer"));
        menuGroup.add(photoViewer);
        view.add(photoViewer);
        photoViewer.setSelected(true);
        
        browser = new JRadioButtonMenuItem("Browser");
        menuGroup.add(browser);
        browser.addActionListener(event -> statusBar.setText("You selected the Browser"));
        view.add(browser);        

		
	}
	
	private void deleteImage() {
		statusBar.setText("You selected the Delete Button");
		this.mainPanel.remove(photocomp);
		this.thereIsPhoto = false;
	}
	
	private void openFileChooser() {
		JFileChooser chooser = new JFileChooser();
		
		chooser.setDialogTitle("Select an image");
		chooser.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
		chooser.addChoosableFileFilter(filter);
		
		int returnVal = chooser.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
		       statusBar.setText("You chose to open this file: " +
		            chooser.getSelectedFile().getAbsolutePath());
		       
		       if (!thereIsPhoto) {
		    	   photocomp = new PhotoComponent (chooser.getSelectedFile().getAbsolutePath());
		    	   mainPanel.add(photocomp);
		    	   this.thereIsPhoto = true;
		    	   
		       }else {
		    	   photocomp.loadNewImage(chooser.getSelectedFile().getAbsolutePath());
		    	   
		       }
		       
		    }
	}

}
