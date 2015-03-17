/* Assignment 3, Datastructuren 2015
 * Authors: Alex Khawalid, 10634207
 * 			Philip Bouman, 10668667
 * Date: 09-02-2015
 */
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// main class, start this to start the game
class Legends {
	// number of tiles on gameboard
	private static int BOARD_SIZE = 61;

	// number of units per team playing
	private static int UNITS_PER_TEAM = 9;

	// width of window
	private static int WINDOW_WIDTH = 800;	

	// height of window
	private static int WINDOW_HEIGHT = 750;
	
	// if currentturn is 1 teamnoord is playing
	// otherwise the AI is playing
	private int currentturn = 1;

	// array of units for each team
	// these units are also used to create visual
	// representations on the gui
	private OpenUnit[] teamnoord;
	private OpenUnit[] teampopos;

	// list of tiles that are occupied
	// the index of the array is the tilenumber on the board
	// the value corresponds to the index in the array of units
	// if the value is below 0, add 12 to the value to get the
	// corresponding index for teampopos (values below 0 are teampopos)
	// if the value is 0 or above, this corresponds to the
	// index for teamnoord (values above 0 are teamnoord units)
	private Integer[] unitlocations = new Integer[BOARD_SIZE];
	
	// determines whether this is the first click or the second
	public int clickonce = 0;

	// gameboard + gameboard gui
	public HexGrid gameboard;

	// layer on which units are drawn
	public DrawUnits unitlayer;

	// this is the currently selected unit
	public OpenUnit selectedUnit;

	// initiate game
	// no commandline arguments possible
	public static void main(String[] args) 
	{
		Legends program = new Legends();
		program.initGame();
	}

	// create an array of units
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
			// swordsmen start at the 5th tile for noord
			// swords
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
			if (unit == null)
			{
				continue;
			}
			xy.add(gameboard.getTileCoords(unit.getTileNum()));
		}
		for (OpenUnit unit : teampopos)
		{
			if (unit == null)
			{
				continue;
			}
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

	// if unit is at tilenum, put as selectedUnit
	private void selectUnit(int tilenum)
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

		boolean setsel = true;
		gameboard.setSelect(setsel);
	}

	// given tilenumber a selected unit is moved or attacks
	private void moveOrAttack(int tilenum)
	{
		ArrayList<Integer> adjacentTiles = gameboard.returnAdjacent();
		String currentteam = selectedUnit.getTeam();
		
		// quit if not turn of current unit team
		if (currentteam == "noord")
		{
			if (currentturn == -1)
			{
				return;
			}
		} else if (currentturn == 1){
			return;
		}


		// quit if selected destination not adjacent
		if (!adjacentTiles.contains(tilenum) )
		{
			return;
		}

		// if destination is empty
		if (unitlocations[tilenum] == null)
		{
			// move unit if moves left
			if (selectedUnit.movesLeft() > 0) {
				// remove old location from unit list
				unitlocations[selectedUnit.getTileNum()] = null;

				// move unit
				selectedUnit.moveUnit(tilenum);

				// update unitlist dependent on team
				if (currentteam == "noord"){
					unitlocations[tilenum] = selectedUnit.getNum();
				} else {
					unitlocations[tilenum] = selectedUnit.getNum()-12;
				}
			}
		} else {
			// if clicked location is not empty
			// quit if attacking same team
			OpenUnit temp = selectedUnit;
			selectUnit(tilenum);
			if (currentteam == selectedUnit.getTeam())
			{
				return;
			}

			if (temp.attack(selectedUnit))
			{
				unitlocations[selectedUnit.getTileNum()] = null;
				if (currentteam == "noord")
				{
					teampopos[selectedUnit.getNum()] = null;

				} else {
					teamnoord[selectedUnit.getNum()] = null;
				}
			}
		}

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
				selectUnit(tilenum);

			} // if second valid click move selected unit to new location
			else {
				moveOrAttack(tilenum);
				
				// deselect everything
				selectedUnit = null;
				clickonce = 0;
				boolean setsel = false;
				gameboard.setSelect(setsel);
			}

			// if any tile is clicked add to clickcount
			if (tilenum > -1 )
			{
				gameboard.clickCount(tilenum);
			}

			// update units on gameboard
			ArrayList<int[]> xy = getCoordsTeams(gameboard);
			unitlayer.addUnitGraphics(teamnoord,teampopos,xy);
		}
	}

}