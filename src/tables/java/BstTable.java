package tables.java;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import trees.binary.java.Bst;
import trees.binary.java.Empty;
import trees.binary.java.Entry;

/**
 * Immutable Table using immutable binary search tree
 * Allows no duplicates
 * @author Oliver
 *
 * @param <Key>
 * @param <Value>
 */
public class BstTable<Key extends Comparable<Key>, Value> implements Table<Key, Value>
{
	private Bst<Key,Value> tree;
	
	/**
	 * Make a new table from an empty tree
	 */
	public BstTable()
	{
		tree = new Empty<Key, Value>();
	}
	
	/**
	 * Make a new table from an existing tree
	 * @param tree tree to be made into a table
	 */
	public BstTable(Bst<Key, Value> tree)
	{
		this.tree = tree;
	}
	
	/**
	 * @param v the key
	 * @return true if the table contains this key
	 */
	@Override
	public boolean containsKey(Key v)
	{
		return tree.has(v);
	}
	
	/**
	 * @param k The key being looked up
	 * @return The value corresponding to key k
	 */
	@Override
	public Optional<Value> get(Key k)
	{
		return tree.getValue();
	}

	/**
	 * @return true if the table is empty
	 */
	@Override
	public boolean isEmpty()
	{
		return tree.isEmpty();
	}

	/**
	 * Insert a new entry into the table
	 * @param k The key to be inserted
	 * @param v The value to be inserted
	 * @return The updated table
	 */
	@Override
	public Table<Key, Value> put(Key k, Value v)
	{
		return new BstTable<Key,Value>(tree.put(k, v));
	}

	/**
	 * Remove an entry from the table
	 * @param k The key of the entry to be removed
	 * @return the updated table
	 */
	@Override
	public Optional<Table<Key, Value>> remove(Key k)
	{
		return Optional.ofNullable(new BstTable<Key, Value>(tree.delete(k).get()));
	}

	/**
	 * @return The number of entries in the table
	 */
	@Override
	public int size()
	{
		return tree.size();
	}

	/**
	 * @return A set of all the values in the table
	 */
	@Override
	public Collection<Value> values()
	{
		// this is probably dodgy
		Key key = null;
		Value value = null;
		Entry<Key,Value> e = new Entry<>(key,value);
	    @SuppressWarnings("unchecked")
	    Entry<Key,Value>[] a = (Entry<Key,Value>[]) Array.newInstance(e.getClass(), size());
	    tree.saveInOrder(a);
	    Set<Value> set = new TreeSet<Value>();
	    for (Entry<Key,Value> entry : a)
	    {
	    	set.add(entry.getValue());
	    }
		return set;
	}

	/**
	 * @return A set of all the keys in the table
	 */
	@Override
	public Collection<Key> keys()
	{
		Key key = null;
		Value value = null;
		Entry<Key,Value> e = new Entry<>(key,value);
	    @SuppressWarnings("unchecked")
	    Entry<Key,Value>[] a = (Entry<Key,Value>[]) Array.newInstance(e.getClass(), size());
	    tree.saveInOrder(a);
	    Set<Key> set = new TreeSet<Key>();
	    for (Entry<Key,Value> entry : a)
	    {
	    	set.add(entry.getKey());
	    }
		return set;
	}

}
