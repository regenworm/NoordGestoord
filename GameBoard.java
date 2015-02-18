import java.awt.*;
import javax.swing.*;

public class GameBoard extends JPanel {

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		drawHex(g);
		drawNoordboi(g);
	}

	public void drawNoordboi(Graphics g) {
		ImageIcon noordboi = new ImageIcon("noordboi.png");
		Image noordboiImage = noordboi.getImage();
		g.drawImage(noordboiImage, 400, 400, null);
	}

	// werkt heel slecht op kleine radius
	private Polygon createHexagon(int x, int y, int r)
	{
		Polygon hexagon = new Polygon();
		double tempx = x;
		double tempy = y;
		for(int i=0; i<6; i++) {
			tempx = (tempx + r*Math.cos(i*2*Math.PI/6));
			tempy = (tempy + r*Math.sin(i*2*Math.PI/6));
			x = (int) tempx;
			y = (int) tempy;

			System.out.println("Point "+ i);
			System.out.println(tempx + "\n" + tempy);
			System.out.println(x + "\n" + y);


			hexagon.addPoint(x,y);
		}
		return hexagon;
	}

	private void drawHex(Graphics g) 
	{
		Graphics2D g2d = (Graphics2D) g;

		// smooth drawing
		RenderingHints renderhints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		renderhints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHints(renderhints);

		// Get size window
		Dimension size = getSize();
		double x = size.getWidth();
		double y = size.getHeight();

		// make hexagons
		Polygon hexagon = createHexagon(300,400,3);
		g2d.setStroke(new BasicStroke(100));
		g2d.setColor(Color.gray);
		g2d.draw(hexagon);
	}


	public GameBoard()
	{
	}
}