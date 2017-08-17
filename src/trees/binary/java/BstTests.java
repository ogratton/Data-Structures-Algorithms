package trees.binary.java;

import tables.java.BstTable;
import tables.java.Table;

public class BstTests
{

	public static void main(String[] args)
	{
		Bst<Integer, String> t1 = new Empty<>();
		assert(t1.size() == 0);
		assert(t1.height() == -1);
		assert(t1.isEmpty());

		Bst<Integer, String> t2 = t1.put(30, "John");
		assert(t1.isEmpty());
		assert(t2.size() == 1);
		assert(t2.height() == 0);

		Bst<Integer, String> t3 = t2.put(40, "Mary").put(25, "Peter").put(37, "Monica").put(34, "Nicolas").put(31, "Martin");
		assert(t3.size() == 6);
		assert(t2.size() == 1);
		assert(t2.height() == 0);
		assert(t3.height() == 4);

		Bst<Integer, String> t4 = t3.put(31, "Kathy");
		assert(t3.size() == 6);
		assert(t4.size() == 6);

		assert(t4.delete(31).get().size() == 5);

		assert(t4.deleteSmallest().get().smallest().get().getKey() == 30);

		t4.printInOrder();

		System.out.println(t4.fancyToString());

		Entry<Integer,String>[] e = ((Fork<Integer, String>) t4).startSave();
		for (int i = 0; i < e.length; i++)
		{
			System.out.println(e[i]);
		}

		Bst<Integer, String> t5 = t3.balanced();
		assert(t5.size() == t3.size());
		assert(t5.height() <= t3.height());
		assert(t5.height() <= log2floor(t5.size()));
		System.out.println(t5.fancyToString());

		Bst<Integer, String> t6 = t3.put(15, "Gnome").put(22, "Goblin").put(66, "Troll");
		assert(t6.size() == 9);
		Bst<Integer, String> t7 = t6.balanced();
		System.out.println(t7.fancyToString());
		assert(t7.height() <= log2floor(t7.size()));

		Table<Integer, String> table = new BstTable<Integer, String>(t5);
		assert(table.size() == t5.size());
		System.out.println(table.values());

		System.out.println("Tests passed!1!!1!!");
	}

	private static int log2floor(int x)
	{
		assert(x > 0);
		int y = 0;
		do
		{
			y = y + 1;
			x = x / 2;
		}
		while (x > 0);
		return y;
	}
}
