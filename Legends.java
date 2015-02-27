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

	private OpenUnit[] teamnoord;
	private OpenUnit[] teampopos;
	
	// initiate game 
	public static void main(String[] args) 
	{
		Legends program = new Legends();
		program.initGame();
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

	// Create interface
	public void createUI() {
		JFrame frame = new JFrame("Noord Gestoord: THE GAME");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		HexGrid applet = new HexGrid();
		frame.getContentPane().add(applet, BorderLayout.CENTER);
		applet.init();
		frame.setResizable(false);
		frame.pack();
		frame.setSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		teamnoord = createTeam("noordboiz");
		teampopos = createTeam("popoow");
		int x = 500;
		int y = 70;

		for (OpenUnit unit : teamnoord)
		{
			y += 55;
			unit.setImage(applet.addUnitGraphics(unit.getType(), x, y));
		}

		x = 100;
		y = 70;
		for (OpenUnit unit : teampopos)
		{
			y+= 55;
			unit.setImage(applet.addUnitGraphics(unit.getType(), x, y));
		}

	}

	private void takeTurns()
	{
		System.out.println("Turn : " );
	}

	private boolean checkWin()
	{
		boolean winner = true;
		for (OpenUnit unit : teamnoord)
		{
			if ( unit != null)
			{
				winner = false;
				break;
			}
		}

		for (OpenUnit unit : teampopos)
		{
			if ( unit != null)
			{
				winner = false;
				break;
			}
		}
		return winner;
	}

	private void gameLoop()
	{
		boolean winner = false;
		while(checkWin())
		{
			takeTurns();
			checkWin();
		}
	}

	// initialize game representation
	private void initGame() 
	{	
		createUI();
		//gameLoop();
	}
}