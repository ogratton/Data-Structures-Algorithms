package lists.immutable_linked.java;

/**
 * A function of type A->boolean
 * 
 * @author Martín Escardo
 */
interface Predicate<A>
{
	boolean holds(A a);
}