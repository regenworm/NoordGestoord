import java.awt.*;
import javax.swing.*;
import java.awt.Shape;

public class Hexagon {

	private static int length = 28; // length of edge 
	private static int a = length;
	private static int b = (int) (a * 0.8660254037844);
	private static int c = a / 2;
	static int[][] hexCords = new int[100][];


	public static int[] getCordXY(int x, int y) {
		int[] xycords;
		//xycords = new int[] {x-c, y-b, x+c, y-b, x+a, y, x+c, y+b, x-c, y+b, x-a, y};
		xycords = new int[] {x-c, x+c, x+a, x+c, x-c, x-a, y-b, y-b, y, y+b, y+b, y};

		return xycords;
	}

	public static int[] getCordX(int x) {
		int[] xcords;
		xcords = new int[] {x-c, x+c, x+a, x+c, x-c, x-a};
		return xcords;
	}

	public static int[] getCordY(int y) {
		int[] ycords;
		ycords = new int[] {y-b, y-b, y, y+b, y+b, y};
		return ycords;
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

	public static void drawHex(int[] xcords, int[] ycords, Graphics2D g) {
		Polygon poly = new Polygon(xcords, ycords, 6);		// object
		g.setColor(Color.GREEN);
		g.fillPolygon(poly);
		g.setColor(Color.BLACK);
		g.drawPolygon(poly);
	}

	public static void printBoard(int[][] hexCords, int l, Graphics2D g) {
		System.out.println(hexCords[0][1]);
		int[] xcords = new int[6];
		int[] ycords = new int[6];
		int[] hex = new int[12];

		for (int i = 0; i < l*l; i++) {
			if (hexCords[i] != null) {
				hex = hexCords[i];
				System.arraycopy(hex, 0, xcords, 0, xcords.length);
				System.arraycopy(hex, xcords.length, ycords, 0, ycords.length);
				drawHex(xcords, ycords, g);
			}
		}
	}
}