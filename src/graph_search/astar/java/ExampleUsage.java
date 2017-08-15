package graph_search.astar.java;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Shows how to use the A* class and has a bunch of IO functions too
 * @author Oliver Gratton
 *
 */
public class ExampleUsage
{
	/**
	 * Print a 2D int array
	 * with some special substutions
	 * @param array
	 */
	private static void print2Darray(int[][] array)
	{
		for (int i = 0; i < array.length; i++)
		{
			for (int j = 0; j < array[0].length; j++)
			{
				String c;
				
				switch(array[i][j])
				{
//					case 1:
//						c = '.';
//						break;
					case -1:
						c = "#";
						break;
					case 0:
						c = "O";
						break;
					default:
						c = ""+array[i][j];
						break;
				}
				System.out.print(c);	
			}
			System.out.println();
		}
		System.out.println();
	}
	
	/**
	 * Read a map from a file (txt, csv etc.)
	 * Uses convention W=-1 for walls
	 * All other values must be natural numbers
	 * @param filepath
	 * @return
	 * @throws IOException 
	 */
	private static int[][] readMapFromFile(String filepath) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(filepath));
		String line = "";
		ArrayList<int[]> data = new ArrayList<int[]>();
		while ((line = br.readLine()) != null)
		{
			String[] s = line.trim().split(",");
			int[] row = new int[s.length];
			for (int i = 0; i < s.length; i++)
			{
				if(s[i].equals("W"))
				{
					row[i] = -1;
				}
				else
				{
					row[i] = Integer.parseInt(s[i]);
				}
			}
			data.add(row);
		}
		br.close();
	
		return _ArrayListToArray(data);
	}
	
	/**
	 * Convert array list of int[] to int[][]
	 * @param list
	 * @return
	 */
	private static int[][] _ArrayListToArray(ArrayList<int[]> list)
	{
		int[][] array = new int[list.size()][list.get(0).length];
		for (int i = 0; i < array.length; i++)
		{
			array[i] = list.get(i);
		}
		return array;
	}
	
	/**
	 * Mark points mentioned in route with a zero
	 * @param array
	 * @param route
	 * @return
	 */
	private static int[][] traceRoute(int[][] array, LinkedList<Point> route)
	{
		while(!route.isEmpty())
		{
			Point p = route.pop();
			array[p.x][p.y] = 0;
		}
		
		return array;
	}
	
	public static void main(String[] args) throws IOException
	{		
		int[][] map = readMapFromFile("src/graph_search/astar/java/graph.csv");
		AStarSearch astar = new AStarSearch(map);
		LinkedList<Point> path = astar.search(new Point(3,9), new Point(3,4), true);
		print2Darray(traceRoute(map, path));									
	}
}
