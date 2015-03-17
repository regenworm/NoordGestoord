/* Assignment 3, Datastructuren 2015
 * Authors: Alex Khawalid, 10634207
 * 			Philip Bouman, 10668667
 * Date: 09-02-2015
 */
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Legends {
	private static int BOARD_SIZE = 61;
	private static int UNITS_PER_TEAM = 9;
	private static int WINDOW_WIDTH = 800;	
	private static int WINDOW_HEIGHT = 750;
	private int currentturn = 1;
	private OpenUnit[] teamnoord;
	private OpenUnit[] teampopos;
	private Integer[] unitlocations = new Integer[BOARD_SIZE];
	public int clickonce = 0;
	public int[] tiles = new int[2];
	public HexGrid gameboard;
	public DrawUnits unitlayer;
	public OpenUnit selectedUnit;

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
		// create swordsmen
		if (type.equals("Swordsman"))
		{		
			int j;			
			// add locations to class and list of locations
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
					units[i] = new Swordsman(team,i);

					units[i].moveUnit(j);
					if (team == "noord") {
						unitlocations[j] = i;
					} else {
						unitlocations[j] = i-12;
					}
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
					units[i] = new General(team,i);
					units[i].moveUnit(j);
					if (team == "noord") {
						unitlocations[j] = i;
					} else {
						unitlocations[j] = i-12;
					}
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

	private void resetMovesLeft()
	{
		for (OpenUnit unit : teamnoord) {
			unit.resetMoves();
		}

		for (OpenUnit unit : teampopos) {
			unit.resetMoves();
		}
	}

	// Create interface
	public void createUI() {
		JFrame frame = new JFrame("Noord Gestoord: THE GAME");
		Container c = frame.getLayeredPane();
		gameboard = new HexGrid();
		unitlayer = new DrawUnits();
		AStar pathfind = new AStar();

		// next turn: create button and container add to layeredpanel
		JPanel buttoncontainer = new JPanel();
		JButton nextturn = new JButton("Next Turn");
		buttoncontainer.setBounds(WINDOW_WIDTH-100, 0, 100, 75);
		buttoncontainer.add(nextturn);
		//buttoncontainer.setBackground(new Color(60, 120, 230));
		c.add(buttoncontainer, new Integer(3));

		// next turn: reset moves left for units and change turn
		nextturn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				resetMovesLeft();
				currentturn *= -1;
			}
		});

		MouseListener mltop = new MouseListener();
		gameboard.addMouseListener(mltop);
		
		// create board gui
		//gameboard.init();
		c.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		c.add(gameboard, new Integer(1));		
		c.add(unitlayer, new Integer(2));
		unitlayer.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		gameboard.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);


		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.pack();
		frame.setVisible( true );
		frame.setResizable(false);
		frame.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		frame.setLocationRelativeTo(null);
		ArrayList<int[]> xy = getCoordsTeams(gameboard);
		unitlayer.addUnitGraphics(teamnoord,teampopos,xy);
	}

	private ArrayList<int[]> getCoordsTeams(HexGrid gameboard)
	{
		ArrayList<int[]> xy = new ArrayList<int[]>();
		for (OpenUnit unit : teamnoord)
		{
			xy.add(gameboard.getTileCoords(unit.getTileNum()));
		}
		for (OpenUnit unit : teampopos)
		{
			xy.add(gameboard.getTileCoords(unit.getTileNum()));
		}
		return xy;
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

	// initialize game representation
	private void initGame() 
	{	
		// create teams
		teamnoord = createTeam("noord");
		teampopos = createTeam("popos");

		// create UI
		createUI();
	}

	// Mouse click listener
	class MouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			// get tilenumber and set mouse points in gameboard
			gameboard.setMousePoint(e.getPoint());
			int tilenum = gameboard.getTileNum(e.getX(),e.getY());

			// get unit to take action with
			if (clickonce < 1)
			{
				Integer tempf = unitlocations[tilenum];
				if (tempf != null )
				{
					if (tempf < 0)
					{
						selectedUnit = teampopos[tempf+12];
					} else {
						selectedUnit = teamnoord[tempf];
					}

					clickonce++;
				}

			} // if second valid click move selected unit to new location
			else {
				// move unit
				selectedUnit.moveUnit(tilenum);
				selectedUnit = null;
				clickonce = 0;

			}
			if (tilenum > -1 )
			{
				gameboard.clickCount(tilenum);
			}

			ArrayList<int[]> xy = getCoordsTeams(gameboard);
			unitlayer.addUnitGraphics(teamnoord,teampopos,xy);
		}
	}

}