import java.util.*;
import java.awt.Image;

public interface OpenUnit {
	public void reduceHp();
	public int getAtk();
	public void setImage(Image imObject);
	public void attack(OpenUnit target);
	public void moveUnit(int[] coords);
	public void die();
	public int getHp();
	public String getType();
	public int movesLeft();
}