package lists.immutable_linked.java;

/**
 * A function of type A->B
 * 
 * @author Martín Escardo
 */
interface Function<A, B>
{
	B apply(A a);
}
