import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.Image;

public class DrawUnits extends JPanel {
	private ImageIcon general = new ImageIcon("pics/noordboi.png");
	private OpenUnit[] noord;
	private OpenUnit[] popos;
	private ArrayList<int[]> xy;


	// paint units
	@Override
	public void paint(Graphics g) {
		this.setOpaque(false);
		Image imtemp;
		if (noord != null)
		{
			Iterator<int[]> it = xy.iterator();
			int[] temp;
			for (OpenUnit unit : noord)
			{
				if (unit == null)
				{
					continue;
				}
				temp = it.next();
				imtemp = unit.getImage();

				g.drawImage(imtemp,temp[0], temp[1], null);
			}
			for (OpenUnit unit : popos)
			{
				if (unit == null)
				{
					continue;
				}
				temp = it.next();
				imtemp = unit.getImage();

				g.drawImage(imtemp,temp[0], temp[1], null);
			}

		}
		
		repaint();
	}


	public void addUnitGraphics(OpenUnit[] noord, OpenUnit[] popos, ArrayList<int[]> xy)
	{	
		this.noord = noord;
		this.popos = popos;
		this.xy = xy;
	}


}