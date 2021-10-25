package nl.vu.labs.phoenix.ap;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * A set interpreter for sets of elements of type T
 */
public class Interpreter<T extends SetInterface<BigInteger>> implements InterpreterInterface<T> {

	HashMap<Identifier, T> hashMap;

	public Interpreter(){
		hashMap = new HashMap<Identifier, T>();
	}

	@Override
	public T getMemory(String v) {
		Scanner in = new Scanner(v);
		in.useDelimiter(" ");
		Identifier identifier = new Identifier();
		try{
			identifier = identifier(in);
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
		return hashMap.get(identifier);
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
		Scanner in = new Scanner(s);
		in.useDelimiter(" ");
		try{
			 return program(in);
		} catch(APException e){
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * //comment method in.nextLine en call naar statement?
	 *
	 * //Exceptions explicit descriptions? moeten de exceptions een soort hulp zijn voor de gebruiker
	 */

	private T program(Scanner in) throws APException{
		/*while(in.hasNext()){
			Scanner lineScanner = new Scanner(in.nextLine());
			statement(lineScanner);
		}*/
		return statement(in);
	}

	private T statement(Scanner in) throws APException{
		skipSpaces(in);
		if(nextCharIsLetter(in)){
			assignment(in);
			}
		else if(nextCharIs(in, '?')){
			nextChar(in);
			skipSpaces(in);
			return print_statement(in);
		}
		else if(nextCharIs(in, '/')){
			nextChar(in);
			comment(in);
		}
		else{
			throw new APException("Command needs to start with a statement");
		}
		return null;
	}


	private void assignment(Scanner in) throws APException{
		Identifier id = identifier(in);
		T expr;
		skipSpaces(in);
		if(nextCharIs(in, '=')){
			nextChar(in);
			skipSpaces(in);
			expr = expression(in);
			skipSpaces(in);
			if(!eoln(in)){
				throw new APException("Assignment should end with an expression");
			}
		} else{
			throw new APException("an assignment needs to consist of an identifier followed by = followed by an expression");
		}
		hashMap.put(id, expr);
	}

	private T print_statement(Scanner in) throws APException{
		return expression(in);
		//if(!eoln(in)){
		//	throw new APException("Assignment should end with an expression");
		//}
		//TODO check method
	}

	private void comment(Scanner in){
		//goes back to program
	}

	private Identifier identifier(Scanner in) throws APException{
		Identifier id = new Identifier();
		id.init(nextChar(in));
		while(nextCharIsLetter(in) || nextCharIsDigit(in)){
			id.add(nextChar(in));
		}
		skipSpaces(in);
		if(!nextCharIs(in,'*') && !nextCharIs(in, '=')){
			throw new APException("Identifiers can only consist of letters and numbers");
		}
		return id;
	}

	private T expression(Scanner in) throws APException {
		char operator;
		T t1 = term(in);
		T t2;
		skipSpaces(in);
		while (in.hasNext()) {
			if (nextCharIs(in, '+') || nextCharIs(in, '-') || nextCharIs(in, '|')) {
				operator = nextChar(in);
				skipSpaces(in);
				t2 = term(in);
				skipSpaces(in);
			} else {
				throw new APException("There needs to be an additive-operator between two terms");
			}
			t1 = additive_Operator(t1, t2, operator);
		}
		return t1;
	}

	private T term(Scanner in) throws APException{
		char operator;
		T f1 = factor(in);
		T f2;
		skipSpaces(in);
		while (in.hasNext()) {
			if (nextCharIs(in, '+') || nextCharIs(in, '-') || nextCharIs(in, '|')) {
				operator = nextChar(in);
				skipSpaces(in);
				f2 = factor(in);
				skipSpaces(in);
			} else {
				throw new APException("There needs to be an multiplicative-operator between two terms");
			}
			f1 = multiplicative_operator(f1, f2, operator);
		}
		return f1;
	}

	private T factor(Scanner in) throws APException{
		skipSpaces(in); //TODO overbodig?
		if(nextCharIsLetter(in)){
			if (hashMap.containsKey(identifier(in))) {
				return hashMap.get(identifier(in));
			}
		}
		else if(nextCharIs(in, '(')){
			nextChar(in);
			skipSpaces(in);
			return complex_factor(in);
		}
		else if(nextCharIs(in, '{')){
			nextChar(in);
			return set(in);
		}
		throw new APException("Factor should be a set, identifier, or a complex factor");
	}

	private T complex_factor(Scanner in) throws APException{
		T cf = expression(in);
		skipSpaces(in);
		if(!nextCharIs(in, ')')){
			throw new APException("complex factors should end with ')'");
		}
		return cf;
	}

	private T set(Scanner in) throws APException{
		T set = (T) new Set<BigInteger>();
		skipSpaces(in);
		if(nextCharIsDigit(in)){
			set.add(row_natural_numbers(in));
			while(nextCharIs(in, ',')){
				nextChar(in);
				skipSpaces(in);
				set.add(row_natural_numbers(in));
			}
		}
		if(nextCharIs(in, '}')){
			nextChar(in);
			skipSpaces(in);
		} else{
			throw new APException("A set should be closed with '}'");
		}
		return set;
	}

	private BigInteger row_natural_numbers(Scanner in) throws APException {
		if (nextCharIs(in, '0')) {
			nextChar(in);
			return new BigInteger("0");
		} else if(nextCharIsDigit(in)){
			return natural_number(in);
		} else{
			throw new APException("Sets should only contain natural numbers");
		}
	}


	private T additive_Operator(T s1, T s2, char operator) throws APException {
		if(operator == '+'){
			Set set2 = (Set) s2;
			return (T) s1.union(set2);
		}
		if(operator == '-'){
			Set set2 = (Set) s2;
			return (T) s1.difference(set2);
		}

		if (operator == '|'){
			Set set2 = (Set) s2;
			return (T) s1.symmetricDifference(set2);
		}
		throw new APException("Operator is not: +. - or |");
	}

	private T multiplicative_operator(T s1, T s2, char operator) throws APException {
		if (operator == '*'){
			Set set2 = (Set) s2;
			return (T) s1.intersection(set2);
		}
		throw new APException("Operator is not: *");
	}

	private BigInteger natural_number(Scanner in) throws APException{
		if(nextCharIs(in, '0')){
			return new BigInteger("0");
		} else{
			return positive_number(in);
		}
	}

	private BigInteger positive_number(Scanner in) throws APException{
		StringBuffer number = new StringBuffer();
		while(!nextCharIs(in, ',')) {
			if (nextCharIsDigit(in)) {
				number.append(number(in));
				nextChar(in);
			} else if(nextCharIs(in, ' ')){
				skipSpaces(in);
				if(!nextCharIs(in, ',')){
					throw new APException("Sets should only contain numbers");
				}
			}
		}
		return new BigInteger(number.toString());
	}

	private int number(Scanner in){
		if(nextCharIs(in, '0')){
			return zero(in);
		} else{
			return not_zero(in);
		}
	}

	private int zero(Scanner in){
		return 0;
	}

	private int not_zero(Scanner in){
		return nextChar(in);
	}

	private char nextChar(Scanner in) {
		return in.next().charAt(0);
	}

	private void skipSpaces(Scanner in){
		while(nextCharIs(in, ' ')){
			nextChar(in);
		}
	}

	private boolean nextCharIs(Scanner in,char c) {
		return in.hasNext(Pattern.quote(c+""));
	}

	private boolean nextCharIsDigit(Scanner in) {
		return in.hasNext("[0-9]");
	}

	private boolean nextCharIsLetter(Scanner in) {
		return in.hasNext("[a-zA-Z]");
	}

	private boolean eoln(Scanner in){
		return in.hasNext();
	}
}
