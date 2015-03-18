import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.Image;

public class DrawUnits extends JPanel {
	// variables containing units that need to be drawn
	// and their locations in the frame
	private OpenUnit[] noord;
	private OpenUnit[] popos;
	private ArrayList<int[]> xy;


	// paint units
	// constantly keep painting all units
	// iterate through list of coordinates and list of units
	// using these units and coordinates draw them on the gameboard
	@Override
	public void paint(Graphics g) {
		// make background transparent
		this.setOpaque(false);

		// temporary container for images
		Image imtemp;

		// if teams are initialized
		if (noord != null)
		{
			// initialize an iterator to iterate through
			// xy coords  arraylist for units
			Iterator<int[]> it = xy.iterator();

			// temporary storage for xy coords
			int[] temp;

			// for each unit in both teams
			// get image from unit object and draw it
			// at xy coords stored in temp
			for (OpenUnit unit : noord)
			{
				// if a unit dies it is set to null in the
				// unit array, these units must be skipped
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
		
		// update visuals
		repaint();
	}

	// update unitinformation and location
	public void addUnitGraphics(OpenUnit[] noord, OpenUnit[] popos, ArrayList<int[]> xy)
	{	
		this.noord = noord;
		this.popos = popos;
		this.xy = xy;
	}


}