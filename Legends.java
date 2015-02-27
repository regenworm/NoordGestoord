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
			int j;			
			if(team == "noordboiz")
			{
				j = 5;
			}
			else
			{
				j = 50;
			}
			for (int i = 0; i < units.length && num != 0; i++)
			{
				if (units[i] == null)
				{
					units[i] = new Swordsman(team);

					units[i].moveUnit(j);
					num--;
					j++;
				}
			}
			return units;
		}
		else if (type.equals("General"))
		{	
			int j;				
			if(team == "noordboiz")
			{
				j = 0;
			}
			else
			{
				j = 56;
			}
			for (int i = 0; i < units.length && num != 0; i++)
			{
				if (units[i] == null)
				{
					units[i] = new General(team);
					units[i].moveUnit(j);
					j+= 2;
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
		int[] xy;
		int j = 0;
		
		for (OpenUnit unit : teamnoord)
		{
			j = unit.getTileNum();
			xy = applet.getTileCoords(j);
			unit.setImage(applet.addUnitGraphics(unit.getType(), xy[0], xy[1]));
		}

		for (OpenUnit unit : teampopos)
		{
			j = unit.getTileNum();
			xy = applet.getTileCoords(j);
			unit.setImage(applet.addUnitGraphics(unit.getType(), xy[0], xy[1]));
		}

	}

	private void takeTurns()
	{
		Scanner userInputScanner = new Scanner(System.in);
		System.out.println("Turn :  Team Noord");
		int tilej = userInputScanner.nextInt();
		int desty = userInputScanner.nextInt();

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