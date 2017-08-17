package trees.binary.java;

import java.util.Optional;

/**
 * The empty tree
 * @author Oliver Gratton
 *
 * @param <Key>
 * @param <Value>
 */
public class Empty<Key extends Comparable<Key>, Value> implements Bst<Key, Value>
{

	public Empty()
	{
		// nothing to do
	}

	@Override
	public boolean isEmpty()
	{
		return true;
	}

	@Override
	public Optional<Key> getKey()
	{
		return Optional.empty();
	}

	@Override
	public Optional<Value> getValue()
	{
		return Optional.empty();
	}

	@Override
	public Optional<Bst<Key, Value>> getLeft()
	{
		return Optional.empty();
	}

	@Override
	public Optional<Bst<Key, Value>> getRight()
	{
		return Optional.empty();
	}

	@Override
	public boolean smaller(Key k)
	{
		return true;
	}

	@Override
	public boolean bigger(Key k)
	{
		return true;
	}

	@Override
	public boolean has(Key k)
	{
		return false;
	}

	@Override
	public Optional<Value> find(Key k)
	{
		return Optional.empty();
	}

	@Override
	public Bst<Key, Value> put(Key k, Value v)
	{
		return new Fork<Key, Value>(k, v, this, this);
	}

	@Override
	public Optional<Bst<Key, Value>> delete(Key k)
	{
		return Optional.empty();
	}

	@Override
	public Optional<Entry<Key, Value>> smallest()
	{
		return Optional.empty();
	}

	@Override
	public Optional<Bst<Key, Value>> deleteSmallest()
	{
		return Optional.empty();
	}

	@Override
	public Optional<Entry<Key, Value>> largest()
	{
		return Optional.empty();
	}

	@Override
	public Optional<Bst<Key, Value>> deleteLargest()
	{
		return Optional.empty();
	}

	@Override
	public String fancyToString()
	{
		return "";
	}

	@Override
	public String fancyToString(int d)
	{
		return "";
	}

	@Override
	public int size()
	{
		return 0;
	}

	@Override
	public int height()
	{
		return -1;
	}

	@Override
	public void printInOrder()
	{
		// TODO Auto-generated method stub
		// nothing to do (?)

	}

	@Override
	public void saveInOrder(Entry<Key, Value>[] a)
	{
		// TODO Auto-generated method stub
		// nothing to do (?)

	}

	@Override
	public int saveInOrder(Entry<Key, Value>[] a, int i)
	{
		// TODO Auto-generated method stub
		// nothing to do (?)
		return 0;
	}

	@Override
	public Bst<Key, Value> balanced()
	{
		// TODO Auto-generated method stub
		// nothing to do (?)
		return null;
	}
	
	public String toString()
	{
		return fancyToString();
	}

}