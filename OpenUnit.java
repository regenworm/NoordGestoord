import java.util.*;
import java.awt.Image;

public interface OpenUnit {
	public Image getImage();
	public boolean reduceHp();
	public int getAtk();
	public boolean attack(OpenUnit target);
	public void moveUnit(int coords);
	public void die();
	public int getHp();
	public String getType();
	public int movesLeft();
	public int getTileNum();
	public void resetMoves();
	public int getNum();
	public String getTeam();
}