import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class AStar {

	private ArrayList<Integer> closed = new ArrayList<Integer>();

	private ArrayList<Integer> open = new ArrayList<Integer>();

	private int maxSearchDist = 3;

//	private Node[] nodes;

//	private AStarHeuristic heuristic;

	private static ArrayList<Shape> tiles = HexGrid.getTiles();

	private int startingTile;

	private int destinationTile;
/*
	public AStar(arrayList<Shape> tiles, int startingTile, int destinationTile) {
		this(tiles, startingTile, destinationTile);
	}
*/
	public Path findPath() {
		// Check if destinationTile is occupied

		// Clear lists
		closed.clear();
		open.clear();
		// Add StartingTile
		open.add(startingTile);

		int maxDepth = 0;
		while ((maxDepth < maxSearchDist) && (open.size() != 0)) {

		}


	}


}