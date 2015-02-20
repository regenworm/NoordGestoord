import java.awt.Image;

public class Swordsman implements OpenUnit {
	public double hp;
	public int atk;
	public String team = "";
	public String type = "Swordsman";
	public Image imObject;

	public Swordsman(String teamname) 
	{
		this.hp = 4;
		this.atk = 6;
		this.team = teamname;

		System.out.println(this.team);
	}

	public void setImage(Image imObject)
	{
		this.imObject = imObject;
	}

	public void getAttacked(double damage)
	{
		this.hp -= damage;
	}
}