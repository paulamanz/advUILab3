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
		Point p;
		this.maxX = this.minX = (int) points.get(0).getX();
		this.maxY = this.minY = (int) points.get(0).getY();
		for (int i = 1; i< points.size();i++) {
			p = points.get(i);
			if(p.getX() > this.maxX) {
				this.maxX = (int) p.getX();
			}else if (p.getX() < this.minX) {
				this.minX = (int) p.getX();
			}
			
			if (p.getY() > this.maxY) {
				this.maxY = (int) p.getY();
			}else if (p.getY() < this.minY) {
				this.minY = (int) p.getY();
			}
			
		}
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
