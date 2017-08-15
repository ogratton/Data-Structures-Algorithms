package lists.immutable_linked.java;

/**
 * A function of type A->void
 * 
 * @author Martín Escardo
 */
interface Action<A>
{
	void apply(A a);
}
