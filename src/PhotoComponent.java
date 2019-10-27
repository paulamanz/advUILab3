import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

public class PhotoComponent  extends JComponent{
	
	private PhotoComponentView view; 
	private PhotoComponentModel model; 
	private boolean noteSelected;
	private boolean strokeSelected;
	private int positionSelected;
	private boolean editable;
	
	public PhotoComponent(String img) {
		
		setModel(new PhotoComponentModel(img));
		setView(new PhotoComponentView(this));
		this.noteSelected = false;
		this.strokeSelected = false;
		this.editable = false;
		this.positionSelected = -1;
	}
	
	
	private void setModel(PhotoComponentModel model) {
		this.model = model;
		model.addChangeListener(event -> repaint());
	}
	
	public PhotoComponentModel getModel() {
		return this.model;
	}
	
	
	private void setView( PhotoComponentView view) {
		this.view = view; 
		
	}
	
	public boolean isStrokeSelected() {
		return strokeSelected;
	}


	public void setStrokeSelected(boolean strokeSelected) {
		this.strokeSelected = strokeSelected;
	}
	
	
	public boolean isEditable() {
		return editable;
	}


	public void setEditable(boolean editable) {
		this.editable = editable;
	}


	public void changeColor( Color color) {
		if(this.noteSelected && this.editable) {
			
			this.model.getNotes().get(this.positionSelected).setColor(color);
			
		}else if( this.strokeSelected && this.editable) {
			
			this.model.getStrokes().get(positionSelected).setColor(color); 
		}else {
			
			this.model.setColor(color);
		}
		revalidate();
		repaint();
	}
	
	/**
	 * When the user clicks twice the image it changes the state of to flipped or not flipped and the whole component 
	 * is painted again. 
	 */
	public void changeImageState() {
		
		if (this.editable) {
			this.editable = false;
			this.setBorder(BorderFactory.createLineBorder(Color.black));
		}else {
			this.editable = true;
			this.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1, Color.red));
		}
		
		revalidate();
		repaint();
	}
	
	public boolean isNoteSelected() {
		return noteSelected;
	}


	public void setNoteSelected(boolean objectSelected) {
		this.noteSelected = objectSelected;
	}


	public int getPositionSelected() {
		return positionSelected;
	}


	public void setPositionSelected(int positionSelected) {
		this.positionSelected = positionSelected;
	}


	/**
	 * Calls to the model for adding a new character to the latest note and paints again the component. 
	 * @param c
	 */
	public void addToNote (char c) {
		
		model.addToNote(c);
		revalidate();
		repaint();
	}

	public void addActionListener(ActionListener listener) {
		model.addActionListener(listener);
	}
	
	/**
	 * Changes the image shown in the component and paints again it. 
	 * @param img: path to the new image
	 */
	public void loadNewImage ( String img) {
		this.model.changeImage(img);
		revalidate();
		repaint();
	}
	

	@Override
	public Dimension getPreferredSize() {
		return view.getPreferredSize();
		
	}
	/**
	 * Called automatically. Calls the view for painting the component. If the image is flipped it shows the white canvas
	 * and if not it shows the image.
	 */
	@Override
	public void paintComponent (Graphics g) {
		
		view.paint(g, this);
		
	}
		
	

}
