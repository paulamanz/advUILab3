import java.awt.Color;
import java.awt.FontMetrics;

public class Note {
	//X and Y saves the position in which the note has started to be written
	private int x;
	private int y;
	private int endx;
	private int endy;
	private int maxX;
	private int linesNumber;
	
	//Content of the note
	private String content;
	
	//Saves the last position that could be used as a break for the line
	private Integer possibleStopPos;
	
	//Saves the position of the last break of line
	private Integer lastBreak;
	
	private FontMetrics font;
	
	//Saves the color of the note
	private Color noteColor;

	public Note(int x, int y, FontMetrics font) {
		this.x = x;
		this.y = y;
		this.endx=x ;
		this.endy=y +font.getHeight();
		this.content="";
		this.linesNumber=1;
		this.font = font;
		
	}
	
	public Note (int x, int y, String content) {
		this.x=  x;
		this.y = y;
		this.content=content;
	}
	
	
	/**
	 * Adds a new character to the note.
	 * @param c
	 */
	public void addChar(Character c) {
		if(c.equals(' ')) {
			//If the character is a space it is saved as a possible break 
			possibleStopPos = (this.content.length());
			
		}
		this.content = content + c;
		this.endx= this.endx + font.charWidth(c);
		if( this.maxX < this.endx) {
			this.maxX = this.endx;
		}
		//this.endx = this.endx + (this.linesNumber-1)*font.getHeight();
		System.out.println("Last line: (" + this.endx + ", "+ this.endy+")");
	}

	public int getMaxX() {
		return maxX;
	}

	public void setMaxX(int maxy) {
		this.maxX = maxy;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		int difference =  x - this.x ;
		this.x = x;
		this.maxX = this.maxX + difference;
		this.endx = this.endx + difference;
		System.out.println(x + " - "+ this.maxX);
	}

	public int getY() {
		return y;
	}
	

	public void setY(int y) {
		int difference =  y - this.y;
		this.y = y;
		this.endy= this.endy + difference;
		
	}
	
	public int getEndx() {
		return endx;
	}

	public void setEndx(int endx) {
		this.endx = endx;
	}

	public int getEndy() {
		return endy;
	}

	public void setEndy(int endy) {
		this.endy = endy;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public int getSpacePosition() {
		return this.possibleStopPos;
	}
	
	public Color getColor() {
		return this.noteColor;
	}
	
	public void setColor (Color color) {
		this.noteColor = color;
	}
	/**
	 * Breaks the note to a new line when the note is arriving to the border.
	 */
	public void breakNote() {
		// if there is a space in the line it changes the space for a \n, which changes the line there.
		if (this.possibleStopPos != null) {
			
			this.content = this.content.substring(0, possibleStopPos) + '\n' + content.substring(possibleStopPos+1);
			
			//Updates the position of the last break of line
			this.lastBreak = this.possibleStopPos +1;
			//Updates the possible stops
			this.possibleStopPos = null;
			
		}else{
			//If there is not a space, a - is added at the previous position and a \n is added aswell. The text will continue in the next line.
			this.content = this.content.substring(0, content.length()-1)+ '-' + '\n'+ this.content.substring(content.length()-1, content.length());
			
			//Updates the position of the last break of line
			this.lastBreak = this.content.length()+1;
		}
		this.linesNumber++;
		this.endx= this.x;
		this.endy= this.endy  +font.getHeight();
		
	}
	
	/**
	 * Calculates the size of the last line of the note
	 * @return size of last line. 
	 */
	public int getLineSize() {
		String line;
		int lineSize;
		
		//If never has been a line break, the size of the line is the size of the whole note
		if (this.lastBreak == null) {
			//lineSize = this.content.length();
			lineSize = font.stringWidth(this.content);
		}else {
			//If there has been a line break, the size is the one of last line. 
			//lineSize = this.content.length() - this.lastBreak;
			line = this.content.substring(lastBreak- 1);
			//System.out.println(line);
			lineSize= font.stringWidth(line);
			
		}
		return lineSize;
	}
	
	
}
