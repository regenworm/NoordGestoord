import java.awt.Image;
import javax.swing.ImageIcon;

public class Swordsman implements OpenUnit {
	// hp of unit, unit dies if it runs out
	public int hp;

	// attack power of unit, determines chance of hitting
	public int atk;

	// team name
	public String team = "";

	// type of unit
	public String type = "Swordsman";
	
	// image which represents unit (swordsman of team1 or 2)
	public Image imObject;

	// location in tilenumber 
	public int coords;

	// number of moves unit can make (attacking or moving)
	public int movesLeft = 3;

	// number in the unit array which contains this unit
	public int number;

	// shows bonus from adjacent units
	public int adjacencyBonus = 0;

	// shows whether last attack hit or missed
	public boolean hitmiss = false;

	// return hitmiss and reset hitmiss
	public boolean hitOrMiss() {
		boolean temp = hitmiss;
		hitmiss = false;
		return temp;
	}

	// constructor
	public Swordsman(String team,int num) 
	{
		this.hp = 4;
		this.atk = 6;
		this.team = team;
		if (team == "noord")
		{
			ImageIcon imGeneral = new ImageIcon("pics/noordboi.png");
			this.imObject = imGeneral.getImage();
			number = num;
		} else {
			ImageIcon imGeneral = new ImageIcon("pics/toilet.png");
			this.imObject = imGeneral.getImage();
			number = num;

		}
	}

	// return index in the corresponding unit array
	public int getNum()
	{
		return number;
	}

	// return corresponding image to represent unit in gui
	public Image getImage()
	{
		return imObject;
	}

	// reducehp of unit by 1
	// if unit dies return true
	// else return false
	public boolean reduceHp()
	{
		this.hp -= 1;
		if (this.hp <= 0) {
			die();
			return true;
		}
		return false;
	}

	// returns hp
	public int getHp()
	{
		return this.hp;
	}

	// system out feedback function for unit death
	public void die()
	{
		System.out.println(this.type + " was killed");
	}

	// return atk so hitchance can be calculated when being attacked
	public int getAtk()
	{
		return this.atk + adjacencyBonus;
	}

	// attack a target
	// if target is killed returns true
	// else returns false
	public boolean attack(OpenUnit target)
	{
		if (movesLeft <= 0)
		{
			return false;
		}
		// attack power of target
		int atkTarget = target.getAtk();

		// chance of hitting
		double pHit = 1 / (1 + Math.exp(0.4*(getAtk()-atkTarget)))+1;
		
		// succes of attack
		int hit = 0;

		// random number between 0 and 1
		double d = Math.random();

		// reduce moves left
		movesLeft -= 1;

		// if number smaller than pHit
		// hit is succes
		if (d < pHit)
		{
			hit = 1;
		}

		// if hit failed tell user
		if (hit == 0)
		{
			System.out.println("Miss!");
		}
		// if it hits tell user and
		// reduce target hp and check if target died
		else 
		{
			hitmiss = true;
			System.out.println("Hit!");
			System.out.println("Target has" + target.getHp() + " points left.");
			if (target.reduceHp()) {
				return true;
			}
		}

		return false;
	}

	// move unit from current tile to given tile
	// reduce movesleft
	public void moveUnit(int coords)
	{
		
		this.coords = coords;
		movesLeft -= 1;
	}

	// get type of unit
	// for example: Swordsman or General
	public String getType()
	{
		return this.type;
	}

	// number of moves a unit can still make
	public int movesLeft()
	{
		return movesLeft;
	}

	// get unit location in tilenumber of gameboard
	public int getTileNum()
	{
		return coords;
	}

	// replenish moves left
	public void resetMoves()
	{
		movesLeft = 2;
	}

	// return which team unit is on
	public String getTeam()
	{
		return team;
	}

	// adjusts adjacency bonus
	public void adjustAdjacencyBonus(int bonus)
	{
		adjacencyBonus+=bonus;
	}

	// reset adjacency bonus
	public void resetBonus()
	{
		adjacencyBonus = 0;
	}
}