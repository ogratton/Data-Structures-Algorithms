package astar;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.TreeSet;

/**
 * Perform A* search on a 2D array of tiles
 * Assumes an int array where the numbers are costs
 * Negative nubmers mean walls
 * n means it costs n 'energy' to get there from its neigbours
 * More complex cost networks will need graphs
 * 
 * Outputs a list of points
 * 
 * @author Oliver Gratton
 *
 */
public class AStarSearch
{
	private PriorityQueue<SearchNode> frontier;
	private TreeSet<Point> visited;
	private Point goal;
	private SearchNode current;
	private double costSoFar;

	private final int width, height;

	private boolean diag;

	private int[][] map;

	public AStarSearch(int[][] map)
	{
		this.map = map;

		width = map.length;
		height = map[0].length;
	}

	/**
	 * Returns a list of waypoints between the start and goal points
	 * 
	 * @param start Start point
	 * @param goal End point
	 * @param diag True if diagonal moves are accepted
	 * @return
	 */
	public LinkedList<Point> search(Point start, Point goal, boolean diag)
	{
		// reset all the fields
		this.frontier = new PriorityQueue<SearchNode>(SearchNode.priorityComparator()); // sorts by cost + heuristic.
		this.visited = new TreeSet<Point>(new PointComparator());
		this.goal = goal;
		this.costSoFar = 0;
		this.current = new SearchNode(start, new SearchNode(), costSoFar, goal);
		this.diag = diag;

		// first check if we're already there somehow
		if (start.equals(goal))
		{
			return new LinkedList<Point>();
		}

		// generate frontier by getting surrounding tiles
		addNeighbours();
		LinkedList<Point> ll = new LinkedList<Point>();

		boolean success = false;
		// explore best looking (first) option in frontier until there are no more unexplored reachable states
		while (!frontier.isEmpty() && !success)
		{
			// set current to the head of the frontier
			this.current = frontier.poll();

			if (!visited.contains(current.getLocation()))
			{
				if (current.getLocation().equals(goal))
				{
					//System.out.println("Found the goal!");
					ll = reconstructPath();
					success = true;
					break;
				}
				visited.add(current.getLocation());
				addNeighbours();
			}
		}

		System.out.println("Cost: " + costSoFar);

		return ll;
	}

	/**
	 * Add neighbours of tile to frontier
	 * Don't include ones we can't walk on (walls)
	 * 
	 */
	private void addNeighbours()
	{
		ArrayList<SearchNode> neighbours = new ArrayList<SearchNode>();
		Point curLoc = current.getLocation();

		for (int i = -1; i < 2; i++)
		{
			if ((curLoc.x + i) < width && (curLoc.x + i) >= 0)
			{
				for (int j = -1; j < 2; j++)
				{
					if ((curLoc.y + j) < height && (curLoc.y + j) >= 0)
					{
						// don't want to add the current node in again
						if (!(i == 0 && j == 0))
						{
							// finally, check if we're meant to be including diagonals or not
							// diagonals are when neither i or j are 0
							if (diag || (!diag && (i == 0 || j == 0)))
							{
								neighbours = _addNeighbour(neighbours, curLoc, i, j);
							}
						}
					}
				}
			}
		}

		frontier.addAll(neighbours);
	}

	/**
	 * Helper:
	 * Add a single neigbour
	 */
	private ArrayList<SearchNode> _addNeighbour(ArrayList<SearchNode> neighbours, Point curLoc, int i, int j)
	{
		int neiX = curLoc.x + i;
		int neiY = curLoc.y + j;
		Point neiLoc = new Point(neiX, neiY);
		int value = map[neiX][neiY];

		// we only care if we've not seen it yet and we can walk on it
		if (!visited.contains(neiLoc) && value > 0)
		{
			double cost = map[neiX][neiY];
			neighbours.add(new SearchNode(neiLoc, current, costSoFar + cost, goal));
		}

		return neighbours;
	}

	/**
	 * When we have found the goal as a search node, follow the parents back to
	 * the source
	 * Builds the list from the front so we don't need to reverse it
	 * 
	 * @return a list of points that lead from the start to the goal
	 */
	private LinkedList<Point> reconstructPath()
	{
		SearchNode node = current;
		LinkedList<Point> ll = new LinkedList<Point>();
		while (!node.isEmpty())
		{
			ll.addFirst(node.getLocation());
			node = node.getParent();
		}

		return ll;
	}

}