import javax.swing.*;
import java.awt.*;

public class DrawUnits extends JPanel {
	Graphics graphics;

	private void drawGeneral(Graphics g) {
		ImageIcon general = new ImageIcon("pics/noordboi.png");
		Image generalImage = general.getImage();
		g.drawImage(generalImage, 300, 400, null);
	}

	@Override
	public void paint(Graphics g) {
		this.graphics = g;
		this.setBackground( new Color(100,100,20,64));
		this.drawGeneral(g);
		this.setOpaque(false);
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