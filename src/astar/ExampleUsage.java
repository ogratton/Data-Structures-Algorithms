package astar;

import java.awt.Point;
import java.util.LinkedList;

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
				char c;
				
				switch(array[i][j])
				{
					case 1:
						c = '.';
						break;
					case -1:
						c = '#';
						break;
					case 0:
						c = 'O';
						break;
					default:
						c = (char) array[i][j];
						break;
				}
				System.out.print(c);	
			}
			System.out.println();
		}
		System.out.println();
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
	
	public static void main(String[] args)
	{
		final int Y = 1, N = -1;
		
		int[][] map = new int[][]{	{Y,N,Y,N,N,N,N,Y,N,N},
									{Y,Y,N,Y,Y,N,Y,Y,Y,N},
									{N,Y,Y,Y,Y,N,N,Y,Y,Y},
									{N,N,N,Y,N,Y,N,N,N,Y},
									{Y,N,N,Y,Y,N,Y,Y,N,Y},
									{Y,Y,N,N,Y,Y,Y,Y,N,Y},
									{N,Y,Y,Y,N,N,Y,N,N,N}};
	
		print2Darray(map);
		
//		LinkedList<Point> route = new LinkedList<Point>();
//		route.add(new Point(4,7));
//		route.add(new Point(1,1));
//		
//		print2Darray(traceRoute(map, route));
		
		AStarSearch astar = new AStarSearch(map);
		
		LinkedList<Point> path = astar.search(new Point(1,1), new Point(4,7), false);
		
		print2Darray(traceRoute(map, path));
		
									
	}
}
