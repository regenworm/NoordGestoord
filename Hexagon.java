import java.awt.*;
import javax.swing.*;

public class Hexagon {

	private static int length = 30; // length of edge 
	private static int a = length;
	private static int b = (int) (a * 0.8660254037844);
	private static int c;

	// returns 6 poitns of hexagon
	public static Polygon hex(int x, int y) {
		c = a / 2;
		int[] xcords, ycords;
		xcords = new int[] {x-c, x+c, x+a, x+c, x-c, x-a};
		ycords = new int[] {y-b, y-b, y, y+b, y+b, y};

		return new Polygon(xcords, ycords, 6);
	}

	public static int hexOffsetColumn(int y) {
		y = y + b;
		return y;
	}

	public static int hexOffsetRow(int x) {
		x = x + a*3/2;
		return x;
	}

	public static int hexColumnSouth(int y) {
		y = y + b*2;
		return y;
	}

	public static int hexRowEast(int x) {
		x = x + a*3;
		return x;
	}

	public static void drawHex(int x, int y, Graphics2D g) {
		Polygon poly = hex(x, y);
		g.setColor(Color.GREEN);
		g.fillPolygon(poly);
		g.setColor(Color.BLACK);
		g.drawPolygon(poly);
	}
}