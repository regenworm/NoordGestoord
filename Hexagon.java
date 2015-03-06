/* Assignment 3, Datastructuren 2015
 * Authors: Alex Khawalid, 10634207
 * 			Philip Bouman, 10668667
 */

import java.awt.*;
import javax.swing.*;
import java.awt.Shape;
/*
 * Various methods for the calculation of the hexagon coordinates.
 * The calculations are relative to the center of the hexagon.
 */

public class Hexagon {

	// length of edge hexagon
	private static int length = 40; 	
	private static int a = length;

	// b = a cos(30)
	// geen idee waarom ik eerst a*6 moet doen, maar het werkt...
	private static int b = (int) ((a*6) * Math.cos(30)); //(a * 0.8660254037844); 

	// c = a sin(30)
	private static int c = a / 2;

	// Get x-coordinates of a hexagon
	public static int[] getCordX(int x) {
		int[] xcords;
		xcords = new int[] {x-c, x+c, x+a, x+c, x-c, x-a};
		return xcords;
	}

	// Get y-coordinates of a hexagon
	public static int[] getCordY(int y) {
		int[] ycords;
		ycords = new int[] {y-b, y-b, y, y+b, y+b, y};
		return ycords;
	}

	// Offset for uneven column
	public static int hexOffsetColumn(int y) {
		y = y + b;
		return y;
	}

	// Offset for uneven row
	public static int hexOffsetRow(int x) {
		x = x + a*3/2;
		return x;
	}

	// Get coordinates of next column
	public static int hexColumnSouth(int y) {
		y = y + b*2;
		return y;
	}

	// Get coordinates of next row
	public static int hexRowEast(int x) {
		x = x + a*3;
		return x;
	}
}