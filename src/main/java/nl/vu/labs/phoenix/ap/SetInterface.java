package nl.vu.labs.phoenix.ap;

public interface SetInterface<T extends Comparable<T>> {

	/*
	 * Elements : Objects of type T
	 * Structure: none
	 * Domain   : only elements of the same type
	 *
	 * Constructors:
	 *
	 * Set();
	 * PRE  -
	 * POST - A new Set-object has been made and is the empty Set.
	 *
	 */

	void init();
		 /* PRE  -
			POST - The set is now empty
		 */

	boolean add(T element);
		/* PRE -
		   POST -   true  - element was inserted
	  	  			false - element was already present
		 */

	boolean checkForPresence(T element);
		/* PRE  -
		   POST - Returns true if element is contained in set
  		    	- Returns false if element is not contained in set
		 */

	T get();
		/*  PRE  - Set is not empty
	   		POST - Returns a copy of a random element
		 */

	boolean remove(T t);
   /*  PRE  -
         POST - Returns true if element T is removed from the set
             - Returns false if element T is not in the set
        */

	

	boolean isEmpty();
		/* PRE  -
    	   POST - returns true if set is empty
    	   		- returns false if set is not empty
		 */

	int size();
		/*PRE -
		  POST - the amount of elements is returned
		 */

	SetInterface<T> copy();
			/* PRE  -
			   POST - A new Set-object has been made and contains a copy of the elements src.
			 */

	Set<T> intersection(Set<T> set2);
		/*  PRE  -
			POST - returns a new set with copies ofThe intersection of set and set2 is returned
		 */


	Set<T> union(Set<T> set2);
		/*  PRE  -
			POST - Returns a new set containing copies of all the elements in both sets
		 */

	Set<T> difference(Set<T> set2);
		/*  PRE  -
			POST - Returns a new set containing copies of the elements in set that are not in set2
		 */

	Set<T> symmetricDifference(Set<T> set2);
		/*  PRE  -
			POST - Returns a new set containing copies of the elements in set1 and set2 that are not in their intersection
		 */
}
