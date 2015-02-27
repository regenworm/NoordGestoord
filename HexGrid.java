/* Assignment 3, Datastructuren 2015
 * Authors: Alex Khawalid, 10634207
 * 			Philip Bouman, 10668667
 */

import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/* 
 * Creates a HexGrid and registers mouse movements
 */

public class HexGrid extends JApplet {
	public Graphics graphics;

	// Number of tiles
	final static int BSIZEX = 9;
	final static int BSIZEY = BSIZEX;

	// Window size
	final static int WIDTH = 600;
	final static int HEIGHT = 600;

	// Position first tile
	static int x = 100;
	static int y = 100;
	static int startX = x;
	static int startY = y;

	// Tile selected
	boolean selected = false;

	// Tiles clicked
	static ArrayList<Integer> clicked = new ArrayList<Integer>();

	// Adjacent tiles
	Rectangle adjacentCheck = new Rectangle();
	Rectangle rect = new Rectangle();
	private static boolean adjacent;


	private ArrayList<Shape> shapeList = new ArrayList<Shape>();
	private Polygon poly;
	private Point mouse = new Point();


	public void init() {
      	MouseListener ml = new MouseListener();
      	addMouseListener(ml);

      	// Create list and fill with hexagon shapes
		int[] xcords;
		int[] ycords; 
		int counter = 0;
		for (int i = 0; i < BSIZEX; i++) {
			for (int j = 0; j < BSIZEY; j++) {

				xcords = Hexagon.getCordX(x);
				ycords = Hexagon.getCordY(y);
			
				poly = new Polygon(xcords, ycords, 6);
				shapeList.add(poly);

				y = Hexagon.hexColumnSouth(y);
			}
			y = startY;
			x = Hexagon.hexOffsetRow(x);
			if (counter % 2 == 0) {
				y = Hexagon.hexOffsetColumn(y);
			}
			counter++;
		}

		// Delete tiles to create custom playing field
		int[] delTile = new int[] {0, 0, 5, 5, 5, 12, 11, 11, 
				18, 26, 43, 43, 50, 50, 56, 56, 56, 56, 61, 61};
		for (int i = 0; i < delTile.length; i++) {
			int del = delTile[i];
			shapeList.remove(del);
		}	
	}

	// Draw graphics 
	public void paint(Graphics g) {
		this.graphics = g;
	    Graphics2D g2 = (Graphics2D) g;
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	      	RenderingHints.VALUE_ANTIALIAS_ON);
	    setBackground(new Color(60, 120, 230));
	    g2.clearRect(0, 0, 700, 75);
	    g2.setPaint(Color.black);
	   
	    // Set starting position
	    x = startX;
	    y = startY;
	    int n = 0;

	    // Iterate shapelist and draw objects
	    while(n < shapeList.size()) {
	    	Shape s = (Shape)shapeList.get(n);
	    	g2.setStroke(new BasicStroke(3));
	    	g2.setColor(new Color(50, 250, 50));
	    	g2.fill(s);
			g2.setColor(new Color(10, 80, 10));
	    	g2.draw(s);
	    	n++;
	    }

	    // Check if mouse is in hexagon and return location/ action
	    for (int i = 0; i < shapeList.size(); i++) {
	    	Shape s = (Shape)shapeList.get(i);
	    	
	    	// Rectangle rect = s.getBounds();
	    	if (s.contains(mouse.x, mouse.y) == true) {
	    		g2.setColor(Color.BLACK);

	    		g2.drawString("Tile: " + i, 10, 20);
	    		g2.drawString("Last clicked Tile: " + lastTile(), 10, 40);
	    		
	    		clickCount(i);			// adds to list clicked tiles
	    		adjacentTiles(i, s);	// checkc adjacent tiles
	    		adjacentTiles2(i, s);

	    		g2.drawString("Adjacent: " + adjacenTile(), 10, 60);

	    		// Check if already slected
	   			if (selected == true && clicked.size() > 1
	   					&& clicked.get(clicked.size() - 1)
	   					== clicked.get(clicked.size() - 2)) {

	   				g2.setStroke(new BasicStroke(3));
	   				g2.setColor(new Color(10, 80, 10));
	   				g2.draw(s);
	   				selected = false;

	   			// New selection	
	   			} else {
	   				g2.setStroke(new BasicStroke(3));
	    			g2.setColor(Color.RED);
	    			g2.draw(s);	
	    			selected = true;	
	   			}
	    	}
	    }

	}

	// Keeps track of clicked Tiles
	public static void clickCount(int i) {
		clicked.add(i);
	}

	// Return clicked tiles
	public static ArrayList<Integer> tilesClicked() {
		return clicked;
	}

	// Returns true if tile is adjacent
	public static boolean adjacenTile() {
		return adjacent;
	}

	// Returns last Clicked Tile
	public static int lastTile() {
		int n;

		// insert exception ?
		if (clicked.size() < 1) {
			n = 0;
		} else {
			n = clicked.get(clicked.size() - 1);
		}	
		return n;
	}

	// Return last clicked tile for adjacentTiles
	public static int lastClicked() {
		int n;

		// insert exception ?
		if (clicked.size() < 2) {
			n = 0;
		} else {
			n = clicked.get(clicked.size() - 2);
		}	
		return n;
	}

	public ArrayList<Integer> adjacentTiles2(int selected, Shape s) {
		ArrayList<Integer> adjacent = new ArrayList();
		rect = s.getBounds();

		int x = (int) rect.getX();
		int y = (int) rect.getY() - 2;
		int h = (int) rect.getHeight() + 2;
		int w = (int) rect.getWidth();
		Rectangle rectCopy = new Rectangle(x, y, h, w);

		for (int i = 0; i < 60; i++) {
			Shape t = shapeList.get(i);
			adjacentCheck = t.getBounds();
			if (rectCopy.intersects(adjacentCheck) == true) {
				adjacent.add(i);
			}
		}
		System.out.println(adjacent.toString());
		return adjacent;
	}

	/*
	 * Creates rectangle that encapsulates the hexagon for the selected tile.
	 * Checks if this rectangle intersects with the polygon from the previous
	 * selected tile. Set boolean adjacent.
	 */
	public void adjacentTiles(int selected, Shape s) {
		rect = s.getBounds();

		int x = (int) rect.getX();
		int y = (int) rect.getY() - 2;
		int h = (int) rect.getHeight() + 2;
		int w = (int) rect.getWidth();
		Rectangle rectCopy = new Rectangle(x, y, h, w);

		Shape t = shapeList.get(lastClicked());
		adjacentCheck = t.getBounds();

		// set boolean
		adjacent = rectCopy.intersects(adjacentCheck);
	}

	// Mouse click listener
	class MouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			mouse = e.getPoint();
			e.getComponent().repaint();
			int x = e.getX();
			int y = e.getY();
		}
	}


	private void drawGeneral(Graphics g) {
		ImageIcon general = new ImageIcon("pics/noordboi.png");
		Image generalImage = general.getImage();
		g.drawImage(generalImage, 300, 400, null);
	}


	public Image addUnitGraphics(String type, int posx, int posy)
	{
		Image imObject;
		if (type.equals("Swordsman"))
		{
			ImageIcon soldier =  new ImageIcon("pics/noordboi.png");
			imObject = soldier.getImage();
			this.graphics.drawImage(imObject, posx, posy, null);
		}
		else //(type.equals("General"))
		{
			ImageIcon general =  new ImageIcon("pics/Noord_G.png");
			imObject = general.getImage();
			this.graphics.drawImage(imObject, posx, posy, null);
		}
		return imObject;
	}
}
	