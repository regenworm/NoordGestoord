import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.lang.Object.*;

public class AStar {

	private static ArrayList<Integer> closed = new ArrayList<Integer>();

	private static ArrayList<Integer> open = new ArrayList<Integer>();

	private static int maxSearchDist = 100;

	private static ArrayList<Shape> tiles = HexGrid.getTiles();

	private static int startingTile = 0;

	private static int destinationTile = 10;

	private void AStar() {
		findPath();
	}
/*
	public static void findPath(int startingTile, int destinationTile) {
		// clear lists
		open.clear();
		closed.clear();
		// get adjacent tiles
		Shape s = (Shape)tiles.get(startingTile);
		ArrayList<Integer> adjacent = HexGrid.adjacentTiles(startingTile, s);
		int cost = cost();	
	}
*/
	public static void findPath() {
		// Check if destinationTile is occupied
		


		// get clicked tile
		startingTile = HexGrid.lastTile();	
		int nextTile;
		int randomTile;
		int lastCost = 1000;
		// Clear lists
		closed.clear();
		open.clear();
		// Add StartingTile to list
		open.add(startingTile);
		Shape s = (Shape)tiles.get(startingTile);
		ArrayList<Integer> adjacent = HexGrid.adjacentTiles(startingTile, s);
		randomTile = getRandomTile(adjacent);

		int maxDepth = 0;

		while ((maxDepth < maxSearchDist) && (open.size() != 0)) {
			s = (Shape)tiles.get(startingTile);
			adjacent = HexGrid.adjacentTiles(startingTile, s);
			// System.out.println("adjacent tiles: " + adjacent.toString());
			//closed.add(startingTile);

			for (int i = 0; i < adjacent.size(); i++)
			{
				int adj = adjacent.get(i);

				if (open.contains(adj) || closed.contains(adj)) {
					continue;
				} else {
					open.add(adj);
					open.remove(Integer.valueOf(startingTile));	
				}
			}

//			if (closed.contains(startingTile)) {
//				continue;
//			} else {
				closed.add(startingTile);
//			}

			int cost = cost();
			System.out.println(cost);
			// random path search

			
			//while (cost > lastCost) {
				randomTile = getRandomTile(adjacent);
			//} 
			nextTile = randomTile;

			lastCost = cost;
			//System.out.println(lastCost);

			startingTile = nextTile;
			maxDepth += 1;
			//System.out.println("open: " + open.toString());
			//System.out.println("closed: " + closed.toString());

			if (closed.contains(destinationTile)) {
				System.out.println("path: " + closed.toString());
				break;
			} else {
				continue;
			}	
		}			
	}

	// Geeft random tile terug
	private static int getRandomTile(ArrayList<Integer> adjacent) {
		Random r = new Random();
		int nextTileOption = adjacent.get(r.nextInt(adjacent.size()));
		return nextTileOption;
	}

	// Geeft de afstand tussen twee punten in pixels
	private static int cost() {
		int[] cordsStart = HexGrid.getTileCoords(startingTile);
		int[] cordsDest = HexGrid.getTileCoords(destinationTile);
		
		int distX = cordsStart[0] - cordsDest[0];
		int distY = cordsStart[1] - cordsDest[1];
		int dist = (int) Math.sqrt(distX*distX + distY*distY);

		return dist;
	}
}











