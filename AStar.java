import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class AStar {

	private static ArrayList<Integer> closed = new ArrayList<Integer>();

	private static ArrayList<Integer> open = new ArrayList<Integer>();

	private static int maxSearchDist = 10;

//	private Node[] nodes;

//	private AStarHeuristic heuristic;

	private static ArrayList<Shape> tiles = HexGrid.getTiles();

	private static int startingTile;

	private static int destinationTile = 60;
/*
	public AStar(arrayList<Shape> tiles, int startingTile, int destinationTile) {
		this(tiles, startingTile, destinationTile);
	}
*/
	private void AStar() {
		findPath();
	}


	public static void findPath() {
		// Check if destinationTile is occupied
		


		startingTile = HexGrid.lastTile();	
		int nextTile;
		// Clear lists
		closed.clear();
		open.clear();
		// Add StartingTile
		open.add(startingTile);

		

		int maxDepth = 0;

		while ((maxDepth < maxSearchDist) && (open.size() != 0)) {
			Shape s = (Shape)tiles.get(startingTile);
			ArrayList<Integer> adjacent = HexGrid.adjacentTiles(startingTile, s);
			System.out.println("adjacent tiles: " + adjacent.toString());

			nextTile = getRandomTile(adjacent);

			if (open.contains(nextTile)) {

			} else {
				closed.add(nextTile);				
			}
			System.out.println("new nexttile: " + nextTile);
			startingTile = nextTile;
			maxDepth += 1;
			System.out.println(closed.toString());


		}


	}

	// Geeft random tile terug
	private static int getRandomTile(ArrayList<Integer> adjacent) {
		Random r = new Random();
		int nextTileOption = adjacent.get(r.nextInt(adjacent.size()));
		return nextTileOption;
	}
}

/*
Kies een random nieuwe Tile
Check of random Tile in 
*/













