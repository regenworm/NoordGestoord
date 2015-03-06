/* Assignment 3, Datastructuren 2015
 * Authors: Alex Khawalid, 10634207
 * 			Philip Bouman, 10668667
 * Date: 09-02-2015
 */
import java.util.*;
import javax.swing.*;
import java.awt.*;

class Legends {
	private static int BOARD_SIZE = 61;
	private static int UNITS_PER_TEAM = 9;
	private static int WINDOW_HEIGHT = 800;
	private static int WINDOW_WIDTH = 800;

	private OpenUnit[] teamnoord;
	private OpenUnit[] teampopos;
	private Integer[] unitlocations = new Integer[BOARD_SIZE];
	
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
			if(team == "noord")
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
					unitlocations[j] = i;
					num--;
					j++;
				}
			}
			return units;
		}
		else if (type.equals("General"))
		{	
			int j;				
			if(team == "noord")
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
					unitlocations[j] = i;
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
		HexGrid gameboard = new HexGrid();
		DrawUnits unitlayer = new DrawUnits();
		unitlayer.setOpaque(false);
		unitlayer.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		gameboard.init();

        

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        layeredPane.add(gameboard, new Integer(50));
        layeredPane.add(unitlayer, new Integer(51));


        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        //frame.add( gameboard );
        frame.pack();
        frame.setVisible( true );
        frame.setResizable(false);
        frame.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.add(layeredPane);

        teamnoord = createTeam("noord");
		teampopos = createTeam("poops");
		int[] xy;
		int j = 0;
		
		
		for (OpenUnit unit : teamnoord)
		{
			j = unit.getTileNum();
			xy = gameboard.getTileCoords(j);
			unit.setImage(unitlayer.addUnitGraphics(unit.getType(), xy[0], xy[1]));
		}

		for (OpenUnit unit : teampopos)
		{
			j = unit.getTileNum();
			xy = gameboard.getTileCoords(j);
			unit.setImage(unitlayer.addUnitGraphics(unit.getType(), xy[0], xy[1]));
		}



/*
		JLayeredPane layers = new JLayeredPane();		HexGrid gameboard = new HexGrid();

		// init gameboard
		gameboard.init();

		// add layers
		layers.setPreferredSize(new Dimension(WINDOW_HEIGHT,WINDOW_WIDTH));
        layeredPane.setBorder(BorderFactory.createTitledBorder(
                            "Move the Mouse to Move Duke"));
		layers.add(gameboard, 2);
		layers.add(unitlayer, 1);
		layers.setVisible(true);
		System.out.println(layers.getLayer(unitlayer));
		System.out.println(layers.getLayer(gameboard));


		//OpenUnit temp = checkUnit(j);
		gameLoop();*/

	}

	private void takeTurns()
	{
		int movesleft = 2;
		int tilej;
		int desty;
		Scanner userInputScanner = new Scanner(System.in);
		System.out.println("Turn :  Team Noord");
		while (movesleft != 0)
		{
			System.out.println("Which piece do you want to move?\n");
			tilej = userInputScanner.nextInt();
			System.out.println("Where do you want to move it?\n");
			desty = userInputScanner.nextInt();

			tilej = unitlocations[tilej];
			teamnoord[tilej].moveUnit(desty);
			movesleft--;
		}

		System.out.println("Turn :  Team Popos");
		while (movesleft != 0)
		{
			System.out.println("Which piece do you want to move?\n");
			tilej = userInputScanner.nextInt();
			while (unitlocations[tilej] == null)
			{	
				System.out.println("Which piece do you want to move?\n");
				tilej = userInputScanner.nextInt();
			}

			System.out.println("Where do you want to move it?\n");
			desty = userInputScanner.nextInt();
			boolean adjacent = true;
			while (adjacent)
			{
				System.out.println("Where do you want to move it?\n");
				desty = userInputScanner.nextInt();
				if (unitlocations[desty] == null)
				{
					tilej = unitlocations[tilej];
					teampopos[tilej].moveUnit(desty);
					movesleft--;
				}
				else if (unitlocations != null)
				{
					tilej = unitlocations[tilej];
					desty = unitlocations[desty];
					teampopos[tilej].attack(teamnoord[desty]);
				}
			}
		}
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
	}
}