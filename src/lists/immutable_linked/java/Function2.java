package lists.immutable_linked.java;

/**
 * A function of type A->B->C
 * 
 * @author Martín Escardo
 */
interface Function2<A, B, C>
{
	C apply(A a, B b);
}
