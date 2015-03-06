import javax.swing.*;
import java.awt.*;

public class DrawUnits extends JPanel {
	Graphics graphics;

	private void drawGeneral(Graphics g) {
		ImageIcon general = new ImageIcon("pics/noordboi.png");
		Image generalImage = general.getImage();
		g.drawImage(generalImage, 300, 400, null);
	}

	public void paint(Graphics g) {
		this.graphics = g;
	}

	public Image addUnitGraphics(String type, int posx, int posy)
	{
		Image imObject;
		if (type.equals("Swordsman"))
		{
			ImageIcon soldier =  new ImageIcon("pics/noordboi.png");
			imObject = soldier.getImage();
			graphics.drawImage(imObject, posx, posy, null);
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