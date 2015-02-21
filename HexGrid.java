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
	    Graphics2D g2 = (Graphics2D) g;
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	      	RenderingHints.VALUE_ANTIALIAS_ON);
	    setBackground(Color.BLUE);
	    g2.clearRect(0, 0, 75, 25);
	    g2.setPaint(Color.black);
	   
	    // Set starting position
	    x = startX;
	    y = startY;
	    int n = 0;

	    // Iterate shapelist and draw objects
	    while(n < shapeList.size()) {
	    	Shape s = (Shape)shapeList.get(n);
	    	g2.setColor(Color.GREEN);
	    	g2.fill(s);
			g2.setColor(Color.BLACK);
	    	g2.draw(s);
	    	n++;
	    }

	    // Check if mouse is in hexagon and return location/ action
	    for (int i = 0; i < shapeList.size(); i++) {
	    	Shape s = (Shape)shapeList.get(i);
	    	
	    	if (s.contains(mouse.x, mouse.y) == true) {
	    		g2.drawString("Tile: " + i, 10, 20);
	    		clickCount(i);
	    		
	    		// Check if already slected
	   			if (selected == true && clicked.size() > 1  
	   					&& clicked.get(clicked.size() - 1) 
	   					== clicked.get(clicked.size() - 2)) {

	   				g2.setStroke(new BasicStroke(1));
	   				g2.setColor(Color.BLACK);
	   				g2.draw(s);
	   				clicked.remove(0);
	   				selected = false;

	   			// New selection	
	   			} else {
	   				g2.setStroke(new BasicStroke(5));
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
		System.out.println("clicked: " + clicked);
	}

	// return clicked tiles
	public static ArrayList<Integer> tilesClicked() {
		return clicked;
	}

	// Create interface
	public static void createUI() {
		JFrame frame = new JFrame("ARBOREA");

				frame.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						System.exit(0);
					}
				});

				JApplet applet = new HexGrid();
				frame.getContentPane().add(applet, BorderLayout.CENTER);
				applet.init();
				frame.setResizable(false);
				frame.pack();
				frame.setSize(new Dimension(WIDTH,HEIGHT));
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
	}

	// Run
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				createUI();
			}
		});
	}

	// Mouse click listener
	class MouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			mouse = e.getPoint();
			e.getComponent().repaint();
			int x = e.getX();
			int y = e.getY();

			System.out.println(x + " ," + y);
		}
	}
}
	