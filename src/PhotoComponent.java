import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import javax.swing.JComponent;

public class PhotoComponent  extends JComponent{
	
	PhotoComponentView view; 
	PhotoComponentModel model; 
	boolean objectSelected;
	int positionSelected;
	
	public PhotoComponent(String img) {
		
		setModel(new PhotoComponentModel(img));
		setView(new PhotoComponentView(this));
		this.objectSelected = false;
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
	
	public void changeColor( Color color) {
		if(this.objectSelected) {
			System.out.println("Cambiar el color del objeto "+ this.positionSelected);
			this.model.getNotes().get(this.positionSelected).setColor(color);
			System.out.println("Color cambiado!");
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
/*	public void changeImageState() {
		if (model.isFlipped()) {
			model.setFlipped(false);
		}else {
			model.setFlipped(true);
		}
		revalidate();
		repaint();
	}*/
	
	public boolean isObjectSelected() {
		return objectSelected;
	}


	public void setObjectSelected(boolean objectSelected) {
		this.objectSelected = objectSelected;
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
//		if ( model.isFlipped()) {
//			view.paintBackPicture(g, this);
//			
//		}else{
			view.paint(g, this);
	}
		
	//}

}
