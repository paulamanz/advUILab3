import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PhotoComponentModel {
	private ArrayList<ActionListener> actionListeners = new ArrayList<ActionListener>();

	private ArrayList<ChangeListener> changeListeners = new ArrayList<ChangeListener>(); 
	//reference to image
	private BufferedImage image;
	private Color currentColor; 
	private FontMetrics font;
	
	//reference to note 
	private List<Note> notes;
	
	//reference to strokes
	private List<Stroke> strokes;
	
	public PhotoComponentModel(String img) {
		try {
			this.image = ImageIO.read(new File(img));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		this.notes = new ArrayList<Note>();
		this.strokes = new ArrayList<Stroke>();
		this.currentColor = Color.red;
		
	}
	
	public BufferedImage getImage() {
		return this.image;
	}
	
	
	public Color getColor() {
		return this.currentColor;
	}
	
	public void setColor(Color color) {
		this.currentColor = color;
	}
	
	public List<Note> getNotes() {
		return this.notes;
	}
	
	public List<Stroke> getStrokes(){
		return this.strokes;
	}
	
	/**
	 * When a new image is imported, the path to the file changes. The back image and the notes of it must be deleted aswell.
	 * @param img: path to the new file.
	 */
	public void changeImage(String img) {
		try {
			this.image = ImageIO.read(new File(img));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.notes = new ArrayList<Note>();
	}
	
	public void saveImage (BufferedImage img) {
		this.image = img;
	}
	
	
	public void addActionListener(ActionListener listener) {
		actionListeners.add(listener);
	}
	
	/**
	 * Adds a new note to the back canvas
	 * @param note
	 */
	public void addNote(Note note) {
		note.setColor(currentColor);
		this.notes.add(note);
	}
	
	public void addStroke(Stroke stroke) {
		stroke.setColor(currentColor);
		this.strokes.add(stroke);
	}
	/**
	 * Adds a new character to the last note displayed in the canvas
	 * @param c
	 */
	public void addToNote (Character c) {
		Note note = this.notes.get(notes.size()-1);
		
		//The full size of the note is calculated by giving an aproximated size of 8 pixels per character and another 8 of margin.
		int fullsize = note.getX()+( note.getLineSize())+8;
		
		//if the note has arrived to the border of the image, it has to break the line and continue in the one above
		if (fullsize >= this.image.getWidth(null)) {
			note.breakNote();
		}
		
		
		if(this.notes.size()> 0) {
			this.notes.get(notes.size()-1).addChar(c);
		}
		
	}
	
	public void addToStroke(int x, int y) {
		this.strokes.get(strokes.size()-1).addPoint(x, y);
		
		
	}
	

	private void fireButton() {
		for (ActionListener listener: actionListeners) {
			
			listener.actionPerformed ( new ActionEvent(this, (int) System.currentTimeMillis(), "fire"));
		}
	}
	private void fireChangeListener() {
		
		for (ChangeListener listener : changeListeners) {
			listener.stateChanged(new ChangeEvent(this));
		}
	}

	
	public void addChangeListener(ChangeListener listener) {
		changeListeners.add(listener);
	}

	public FontMetrics getFontMetrics() {
		return font;
	}

	public void setFontMetrics(FontMetrics font) {
		this.font = font;
	}
	
}
