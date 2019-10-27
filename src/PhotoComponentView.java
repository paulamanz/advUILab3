import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.ImageIcon;

public class PhotoComponentView {
	
	

	PhotoComponent controller;
	// Graphics2D object 
	private Graphics2D g2;
	private Graphics g;
	private Image image;
	private boolean noteSelected;
	private boolean strokeSelected;
	private boolean paintingStroke;
	private int positionSelectedNote;
	// Mouse coordinates
	private int currentX, currentY, oldX, oldY;
	 
	
	public PhotoComponentView (PhotoComponent controller) {
		this.controller = controller;
		setupListeners();
		this.paintingStroke=false;
//		noteSelected = false;
//		positionSelectedNote = -1;
		
	}

	private void setupListeners() {
		 controller.setDoubleBuffered(false);
		 PhotoComponentModel model = controller.getModel();
		 
	
		controller.addMouseListener(new MouseListener() {
			
			//Used for taking the position for drawing lines
			@Override public void mousePressed(MouseEvent e) {
				oldX = e.getX();
		        oldY = e.getY();
			}
			@Override public void mouseReleased(MouseEvent e) {
				
				
				if (controller.isObjectSelected()) {
					if(controller.getPositionSelected() > 0) {
						System.out.println("Note "+ controller.getPositionSelected() + ":");
						Note note = controller.getModel().getNotes().get(controller.getPositionSelected());
						System.out.println("- Starts: ("+ note.getX()+", "+ note.getY()+")");
						System.out.println("- Finishes: ("+ note.getEndx()+", "+ note.getEndy()+")");
					}
				}
				if (paintingStroke) {
					paintingStroke = false;
				}
			}

			@Override 
			public void mouseClicked(MouseEvent e) {
				
				//If there is a double click it flips the image
//				if (e.getClickCount() == 2) {
//				    controller.changeImageState();
//				    
//				}else 
				 controller.setObjectSelected(false);
				 strokeSelected = false;
				 
					
					if (e.getClickCount() == 1){
						
						int x=e.getX();
						int y=e.getY();
						
						int positionSelectedNote = isIntoNote(x,y);
						controller.setPositionSelected(positionSelectedNote);
						if(controller.isObjectSelected()) {
							Note note = model.getNotes().get(positionSelectedNote);
							System.out.println("YOU CLICKED INSIDE A NOTE");
							
							controller.repaint();
						}else {
							int positionSelectedStroke = isIntoStroke(x,y);
							if (strokeSelected) {
								controller.setPositionSelected(positionSelectedStroke);
								System.out.println("YOU CLICKED INSIDE A STROKE");
								controller.repaint();
							}else {
								model.addNote(new Note(x,y, g.getFontMetrics()));
							}
							
							
						}
						
					//If there is one click and the back of the image is displayed, user can paint and write
					 // if (model.isFlipped()) {
						  //get position 
						  
						  
						  controller.setFocusable(true);
						  controller.requestFocusInWindow();
					 }
				  //}
			}
			@Override public void mouseEntered(MouseEvent e) {}
			@Override public void mouseExited(MouseEvent e) {}
			
			
			
		});
		
		controller.addMouseMotionListener(new MouseMotionListener() {

			//Used for tracking the path of the draw and painting it.
			@Override
			public void mouseDragged(MouseEvent e) {
				
				currentX = e.getX();
		        currentY = e.getY();
		        PhotoComponentModel model = controller.getModel();
				
				if(controller.isObjectSelected()) {
					Note note = model.getNotes().get(controller.getPositionSelected());
					note.setX(currentX);
					note.setY(currentY);
					model.getNotes().set(controller.getPositionSelected(), note);
					
					
				}else {
					// coord x,y when drag mouse
			        
			 
			        if (g2 != null) {
			        	//System.out.println("MouseDragged");
			          // draw line if g2 context not null
			          g2.setColor(model.getColor());
			          g2.drawLine(oldX, oldY, currentX, currentY);
			         
			          
			          if (!paintingStroke) {
			        	  model.addStroke(new Stroke(oldX, oldY));
			        	  paintingStroke = true;
			          }else {
			        	  model.addToStroke(oldX, oldY);
			          }
			          
			         
			          
			          // store current coords x,y as olds x,y
			          oldX = currentX;
			          oldY = currentY;
			        }
				}
				controller.repaint();
				
			}

			@Override public void mouseMoved(MouseEvent e) {}
			
		});
		
		controller.addKeyListener(new KeyListener() {

			//Adds the key pressed to the note displayed in the screen
			@Override public void keyTyped(KeyEvent e) {
				
				controller.addToNote(e.getKeyChar());
			//	Note note = model.getNotes().get(model.getNotes().size()-1);
			}

			@Override public void keyPressed(KeyEvent e) {}

			@Override public void keyReleased(KeyEvent e) {}
			
		});
		
	}
	
	public int isIntoNote(int x, int y) {
		 
		PhotoComponentModel model = controller.getModel();
		List<Note> notelist = model.getNotes();
		int i = 0;
		int positionNote = -1;
		System.out.println("-----------------------------------------");
		System.out.println("Entered in method isIntoNOTE");
	    if (notelist.size() > 0) {
	    	for (i= 0; i < notelist.size(); i++) {
	    		Note note = notelist.get(i);
	    		System.out.println("Note number "+ i);
	    		
	    		System.out.println("VERTICAL: ("+note.getY()+" ->" + note.getEndy() +") | real click:"+ y);
	    		if( note.getY() <= y && y <= note.getEndy()) {
	    			System.out.println("FIRST FILTER");
	    			System.out.println("HORIZONTAL: ("+note.getX()+" ->" + note.getMaxX() +") | real click:"+ x);
	    			
    				
	    			if( note.getX() <= x && x <= note.getMaxX()) {
	    				controller.setObjectSelected(true);
	    				positionNote = i;
	    				System.out.println("Has clickado dentro de la nota "+ i +" !!!");
	    				g.setColor(Color.RED);
	    				System.out.println(note.getX()+ " - " + note.getY()+ " - " + (note.getMaxX()- note.getX())+ " - " + (note.getEndy() - note.getY()));
	    				
	    				

	    				
	    			}
	    		}
	    	}
		    
	    }
		
		return positionNote;
	}
	
	public int isIntoStroke(int x, int y) {
		PhotoComponentModel model = controller.getModel();
		List<Stroke> strokelist = model.getStrokes();
		//int i = 0;
		int positionStroke = -1;
		System.out.println("-----------------------------------------");
		System.out.println("Entered in method isIntoSTROKE");
		if (strokelist.size() >0) {
			for (int i = 0; i < strokelist.size();i++) {
				Stroke stroke = strokelist.get(i);
				System.out.println("Stroke number "+ i);
				
				System.out.println("HORIZONTAL: ("+ stroke.getMinX() + " ->" + stroke.getMaxX() + ") | real click:" + x);
				if (stroke.getMinX() <= x && x <= stroke.getMaxX()) {
					System.out.println("FIRST FILTER");
					System.out.println("VERTICAL: ("+ stroke.getMinY() + " ->" + stroke.getMaxY() + ") | real click:" + y);
					if (stroke.getMinY() <= y && y <= stroke.getMaxY()) {
						strokeSelected = true;
						positionStroke= i;
						System.out.println("Has clickado dentro del dibujo "+ i);
					}
				}
			}
		}
		
		return positionStroke;
	}
	
	/**
	 * Paints the back of the image in a white canvas that lets the user draw lines and write on it. 
	 * @param g: Graphics of the component
	 * @param component: The controller
	 */
	public void paintBackPicture (Graphics g, PhotoComponent component) {
		
		this.g = g;
		
		PhotoComponentModel model = controller.getModel();
		Image icon = model.getImage();
		Image backImage = model.getImage();
		
		if (backImage == null) {
			
			/*Code for drawing the blank canvas and drawing lines
			 * THIS AND THE KEYLISTENERS MODULES HAVE BEEN INSPIRED BY A WEBSITE TUTORIAL.
			 *See the full page here: http://www.ssaurel.com/blog/learn-how-to-make-a-swing-painting-and-drawing-application/
			 */
			backImage = controller.createImage(icon.getWidth(null), icon.getHeight(null));
		    g2 = (Graphics2D) backImage.getGraphics();
		    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
		}
		
		
		g.drawImage(backImage, 0, 0, null);
		
		//code for painting sentences
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
	   // g.setColor(Color.RED);
	    List<Note> notelist = model.getNotes();
	    if (notelist.size() > 0) {
	    	for (int i= 0; i < notelist.size(); i++) {
	    		Note note = notelist.get(i);
	    		//g.setColor(note.getColor());
	    		//drawString(g, note.getContent(), note.getX(), note.getY(), note.getColor());
	    	}
		    
	    }
	    
		//model.saveBackImage(backImage);
	}
	
	
	/**
	 * THIS FUNCTION HAS BEEN TAKEN FROM A STACKOVERFLOW POST IN ORDER TO PRINT STRINGS WITH DIFFERENT LINES.
	 * See the complete post here:  https://stackoverflow.com/questions/4413132/problems-with-newline-in-graphics2d-drawstring
	 * @param g
	 * @param text
	 * @param x
	 * @param y
	 */
	private void drawString(Graphics g, String text, int x, int y) {
		
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }

	/**
	 * Paints the image imported
	 * @param g
	 * @param component
	 */
	public void paint(Graphics g, PhotoComponent component) {
		this.g = g;
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		FontMetrics fontmetric = g.getFontMetrics();
		
	
		PhotoComponentModel model = controller.getModel();
		model.setFontMetrics(fontmetric);
		
		BufferedImage image = model.getImage();
		
		g2 = (Graphics2D) image.getGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    
		g.drawImage(image, 0,0,null);
		
		
	   // g.setColor(Color.black);
	    List<Note> notelist = model.getNotes();
	    if (notelist.size() > 0) {
	    	for (int i= 0; i < notelist.size(); i++) {
	    		Note note = notelist.get(i);
	    		g.setColor(note.getColor());
	    		drawString(g, note.getContent(), note.getX(), note.getY());
	    	}
		    
	    }
	    if( controller.isObjectSelected()) {
	    	Note note = model.getNotes().get(controller.getPositionSelected());
	    	g.setColor(Color.red);
			g.drawRect(note.getX(), note.getY(), note.getMaxX()- note.getX(), note.getEndy() - note.getY());
	    }else if (strokeSelected) {
	    	Stroke stroke = model.getStrokes().get(controller.getPositionSelected());
	    	g.setColor(Color.red);
	    	g.drawRect(stroke.getMinX(), stroke.getMinY(), stroke.getMaxX()- stroke.getMinX(), stroke.getMaxY()- stroke.getMinY());
	    }
		model.saveImage(image);
		
	}
	
	public Dimension getPreferredSize() {
		PhotoComponentModel model = controller.getModel();
		Image icon = model.getImage();
	
		return new Dimension(icon.getWidth(null), icon.getHeight(null)); 
	}

}
