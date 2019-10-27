import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Stroke {
	
	private Color color;
	private List<Point> points ;
	private int maxX;
	private int minX;
	private int maxY;
	private int minY;
	
	public Stroke() {
		this.points = new ArrayList<Point>();
	}
	
	public Stroke(int x, int y) {
		this.points = new ArrayList<Point>();
		this.maxX = this.minX= x;
		this.minY = this.maxY = y;
		this.addPoint(x, y);
	}
	
	public void addPoint(int x, int y) {
		this.points.add(new Point(x,y));
		
		if(x > this.maxX) {
			this.maxX = x;
		}else if (x < this.minX) {
			this.minX = x;
		}
		
		if (y > this.maxY) {
			this.maxY = y;
		}else if (y < this.minY) {
			this.minY = y;
		}
	}
	
	
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public List<Point> getPoints() {
		return points;
	}
	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMinX() {
		return minX;
	}

	public int getMaxY() {
		return maxY;
	}

	public int getMinY() {
		return minY;
	}
	
	
	
	
	

}
