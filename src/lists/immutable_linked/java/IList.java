package lists.immutable_linked.java;

/**
 * Interface for immutable lists using the "composite pattern".
 * We include high-order methods.
 * 
 * @author Martín Escardo
 *
 * @param <A> The type the list contains
 */
public interface IList<A>
{
	/**
	 * @return Is the list empty?
	 */
	public boolean isEmpty();

	/**
	 * @return the number of elements of the list
	 */
	public int size();

	/**
	 * @return a reversed copy of the list
	 */
	public IList<A> reverse();

	/**
	 * @param l list to be appended
	 * @return a new list with the list l appended
	 */
	public IList<A> append(IList<A> l);

	/**
	 * @param a element to be appended
	 * @return a new list with the element a appended
	 */
	public IList<A> append(A a);

	/**
	 * @return is the value a an element of the list?
	 */
	public boolean has(A a);

	// Higher-order methods:

	/**
	 * @param p function of type A->boolean
	 * @return the sublist of elements satisfying the predicate
	 */
	public IList<A> filter(Predicate<A> p);

	/**
	 * Returns a list with f applied to every element. <br>
	 * e.g. list.map(s -> s.length()) where list is an IList of Strings
	 * returns a list of the length of each string
	 * 
	 * @param f function of type A->B
	 * @return list of the result of f on every element of this list
	 */
	public <B> IList<B> map(Function<A, B> f); // Applies a function to each element of the list.

	/**
	 * Apply a binary function across the list and store the result in
	 * accumulator b
	 * 
	 * @param f binary function of type A->B->B
	 * @param b accumulator of type B
	 * @return the result of type B of applying the function to every element
	 */
	public <B> B fold(Function2<A, B, B> f, B b);

	/**
	 * @param p predicate (e.g. n -> n % 2 == 0 for evenness)
	 * @return true if all elements satisfy the predicate p
	 */
	public boolean all(Predicate<A> p);

	/**
	 * @param p predicate (e.g. n -> n % 2 == 0 for evenness)
	 * @return true if at least one element satisfies the predicate p
	 */
	public boolean some(Predicate<A> p);

	/**
	 * Applies an action (like printing) to each element of the list
	 * 
	 * @param a a function of type A->void
	 */
	public void forEach(Action<A> a);

	/**
	 * Performs case analysis (empty/non-empty)
	 * @param b value if the list is empty
	 * @param f functon for calculating value if not empty
	 * @return value of type B
	 */
	public <B> B cases(B b, Function2<A, IList<A>, B> f);

	// Unsafe operations (both throw an exception for the empty list):

	/**
	 * @return the head of a non-empty list
	 */
	public A head();

	/**
	 * @return the tail of a non-empty list
	 */
	public IList<A> tail();

}
