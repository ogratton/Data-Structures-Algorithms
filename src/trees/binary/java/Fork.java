package trees.binary.java;

import java.lang.reflect.Array;
import java.util.Optional;

/**
 * The non-empty tree
 * @author Oliver Gratton
 *
 * @param <Key>
 * @param <Value>
 */
public class Fork<Key extends Comparable<Key>, Value> implements Bst<Key, Value>
{

	private final Key rootKey;
	private final Value rootValue;

	private final Bst<Key, Value> left, right;

	public Fork(Key key, Value value, Bst<Key, Value> left, Bst<Key, Value> right)
	{
		assert(left != null); // Refuse to work with null pointers.
		assert(right != null);

		assert(left.smaller(key)); // Refuse to violate the bst property.
		assert(right.bigger(key)); // So all our objects will really be bst's.

		this.rootKey = key;
		this.rootValue = value;
		this.left = left;
		this.right = right;
	}

	/**
	 * Is this tree empty?
	 * 
	 * @return false as by definition a fork has elements
	 */
	@Override
	public boolean isEmpty()
	{
		return false;
	}

	@Override
	public Optional<Key> getKey()
	{
		return Optional.of(rootKey);
	}

	@Override
	public Optional<Value> getValue()
	{
		return Optional.of(rootValue);
	}

	@Override
	public Optional<Bst<Key, Value>> getLeft()
	{
		return Optional.of(left);
	}

	@Override
	public Optional<Bst<Key, Value>> getRight()
	{
		return Optional.of(right);
	}

	/**
	 * Does every node have its key smaller than k?
	 * 
	 * @param k The key we are comparing all others to
	 * @return true if all keys are smaller than k
	 */
	@Override
	public boolean smaller(Key k)
	{
		Key largestKey = this.largest().get().getKey();
		return k.compareTo(largestKey) > 0;

	}

	/**
	 * Does every node have its key bigger than k?
	 * 
	 * @param k The key we are comparing all others to
	 * @return true if all keys are bigger than k
	 */
	@Override
	public boolean bigger(Key k)
	{
		Key smallestKey = this.smallest().get().getKey();
		return k.compareTo(smallestKey) < 0;
	}

	/**
	 * Does the key occur in this tree?
	 * 
	 * @param k The key being enquired about
	 * @return true if present
	 */
	@Override
	public boolean has(Key k)
	{
		// if this tree's root is k
		if (k.compareTo(rootKey) == 0)
		{
			return true;
		}
		else
		{
			// if k would be in the left tree
			if (k.compareTo(rootKey) < 0)
			{
				return left.has(k);
			}
			// if k is in this tree it must now be in the right tree
			else
			{
				return right.has(k);
			}
		}
		// false cases are handled by empty
	}

	/**
	 * Finds the value of the node with a given key k, if it exists.
	 * 
	 * @param k The key being searched for
	 * @return The value of node with key k
	 */
	@Override
	public Optional<Value> find(Key k)
	{
		// if this tree's root is k
		if (k.compareTo(rootKey) == 0)
		{
			return Optional.of(rootValue);
		}
		else
		{
			// if k would be in the left tree
			if (k.compareTo(rootKey) < 0)
			{
				return left.find(k);
			}
			// if k is in this tree it must now be in the right tree
			else
			{
				return right.find(k);
			}
		}
	}

	/**
	 * Returns a copy of this tree with k,v inserted, if the key isn't
	 * already there, or with the value replaced, it is already there.
	 * 
	 * @param k Key of node being inserted
	 * @param v Value of node being inserted
	 * @return Updated tree
	 */
	@Override
	public Bst<Key, Value> put(Key k, Value v)
	{
		// go to the left if input is smaller than root
		if (k.compareTo(rootKey) == 0)
		{
			return this;
		}
		// go right if input bigger than root
		else if (k.compareTo(rootKey) < 0)
		{
			return new Fork<Key, Value>(rootKey, rootValue, left.put(k, v), right);
		}
		// overwrite the current root
		else
		{
			return new Fork<Key, Value>(rootKey, rootValue, left, right.put(k, v));
		}
	}

	/**
	 * Returns a copy of "this" with the node with key k deleted, it if exists.
	 * 
	 * @param k Key of node to be deleted
	 * @return Updated tree
	 */
	@Override
	public Optional<Bst<Key, Value>> delete(Key k)
	{
		if (k.compareTo(rootKey) == 0)
		{
			// at least the left child is empty
			if (left.isEmpty())
			{
				return Optional.of(right);
			}
			// right child is empty but left is not
			else if (right.isEmpty())
			{
				return Optional.of(left);
			}
			// both children are non-empty
			else
			{
				return Optional.of(new Fork<Key, Value>((left.largest().get().getKey()), (left.largest().get().getValue()),
						(left.deleteLargest().get()), right));
			}
		}
		else
		{
			if (k.compareTo(rootKey) < 0)
			{
				return Optional.of(new Fork<Key, Value>(rootKey, rootValue, left.delete(k).get(), right));
			}
			else
			{
				return Optional.of(new Fork<Key, Value>(rootKey, rootValue, left, right.delete(k).get()));
			}
		}
	}

	/**
	 * Get smallest Entry in the tree
	 * 
	 * @return Entry with smallest value
	 */
	@Override
	public Optional<Entry<Key, Value>> smallest()
	{
		// Farthest left node will be the smallest
		if (left.isEmpty())
		{
			return Optional.of(new Entry<Key, Value>(rootKey, rootValue));
		}
		else
		{
			return left.smallest();
		}
	}

	/**
	 * Return new tree with smallest element deleted, if it exists.
	 * 
	 * @return Updated tree
	 */
	@Override
	public Optional<Bst<Key, Value>> deleteSmallest()
	{
		if (left.isEmpty())
		{
			return Optional.of(right);
		}
		else
		{
			return Optional.of(new Fork<Key, Value>(rootKey, rootValue, left.deleteSmallest().get(), right));
		}
	}

	/**
	 * Get largest Entry in the tree
	 * 
	 * @return Entry with largest value
	 */
	@Override
	public Optional<Entry<Key, Value>> largest()
	{
		// Farthest right node will be the largest
		if (right.isEmpty())
		{
			return Optional.of(new Entry<Key, Value>(rootKey, rootValue));
		}
		else
		{
			return right.largest();
		}
	}

	/**
	 * Return new tree with largest element deleted, if it exists.
	 * 
	 * @return Updated tree
	 */
	@Override
	public Optional<Bst<Key, Value>> deleteLargest()
	{
		if (right.isEmpty())
		{
			return Optional.of(left);
		}
		else
		{
			return Optional.of(new Fork<Key, Value>(rootKey, rootValue, left, right.deleteSmallest().get()));
		}
	}

	/**
	 * 2-dimensional, rotated tree printing.
	 * 
	 * @return String representation of the tree
	 */
	@Override
	public String fancyToString()
	{
		return "\n\n" + fancyToString(0) + "\n\n";
	}

	/**
	 * fancyToString starting at a given position d.
	 * 
	 * @param d position to start
	 * @return String representation of the tree
	 */
	@Override
	public String fancyToString(int d)
	{
		int step = 5; // depth step (spacing)
		String r = right.fancyToString(d + step);
		String l = left.fancyToString(d + step);
		return r + spaces(d) + rootKey + "\n" + l;
	}

	/**
	 * Private helper method for fancyToString
	 * Generates a string of n spaces
	 * 
	 * @param n number of spaces required
	 * @return
	 */
	private String spaces(int n)
	{
		String s = "";
		for (int i = 0; i < n; i++)
		{
			s = s + " ";
		}
		return s;
	}

	/**
	 * Counts how many values are stored.
	 * 
	 * @return size of tree
	 */
	@Override
	public int size()
	{
		return 1 + left.size() + right.size();
	}

	/**
	 * Gives the height of this tree. That of the empty tree is -1 by
	 * convention.
	 * 
	 * @return height of tree
	 */
	@Override
	public int height()
	{
		return 1 + Math.max(left.height(), right.height());
	}

	/**
	 * Prints the values in key order (left, then root, then right).
	 */
	@Override
	public void printInOrder()
	{
		left.printInOrder();
		System.out.println(rootKey);
		right.printInOrder();

	}

	public Entry<Key, Value>[] startSave()
	{
		Entry<Key, Value> e = new Entry<Key, Value>(rootKey, rootValue);
		@SuppressWarnings("unchecked")
		Entry<Key, Value>[] a = (Entry<Key, Value>[]) Array.newInstance(e.getClass(), size());
		saveInOrder(a);
		// for (int i = 0; i < a.length; i++)
		// {
		// System.out.println(a[i].getValue());
		// }
		return a;
	}

	/**
	 * Save entries in key order in the array a starting at 0, as in an
	 * in-order traversal (left, then root, then right).
	 * 
	 * @param a Array the entries are to be stored in
	 */
	@Override
	public void saveInOrder(Entry<Key, Value>[] a)
	{
		saveInOrder(a, 0);
	}

	/**
	 * saveInOrder but starting at position i, and inform the caller what the
	 * next available position of the array is.
	 * 
	 * @param a Array the entries are to be stored in
	 * @param i where to start filling the array
	 * @return next available position of the array
	 */
	@Override
	public int saveInOrder(Entry<Key, Value>[] a, int i)
	{
		// TODO
		if (!left.isEmpty())
		{
			i = left.saveInOrder(a, i);
		}
		a[i++] = new Entry<Key, Value>(rootKey, rootValue);
		if (!right.isEmpty())
		{
			i = right.saveInOrder(a, i);
		}
		return i;
	}

	/**
	 * Returns a balanced copy of this tree (let's take this to mean,
	 * ambiguously, a tree with same key-value pairs but with minimal
	 * height).
	 * 
	 * @return Updated tree
	 */
	@Override
	public Bst<Key, Value> balanced()
	{
		// saveInOrder()
		Entry<Key, Value>[] a = startSave();
		// make middle entry root (round up for completeness)
		int middle = (int) Math.ceil(a.length / 2.0);
		Entry<Key, Value> root = a[middle];
		// System.out.println("ROOT1: " + root.getValue());

		// repeat process on left and right of root to build sub-trees
		Bst<Key, Value> lTree = balanced(a, 0, middle - 1);
		Bst<Key, Value> rTree = balanced(a, middle + 1, a.length - 1);

		return new Fork<Key, Value>(root.getKey(), root.getValue(), lTree, rTree);
	}

	private Bst<Key, Value> balanced(Entry<Key, Value>[] a, int start, int end)
	{
		if (start == end)
		{
			// System.out.println(start);
			return new Fork<Key, Value>(a[start].getKey(), a[start].getValue(), new Empty<>(), new Empty<>());
		}
		else
		{
			int middle = (int) Math.round(start + ((end - start) / 2.0));
			// System.out.println(start + ", " + middle + ", " + end);
			Entry<Key, Value> root = a[middle];

			Bst<Key, Value> lTree = balanced(a, start, middle - 1);
			// System.out.println("left done");
			Bst<Key, Value> rTree;
			// cannot add one to the middle if the middle is the end
			if (middle != end)
			{
				rTree = balanced(a, middle + 1, end);
			}
			else
			{
				rTree = new Empty<>();
			}
			// System.out.println("right done");

			return new Fork<Key, Value>(root.getKey(), root.getValue(), lTree, rTree);
		}
	}
	
	public String toString()
	{
		return fancyToString();
	}
}
