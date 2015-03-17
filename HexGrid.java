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

public class HexGrid extends JPanel {

	// Number of tiles
	final static int BSIZEX = 9;
	final static int BSIZEY = BSIZEX;

	// Window size
	final static int WIDTH = 600;
	final static int HEIGHT = 600;

	// Position first tile (top left)
	static int x = (WIDTH / 2) - (int) (40 * 3.5);
	static int y = (HEIGHT / 2) - (int) (40 * 5.8);
	static int startX = x;
	static int startY = y;

	// Tile selected
	boolean selected = false;

	// Tiles clicked
	static ArrayList<Integer> clicked = new ArrayList<Integer>();

	// Adjacent tiles
	Rectangle adjacentCheck = new Rectangle();
	Rectangle rect = new Rectangle();
	private ArrayList<Integer> adjacentTiles = new ArrayList<Integer>();	

	// List of tiles and shape
	public static ArrayList<Shape> shapeList = new ArrayList<Shape>();
	private Polygon poly;

	private Point mouse = new Point();

	public HexGrid() {

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
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
		setBackground(new Color(60, 120, 230));
		g2.clearRect(0, 0, 800, 75);
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
		//System.out.println(getTileNum(mouse.x,mouse.y));
		int test = getTileNum(mouse.x,mouse.y);
		if (test > -1)
		{
			// get clicked tile and init var
			Shape s = shapeList.get(test);
			Shape temp;
			// get adjacent tiles
			adjacentTiles(test,s);

			// write
			g2.drawString("Tile: " + test, 10, 20);
			g2.drawString("Last clicked Tile: " + lastTile(), 10, 40);
			g2.drawString("Adjacent: " + adjacentTiles.toString(), 10, 60);
			
			// set paint brush
			g2.setStroke(new BasicStroke(3));
			g2.setColor(Color.RED);

			// paint selected tile flower
			if (selected) {
				for (int tilenext : adjacentTiles)
				{
					temp = shapeList.get(tilenext);
					g2.draw(temp);
				}
				g2.draw(s);	
				selected = true;	
			}

		}
	}

	// get tile num from xy location
	public int getTileNum(int x, int y) {
		// Check if mouse is in hexagon and return location/action
		for (int i = 0; i < shapeList.size(); i++) {
			Shape s = (Shape)shapeList.get(i);	
			// Rectangle rect = s.getBounds();
			if (s.contains(mouse.x, mouse.y) == true) {
				return i;
			}
		}
		return -1;
	}

	// Keeps track of clicked Tiles
	public static void clickCount(int i) {
		clicked.add(i);
	}

	// Return clicked tiles
	public static ArrayList<Integer> tilesClicked() {
		return clicked;
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

	/*
	 * Creates a rectangle that encapsulates the hexagon for the selected tile.
	 * Checks if this rectangle intersects with adjacent tiles relative to the 
	 * selected tile. Returns all adjacent tiles.
	 */
	public ArrayList<Integer> adjacentTiles(int selected, Shape s) {
		adjacentTiles = new ArrayList<Integer>();
		rect = s.getBounds();

		int x = (int) rect.getX();
		int y = (int) rect.getY() - 2;
		int h = (int) rect.getHeight() + 2;
		int w = (int) rect.getWidth();
		Rectangle rectCopy = new Rectangle(x, y, h, w);

		for (int i = 0; i < 60; i++) {
			Shape t = shapeList.get(i);
			adjacentCheck = t.getBounds();
			if (rectCopy.intersects(adjacentCheck) == true && selected != i) {
				adjacentTiles.add(i);
			}
		}
		return adjacentTiles;
	}

	public ArrayList<Integer> returnAdjacent()
	{
		return adjacentTiles;
	}

	public void setSelect(boolean mode)
	{
		selected = mode;
	}

	public void setMousePoint(Point mousepoint)
	{
		mouse = mousepoint;
	}

	public int[] getTileCoords(int j)
	{
		double x = shapeList.get(j).getBounds().getX()+20;
		double y = shapeList.get(j).getBounds().getY()-5;
		int[] xy = new int[2];
		xy[0] = (int) x;
		xy[1] = (int) y;
		return xy;
	}

	public static ArrayList<Shape> getTiles() {
		return shapeList;
	}

}
