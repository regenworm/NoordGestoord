/* Assignment 3, Datastructuren 2015
 * Authors: Alex Khawalid, 10634207
 * 			Philip Bouman, 10668667
 * Date: 09-02-2015
 */
import java.util.*;
import javax.swing.*;
import java.awt.*;

class Legends {
	private static int BOARD_SIZE = 50;
	private static int UNITS_PER_TEAM = 9;
	private static int WINDOW_HEIGHT = 800;
	private static int WINDOW_WIDTH = 800;
	
	// initiate game 
	public static void main(String[] args) 
	{
		Legends program = new Legends();
		program.initGame();
	}

	// initialize game representation
	private void initGame() 
	{
		// init unit representations
		OpenUnit[] teamnoord = createTeam("noordboiz");
		OpenUnit[] teampopos = createTeam("popos");

	}


	// type 	= type of unit
	// num 		= number of units
	// units 	= the units that are made
	// team 	= team of the units
	private OpenUnit[] createUnits(String type, int num, OpenUnit[] units, String team)
	{
		if (type.equals("Swordsman"))
		{
			for (int i = 0; i < units.length && num != 0; i++)
			{
				if (units[i] == null)
				{
					units[i] = new Swordsman(team);
					num--;
				}
			}
			return units;
		}
		else if (type.equals("General"))
		{
			for (int i = 0; i < units.length && num != 0; i++)
			{
				if (units[i] == null)
				{
					units[i] = new General(team);
					num--;
				}
			}
			return units;
		}
		else
		{
			return null;
		}
	}

	// Creates teams using createunits
	private OpenUnit[] createTeam(String team)
	{
		OpenUnit[] units = new OpenUnit[UNITS_PER_TEAM];
		units = createUnits("Swordsman",6,units,team);
		units = createUnits("General",3,units,team);
		return units;
	}

}