import java.awt.Image;
import javax.swing.ImageIcon;

public class Swordsman implements OpenUnit {
	public int hp;
	public int atk;
	public String team = "";
	public String type = "Swordsman";
	public Image imObject;
	//private Random rGen = new Random();
	public int coords;
	public int movesLeft = 2;
	public int number;

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
			number = num+12;

		}
	}

	public int getNum()
	{
		return number;
	}

	public Image getImage()
	{
		return imObject;
	}

	public void reduceHp()
	{
		this.hp -= 1;
	}

	public int getHp()
	{
		return this.hp;
	}

	public void die()
	{
		System.out.println(this.type + " was killed");
	}

	public int getAtk()
	{
		return this.atk;
	}

	public void setImage(Image imObject)
	{
		this.imObject = imObject;
	}

	public void attack(OpenUnit target)
	{
		int atkTarget = target.getAtk();
		double pHit = 1 / (1 + Math.exp(0.4*(this.atk-atkTarget)));
		int hit = 0;// rGen.nextInt(pHit*100);

		if (hit == 0)
		{
			System.out.println("Miss!");
		}
		else 
		{
			System.out.println("Hit!");
			target.reduceHp();
			if (target.getHp() == 0)
			{
				target.die();
			}
		}
	}

	public void moveUnit(int coordss)
	{
		this.coords = coordss;
	}

	public String getType()
	{
		return this.type;
	}

	public int movesLeft()
	{
		return movesLeft;
	}

	public int getTileNum()
	{
		return coords;
	}

	public void resetMoves()
	{
		movesLeft = 2;
	}
}