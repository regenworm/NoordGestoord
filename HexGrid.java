// creates game arena
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HexGrid extends JApplet {

	final static int BSIZEX = 2;
	final static int BSIZEY = BSIZEX;
	final static int WIDTH = 600;
	final static int HEIGHT = 600;
	static int x = 100;
	static int y = 100;
	static int startX = x;
	static int startY = y;
	private Point mouse = new Point();
	static int[][] hexCords = new int[100][];

	public void init() {
      	MouseListener ml = new MouseListener();
      	addMouseListener(ml);
      	int[] xycords;										// coordinates of hexagonBASEX
		int counter = 0;
		for (int i = 0; i < BSIZEX*BSIZEX; i = i + BSIZEX) {
			for (int j = 0; j < BSIZEY; j++) {
				xycords = Hexagon.getCordXY(x, y);					// get coordinates of hexagon
				int k = i + j;
				hexCords[k] = xycords;						// store coordinates of hexagon

				y = Hexagon.hexColumnSouth(y);
			}
			y = startY;
			x = Hexagon.hexOffsetRow(x);
			if (counter % 2 == 0) {
				y = Hexagon.hexOffsetColumn(y);
			}
			counter++;
		}
		printHexCords(0);
		//hexCords[0] = null;

	}

	public static void printHexCords(int i) {
		System.out.print("Cords: " + i + ": ");
		for (int j = 0; j < 12; j++) {
			System.out.print(hexCords[i][j] + " , ");
		}
		System.out.println("");
	}

/*
	public HexGrid() {
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				mouse = e.getPoint();
				e.getComponent().repaint();
			}
		});
	}
*/	

	public void paint(Graphics g) {
	    Graphics2D g2 = (Graphics2D) g;
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	      	RenderingHints.VALUE_ANTIALIAS_ON);
	    setBackground(Color.BLUE);
	    g2.setPaint(Color.black);
	    x = startX;
	    y = startY;
		Hexagon.printBoard(hexCords, BSIZEX, g2);	
	}

/*

	    g2.drawString("contains(" + (mouse.x) + ", " + (mouse.y)
	    	+ ") is " + outline.contains(mouse.x, mouse.y), 10, 350);
	    if (outline.contains(mouse.x, mouse.y) == true) {
	        	g2.fill(outline);
	    }
*/
	

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
				// frame.setR3esizable(false);
				frame.pack();
				frame.setSize(new Dimension(WIDTH,HEIGHT));
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				createUI();
			}
		});
	}
	class MouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			System.out.println(x + " ," + y);
		}
	}
}
	