public class AStar {

	private ArrayList<Integer> closed = new ArrayList<Integer> ();

	private SortedList<Integer>  open = new SortedList<Integer> ();

	private int maxSearchDist;

	private Node[] nodes;

	private AStarHeuristic heuristic;

	private arrayList<Shape> tiles = Hexagon.shapelist;

	private int startingTile;

	private int destinationTile;

	public AStar(arrayList<Shape> tiles, int startingTile, int destinationTile) {
		this(tiles, startingTile, destinationTile);
	}

	public AStar()
}