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

	private static int maxSearchDist = 1;

	private static ArrayList<Shape> tiles = HexGrid.getTiles();

	private static ArrayList<Integer> costs = new ArrayList<Integer>();

	private static Integer[] unitLocations = new Integer[61];

	private static ArrayList<Integer> occupied = new ArrayList<Integer>();

	/* Find the next optimal tile (lowest cost) from the starting tile
	 * to the destination tile.
	 */
	public static int findPath(int startingTile, int destinationTile) {
		// get last clicked tile
		startingTile = HexGrid.lastTile();	

		// get occupied tiles
		getOccupado();

		// Get the adjacent tiles of the start tile
		Shape s = (Shape)tiles.get(startingTile);
		ArrayList<Integer> adjacent = HexGrid.adjacentTiles(startingTile, s);

		Integer currentlowest = null;
		int adjIndex = 0;

		// for all adjacent tiles
		for (int i = 0; i < adjacent.size(); i++)
		{
			// get tilenum
			int adj = adjacent.get(i);

			// init variables
			int[] cost = new int[adjacent.size()];

			// put cost of every adjacent tile to destination tile
			cost[i] = cost(adj, destinationTile);

			// feedback
			if (currentlowest == null)
			{
				currentlowest = cost[i];
				adjIndex = i;
			} else {
				if (currentlowest > cost[i])
				{
					currentlowest = cost[i];
					adjIndex = i;
				}
			}
			System.out.println(cost[i]);

		}
		int tilenum = adjacent.get(adjIndex);
		startingTile = tilenum;

		//System.out.println("lowest: " + currentlowest);
		//System.out.println("tilenum: " + tilenum);	
		System.out.println(tilenum);
		return tilenum;
	}

	// Gives back a random tile
	private static int getRandomTile(ArrayList<Integer> adjacent) {
		Random r = new Random();
		int nextTileOption = adjacent.get(r.nextInt(adjacent.size()));
		return nextTileOption;
	}

	// Give the Euclidian Distance between 2 points
	private static int cost(int startingTile, int destinationTile) {
		int[] cordsStart = HexGrid.getTileCoords(startingTile);
		int[] cordsDest = HexGrid.getTileCoords(destinationTile);
		
		int distX = cordsStart[0] - cordsDest[0];
		int distY = cordsStart[1] - cordsDest[1];
		int dist = (int) Math.sqrt(distX*distX + distY*distY);

		return dist;
	}
	// Returns all tiles that are occupied by friendly players (impassable)
	private static ArrayList getOccupado() {
		for (Integer el : unitLocations)
		{
			if (el == null)
			{
				continue;
			}
			if (el < 0)
			{
				occupied.add(el);
			}
		}
		return occupied;
	}

	public void setUnitLocations(Integer[] unitsloc) {
		unitLocations = unitsloc;
	}
}











