package nl.vu.labs.phoenix.ap;

import java.math.BigInteger;

/**
 * A set interpreter for sets of elements of type T
 */
public class Interpreter<T extends SetInterface<BigInteger>> implements InterpreterInterface<T> {

	@Override
	public T getMemory(String v) {

		return null;
	}

	@Override
	public T eval(String s) {
		/**
		 * Evaluate a line of input
		 * @param s 		an expression
		 * @return
		 * 	if the statement is a print return the corresponding set
		 * 	otherwise return null. also return null when an exception occurs (after printing it out!)


		if (the next stament is print){
			read the identifer
			retrieve the set that belongs to the identifier
			return the set
					else
						give clear error message & return null
		}
*/
		return null;
	}

}
