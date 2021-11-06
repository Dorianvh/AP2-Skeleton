package nl.vu.labs.phoenix.ap;

/** @elements
 *    char
 *
 *  @structure
 *    linear
 *  @domain
 *    all names starting with a letter
 *  @constructor
 *
 *
 *     Identifier();
 *     PRE:
 *     POST: creates new identifier object containing a placeholder
 *
 *
 *
 *     Identifier(Identifier src):
 *     PRE:
 *     POST(): new Identifier containing copy of elements of src
 *
 *
 **/

public interface IdentifierInterface {
	/*
	 * [2] Mandatory methods. Make sure you do not modify these!
	 * 	   -- Complete the specifications of these methods
	 */

	void init(char c);
    /*
    PRE: c contains a letter
    POST: the Identifier only contains c
     */

	void add(char c);
    /*
    PRE: c contains a alphanumeric char
    POST:c is add to the end of row of elements
     */

	int size();
    /*
    PRE:
    POST: numberOfElements is returned
     */

	char getElement(int position);
    /*
    PRE: Position => 0 & < size()
    POST: element at position is returned
     */

	boolean equals(Object o);
    /*
     PRE:
     POST: returns true: if identifier is equal to id2.
     	   returns false: if identifier is not equal to id2.
      */

	int hashCode();

	public String value();

	/*
	PRE: Identifier is not empty
    POST:the value of the element is returned
	 */
}