import java.awt.Image;

public class General implements OpenUnit {
	public double hp;
	public int atk;
	public String team;
	public String type = "General";
	public Image imObject;

	public General(String team) 
	{
		this.hp = 5;
		this.atk = 8;
		this.team = team;
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