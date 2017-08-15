package graph_search.astar.java;

import java.awt.Point;

/**
 * Public methods I may need to access in both AStarSearch and SearchNode, and
 * maybe elsewhere
 * 
 * @author Oliver Gratton
 *
 */
public class StaticHeuristics
{

	/**
	 * @param a
	 * @param b
	 * @return the Euclidean distance between two points, a and b
	 */
	public static double euclidean(Point a, Point b)
	{
		return Math.sqrt(Math.pow(b.getX() - a.getX(), 2) + Math.pow(b.getY() - a.getY(), 2));
	}

	/**
	 * @param a
	 * @param b
	 * @return the Manhattan distance between two points, a and b
	 */
	public static double manhattan(Point a, Point b)
	{
		return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
	}
}
