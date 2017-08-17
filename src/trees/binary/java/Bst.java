package trees.binary.java;

import java.util.*;

/**
 * Binary Search Tree implementation that holds a key and a value
 * @author Oliver Gratton
 *
 * @param <Key>
 * @param <Value>
 */
public interface Bst<Key extends Comparable<Key>, Value>
{
	/**
	 * @return Is this tree empty?
	 */
	public boolean isEmpty();

	/**
	 * @param k key
	 * @return Does every node have its key smaller than k?
	 */
	public boolean smaller(Key k);

	/**
	 * @param k key
	 * @return Does every node have its key bigger than k?
	 */
	public boolean bigger(Key k);

	/**
	 * @param k key
	 * @return Does the key occur in this tree?
	 */
	public boolean has(Key k);

	/**
	 * Finds the value of the node with a given key k, if it exists.
	 * 
	 * @param k key
	 * @return value
	 */
	public Optional<Value> find(Key k);

	/**
	 * @param k key
	 * @param v value
	 * @return a copy of this tree with k,v inserted, if the key isn't
	 * already there, or with the value replaced, if it is already there.
	 */
	public Bst<Key, Value> put(Key k, Value v);

	/**
	 * @param k key
	 * @return a copy of "this" with the node with key k deleted, it if exists.
	 */
	public Optional<Bst<Key, Value>> delete(Key k);

	/**
	 * @return the entry with smallest key (=left-most node), if it exists.
	 */
	public Optional<Entry<Key, Value>> smallest();

	/**
	 * @return new tree with smallest element deleted, if it exists.
	 */
	public Optional<Bst<Key, Value>> deleteSmallest();

	/**
	 * @return entry with largest key (=right-most node), if it exists.
	 */
	public Optional<Entry<Key, Value>> largest();

	/**
	 * @return new tree with largest element deleted, if it exists.
	 */
	public Optional<Bst<Key, Value>> deleteLargest();

	/**
	 * @return 2-dimensional, rotated tree printing. Use for debugging.
	 */
	public String fancyToString();

	/**
	 * @param d start position
	 * @return 2-dimensional, rotated tree printing. Use for debugging.
	 */
	public String fancyToString(int d);

	/**
	 * @return how many values are stored
	 */
	public int size();

	/**
	 * @return the height of this tree. That of the empty tree is -1 by
	 * convention
	 */
	public int height();

	/**
	 * Prints the values in key order (left, then root, then right).
	 */
	public void printInOrder();

	/**
	 * Save entries in key order in the array a starting at 0, as in an
	 * in-order traversal (left, then root, then right).
	 * 
	 * @param a array of entries
	 */
	public void saveInOrder(Entry<Key, Value> a[]);

	/**
	 * Save entries in key order in the array a starting at 0, as in an
	 * in-order traversal (left, then root, then right).
	 * 
	 * Start at position i, and inform the caller what the
	 * next available position of the array is.
	 * 
	 * @param a array of entries
	 * @param i starting position
	 * @return next available position of array
	 */
	public int saveInOrder(Entry<Key, Value> a[], int i);

	/**
	 * @return a balanced copy of this tree (let's take this to mean,
	 * ambiguously, a tree with same key-value pairs but with minimal
	 * height).
	 */
	public Bst<Key, Value> balanced();

	// Added 2016/02/25 for automatic marking purposes.
	public Optional<Key> getKey();

	public Optional<Value> getValue();

	public Optional<Bst<Key, Value>> getLeft();

	public Optional<Bst<Key, Value>> getRight();
	
	public String toString();
}
