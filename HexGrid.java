// creates game arena
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;

public class HexGrid extends JApplet {

	final static int BSIZE = 3;
	final static int WIDTH = 800;
	final static int HEIGHT = 400;
	static int x = 120;
	static int y = 80;
	int startX = x;
	int startY = y;
	private Point mouse = new Point();
	private static Shape outline = Hexagon.hex(x, y);


	public void init() {
      	MouseListener ml = new MouseListener();
      	addMouseListener(ml);
      	
	}

	public HexGrid() {
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				mouse = e.getPoint();
				e.getComponent().repaint();
			}
		});
	}



	public void paint(Graphics g) {
	    Graphics2D g2 = (Graphics2D) g;
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	      	RenderingHints.VALUE_ANTIALIAS_ON);
	    setBackground(Color.BLUE);
	    g2.clearRect(0,HEIGHT - 75, 200,HEIGHT);
	    g2.setPaint(Color.black);

	    // 1st rows
	    
	    for (int i = 0; i < BSIZE; i++) {
	    	y = startY;
	    		    	for (int j = 0; j < BSIZE; j++) {
	    		Hexagon.drawHex(x, y, g2);
	    		y = Hexagon.hexColumnSouth(y);
	    	}
	    	
	    	outline = Hexagon.hex(x, y);
	    	g2.fill(outline);
	    	//x = Hexagon.hexRowEast(x);
	    }
	/*    
	    // 2nd rows
	    x = Hexagon.hexOffsetRow(startX);
	    for (int i = 0; i < BSIZE; i++) {
	    	y = Hexagon.hexOffsetColumn(startY);
	    	for (int j = 0; j < BSIZE; j++) {
	    		Hexagon.drawHex(x, y, g2);
	    		y = Hexagon.hexColumnSouth(y);
	    	}
	    	x = Hexagon.hexRowEast(x);
	    }
	   */ 
	    
	    g2.drawString("contains(" + (mouse.x) + ", " + (mouse.y)
	    	+ ") is " + outline.contains(mouse.x, mouse.y), 10, 350);

	}

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
	