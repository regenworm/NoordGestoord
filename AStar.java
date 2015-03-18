import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.lang.Object.*;
import java.util.Arrays;

public class AStar {

	private static ArrayList<Integer> closed = new ArrayList<Integer>();

	private static ArrayList<Integer> open = new ArrayList<Integer>();

	private static int maxSearchDist = 100;

	private static ArrayList<Shape> tiles = HexGrid.getTiles();

	private static int startingTile = 0;

	private static int destinationTile = 10;

	private static ArrayList<Integer> costs = new ArrayList<Integer>();

	private void AStar() {
		findPath();
	}
/*
	public static void findPath() {
		// clear lists
		open.clear();
		closed.clear();
		// get adjacent tiles
		Shape s = (Shape)tiles.get(startingTile);
		ArrayList<Integer> adjacent = HexGrid.adjacentTiles(startingTile, s);
		int cost = cost();
		int maxDepth = 0;

		while ((cost > 0)) {
			s = (Shape)tiles.get(startingTile);
			adjacent = HexGrid.adjacentTiles(startingTile, s);
			
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
		}	
	}
*/
	public static void findPath() {
		// Check if destinationTile is occupied
		// int[] = Legends.unitLocations();

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

		int maxDepth = 10;

		//while ((maxDepth < maxSearchDist) && (open.size() != 0)) {
			s = (Shape)tiles.get(startingTile);
			adjacent = HexGrid.adjacentTiles(startingTile, s);
			// System.out.println("adjacent tiles: " + adjacent.toString());
			// closed.add(startingTile);

			for (int i = 0; i < adjacent.size(); i++)
			{
				int adj = adjacent.get(i);

				int[] cost = new int[adjacent.size()];

				cost[i] = cost(adj, destinationTile);

				System.out.println(cost[i]);

				// int lowestCost = cost.indexOf(Collections.min(cost));

				//System.out.println(lowestCost);
				if (open.contains(adj) || closed.contains(adj)) {
					continue;
				} else {
					open.add(adj);
					open.remove(Integer.valueOf(startingTile));	
				}
			//}

			closed.add(startingTile);


			
			randomTile = getRandomTile(adjacent);
			int newCost = cost(randomTile, destinationTile);
			
			nextTile = randomTile;



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
	private static int cost(int startingTile, int destinationTile) {
		int[] cordsStart = HexGrid.getTileCoords(startingTile);
		int[] cordsDest = HexGrid.getTileCoords(destinationTile);
		
		int distX = cordsStart[0] - cordsDest[0];
		int distY = cordsStart[1] - cordsDest[1];
		int dist = (int) Math.sqrt(distX*distX + distY*distY);

		return dist;
	}


}











