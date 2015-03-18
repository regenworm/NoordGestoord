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

	// container which contains all jpanels
	Container c;

	// gameboard + gameboard gui
	public HexGrid gameboard;

	// layer on which units are drawn
	public DrawUnits unitlayer;

	// this is the currently selected unit
	public OpenUnit selectedUnit;

	// layer on which info is displayed
	public JLabel info;
	public JLabel battle;

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
			// create swordsmen and add locations,add to list of locations(int[] unitlocations) and number in array of units
			// 
			for (int i = 0; i < units.length && num != 0; i++)
			{
				// if an entry is use it
				if (units[i] == null)
				{
					units[i] = new Swordsman(team,i);

					units[i].moveUnit(j);
					if (team == "noord") {
						unitlocations[j] = i;
					} else {
						// if teampopos decrement value bij 12 so it is under 0 and 
						// recognizable as teampopos units
						unitlocations[j] = i-12;
					}
					num--;
					j++;
				}
			}
			// return array to which units are now added
			return units;
		} // principle of swordsman applies to general as well
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
					if (team == "noord") 
					{
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

	// reset the number of moves a unit can make
	private void resetMovesLeft()
	{
		// for each unit on each team reset the moves that can be made
		for (OpenUnit unit : teamnoord) 
		{
			if (unit != null) {
				unit.resetMoves();
			}
		}

		for (OpenUnit unit : teampopos) 
		{
			if (unit != null) {
				unit.resetMoves();
			}
		}
	}

	// Create interface
	public void createUI() 
	{
		// init variables
		JFrame frame = new JFrame("Noord Gestoord: THE GAME");

		// container for jpanels
		c = frame.getLayeredPane();

		// background
		ImageIcon background = new ImageIcon("pics/bg.jpg");
		JPanel bgpanel = new JPanel();
		bgpanel.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		JLabel bglabel = new JLabel();
		bglabel.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		bglabel.setIcon(background);
		bgpanel.add(bglabel);
		c.add(bgpanel, new Integer(0));



		// jpanels
		gameboard = new HexGrid();
		unitlayer = new DrawUnits();

		// make background transparent
		gameboard.setOpaque(false);

		// ai
		// AStar pathfind = new AStar();

		// create a button to go to next turn and make background transparent
		JPanel buttoncontainer = new JPanel();
		JButton nextturn = new JButton("Next Turn");
		buttoncontainer.setBounds(WINDOW_WIDTH-100, 0, 100, 75);
		buttoncontainer.add(nextturn);
		buttoncontainer.setOpaque(false);
		c.add(buttoncontainer, new Integer(3));

		// create a window for extra info
		info = new JLabel("Info");
		info.setOpaque(true);
		info.setVerticalAlignment(SwingConstants.TOP);
		info.setBounds(0, 75, 100, WINDOW_HEIGHT-75);
		c.add(info, new Integer(3));

		// create a window for battle info
		battle = new JLabel("Battle:");
		battle.setOpaque(true);
		battle.setVerticalAlignment(SwingConstants.TOP);
		battle.setBounds(0, 75+(WINDOW_HEIGHT/2), 100, (WINDOW_HEIGHT/2)-75);
		c.add(battle, new Integer(4));

		// add listener to button for next turn
		// for every turn moves are reset and 
		// team is switched
		nextturn.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				resetMovesLeft();
				AStar.findPath();
				currentturn *= -1;
				checkWin();
			}
		});

		// add mouselistener to gameboard
		MouseListener mltop = new MouseListener();
		gameboard.addMouseListener(mltop);
		
		// create board gui
		// add jpanels to container
		c.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		c.add(gameboard, new Integer(1));		
		c.add(unitlayer, new Integer(2));
		unitlayer.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		gameboard.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

		// finish frame
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.pack();
		frame.setVisible( true );
		frame.setResizable(false);
		frame.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		frame.setLocationRelativeTo(null);

		// update gui units
		ArrayList<int[]> xy = getCoordsTeams(gameboard);
		unitlayer.addUnitGraphics(teamnoord,teampopos,xy);
	}

	// get x,y coords for all units from all teams in specific order
	// when units die, their place in the array is set to null
	// these null units must be skipped otherwise exceptions
	// will occur
	// the x and y coordinates are given back in an arraylist
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


	// check if there is a winner
	// if one of the teamarrays is completely set to null
	// other team is winner
	private boolean checkWin()
	{
		boolean winner = true;
		String winteam = "";
		for (OpenUnit unit : teamnoord)
		{
			if ( unit != null)
			{
				winner = false;
				break;
			}
			winteam = "Noord";
		}
		if (!winner){
			winner = true;
			for (OpenUnit unit : teampopos)
			{
				if ( unit != null)
				{
					winner = false;
					break;
				}
				winteam = "Popos";
			}
		}

		if (winner)
		{
			JPanel win = new JPanel();
			JLabel winstring = new JLabel("<html>The winning team is:<br>"+ winteam+"</html>");
			win.add(winstring);
			win.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
			c.add(win, new Integer(5));

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

	// if unit is at tilenumber, put as selectedUnit
	private void selectUnit(int tilenum)
	{
		Integer tempf = unitlocations[tilenum];

		// if value in unitlocation at tilenumber is not null
		// fetch unit from unit array
		if (tempf != null )
		{

			if (tempf < 0)
			{
				selectedUnit = teampopos[tempf+12];
			} else {
				selectedUnit = teamnoord[tempf];
			}

			//update info
			info.setText("<html> Info:<br>Selected Unit HP: " + selectedUnit.getHp() + "<br>Moves left: " + selectedUnit.movesLeft() + "<br><html>" );

		}

		// set select to true since a unit has been selected
		// update on gui as well
		boolean setsel = true;
		gameboard.setSelect(setsel);
	}

	// calculate adjacency bonus
	private void calcBonus( ArrayList<Integer> adjacentTiles)
	{
		// if teamnoord's turn
		if (currentturn == 1)
		{
			// for every adjacent ally
			for (OpenUnit unit : teamnoord)
			{
				if (unit != null)
				{
					// increment adjacency bonus
					if (adjacentTiles.contains(unit.getTileNum()))
					{
						selectedUnit.adjustAdjacencyBonus(1);
						if (unit.getType() == "General")
						{
							selectedUnit.adjustAdjacencyBonus(1);
						}
					}
				}
			}// for every adjacent enemy
			for (OpenUnit unit : teampopos)
			{
				if (unit != null)
				{
					// decrement adjacency bonus
					if (adjacentTiles.contains(unit.getTileNum()))
					{
						selectedUnit.adjustAdjacencyBonus(-1);
						if (unit.getType() == "General")
						{
							selectedUnit.adjustAdjacencyBonus(-1);
						}
					}
				}
			}
		} else {
			// same principle applies for turn of other team
			for (OpenUnit unit : teampopos)
			{
				if (unit != null)
				{
					if (adjacentTiles.contains(unit.getTileNum()))
					{
						selectedUnit.adjustAdjacencyBonus(1);
						if (unit.getType() == "General")
						{
							selectedUnit.adjustAdjacencyBonus(1);
						}
					}
				}
			}
			for (OpenUnit unit : teamnoord)
			{
				if (unit != null)
				{
					// increment adjacency bonus for every
					if (adjacentTiles.contains(unit.getTileNum()))
					{
						selectedUnit.adjustAdjacencyBonus(-1);
						if (unit.getType() == "General")
						{
							selectedUnit.adjustAdjacencyBonus(-1);
						}
					}
				}
			}
		}
	}

	// given tilenumber a selected unit is moved or attacks
	private void moveOrAttack(int tilenum)
	{
		// get adjacent tiles and name of team whose turn it is
		ArrayList<Integer> adjacentTiles = gameboard.returnAdjacent();
		String currentteam = selectedUnit.getTeam();
		
		// quit if not turn of current unit team
		// if noord's turn, currentturn should be 1
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


				// add info about enemy to info label
				String infostring = info.getText();
				infostring = infostring.substring(0, infostring.length()-5);
				info.setText("<html> Info:<br>Selected Unit HP: " + selectedUnit.getHp() + "<br>Moves left: " + selectedUnit.movesLeft() +  "<br><html>" );


				// update unitlist dependent on team
				if (currentteam == "noord"){
					unitlocations[tilenum] = selectedUnit.getNum();
				} else {
					unitlocations[tilenum] = selectedUnit.getNum()-12;
				}
			}
		} else {
			// if clicked location is not empty
			// get adjacency bonus for current unit
			calcBonus(adjacentTiles);


			// save currently selected unit
			// get unit at destination tile and put it as selectedUnit
			OpenUnit temp = selectedUnit;
			selectUnit(tilenum);

			// quit if attacking same team
			if (currentteam == selectedUnit.getTeam())
			{
				return;
			}

			// if selectedUnit got killed by attack
			boolean kill = temp.attack(selectedUnit);

			// add info about enemy to info label
			String infostring = info.getText();
			infostring = infostring.substring(0, infostring.length()-5);
			info.setText("<html> Info:<br>Selected Unit HP: " + temp.getHp() + "<br>Moves left: " + temp.movesLeft() + "<br>Enemy Unit HP: " + selectedUnit.getHp() +  "<br><html>" );

			
			// if hit print hit in battle info
			// else print miss in battle info
			if (temp.hitOrMiss()) {
				battle.setText("<html>Battle:<br>Enemy unit was hit!<br>Only " + selectedUnit.getHp() + " hp remains! </html>");
			} else {
				battle.setText("<html>Battle:<br>You missed!</html>");
			}
			if (kill)
			{
				battle.setText("<html>Battle:<br>Enemy unit has died!</html>");
				// set location in unitlocations to null
				// and set corresponding unit in unit array to null as well
				unitlocations[selectedUnit.getTileNum()] = null;
				if (currentteam == "noord")
				{
					teampopos[selectedUnit.getNum()] = null;

				} else {
					teamnoord[selectedUnit.getNum()] = null;
				}
			}

			temp.resetBonus();
		}
	}

	// Mouse click listener
	class MouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			// get tilenumber and set mouse points in gameboard
			gameboard.setMousePoint(e.getPoint());
			int tilenum = gameboard.getTileNum(e.getX(),e.getY());
			if (tilenum == -1)
			{
				return;
			}

			// get unit to take action with
			if (selectedUnit == null)
			{
				selectUnit(tilenum);

			} // if second valid click move selected unit to new location
			else {
				moveOrAttack(tilenum);
				
				// deselect everything
				selectedUnit = null;
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