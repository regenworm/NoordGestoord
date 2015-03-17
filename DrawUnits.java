import javax.swing.*;
import java.awt.*;

public class DrawUnits extends JPanel {
	Graphics graphics;
	ImageIcon general = new ImageIcon("pics/noordboi.png");
	Integer[] unitlocations;

	private void DrawUnits(Integer[] list) {
		unitlocations = list;
		Image generalImage = general.getImage();
		graphics.drawImage(generalImage, 300, 400, null);
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		this.graphics = g;
		this.setBackground( new Color(100,100,20,64));
		this.setOpaque(false);
	}


	public void addUnitGraphics(Integer[] list)
	{
		unitlocations = list;
		repaint();
	}


}