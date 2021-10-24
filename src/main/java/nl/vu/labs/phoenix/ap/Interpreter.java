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
			program(in);
		} catch(APException e){
			System.out.println(e.getMessage());
			return null;
		}
		return null;
	}

	/**
	 * //Moet na de return null statement in eval alle input gelezen zijn?
	 * Zo ja doe je dit door na elke "dead end" een call naar statement te maken en daar een check te hebben zoals hasNextLine
	 *
	 * //comment method in.nextLine en call naar statement?
	 *
	 * //Exceptions explicit descriptions? moeten de exceptions een soort hulp zijn voor de gebruiker
	 */

	private void program(Scanner in) throws APException{
		/*while(in.hasNext()){
			Scanner lineScanner = new Scanner(in.nextLine());
			statement(lineScanner);
		}*/
		statement(in);
	}

	private void statement(Scanner in) throws APException{
		skipSpaces(in);
		if(nextCharIsLetter(in)){
			assignment(in);
			}
		else if(nextCharIs(in, '?')){
			nextChar(in);
			skipSpaces(in);
			print_statement(in);
		}
		else if(nextCharIs(in, '/')){
			nextChar(in);
			comment(in);
		}
		else{
			throw new APException("Command needs to start with a statement");
		}
	}


	private void assignment(Scanner in) throws APException{
		Identifier id = identifier(in);
		skipSpaces(in);
		if(nextCharIs(in, '=')){
			nextChar(in);
			skipSpaces(in);
			T expr = expression(in);
			skipSpaces(in);
			if(!eoln(in)){
				throw new APException("Assignment should end with an expression");
			}
		} else{
			throw new APException("an assignment needs to consist of an identifier followed by = followed by an expression");
		}
		hashMap.put(id, expr);
	}

	private void print_statement(Scanner in) throws APException{
		expression(in);
		if(!eoln(in)){
			throw new APException("Assignment should end with an expression");
		}
	}

	private void comment(Scanner in){
		//goes back to program
	}

	private Identifier identifier(Scanner in) throws APException{
		if(!nextCharIsLetter(in)){
			throw new APException("Identifiers need to start with a letter");
		}
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

	private void expression(Scanner in) throws APException{
		term(in);
		skipSpaces(in);
		while(in.hasNext()){
			if(nextCharIs(in, '+') || nextCharIs(in, '-') || nextCharIs(in, '|')){
				additive_Operator(in);
				skipSpaces(in);
				term(in);
				skipSpaces(in);
			} else{
				throw new APException("There needs to be an additive-operator between two terms");
			}
		}
	}

	private void term(Scanner in) throws APException{
		factor(in);
		skipSpaces(in);
		while(!nextCharIs(in, '+') || nextCharIs(in, '-') || nextCharIs(in, '|')){
			if(nextCharIs(in, '*')){
				multiplicative_operator(in);
				skipSpaces(in);
				factor(in);
				skipSpaces(in);
			} else{
				throw new APException("There needs to be an multiplicative-operator between two factors");
			}
		}
	}

	private void factor(Scanner in) throws APException{
		if(nextCharIsLetter(in)){
			identifier(in);
			skipSpaces(in);
		}
		else if(nextCharIs(in, '(')){
			nextChar(in);
			skipSpaces(in);
			complex_factor(in);
		}
		else if(nextCharIs(in, '{')){
			nextChar(in);
			set(in);
		}
		else{
			throw new APException("Factor should be a set, identifier, or a complex factor");
		}
	}

	private void complex_factor(Scanner in) throws APException{
		expression(in);
		skipSpaces(in);
		if(!nextCharIs(in, ')')){
			throw new APException("complex factors should end with ')'");
		}
	}

	private void set(Scanner in) throws APException{
		row_natural_numbers(in);
		if(nextCharIs(in, '}')){
			nextChar(in);
			skipSpaces(in);
		} else{
			throw new APException("A set should be closed with '}'");
		}
	}

	private void row_natural_numbers(Scanner in) throws APException{
		while(!nextCharIs(in, '}')){
			skipSpaces(in);
			if(nextCharIsDigit(in)){
				natural_number(in);
				skipSpaces(in);
			}else{
				throw new APException("sets should only contain natural numbers");
			}
			if(nextCharIs(in, ',')){
				nextChar(in);
				skipSpaces(in);
			}else{
				if(!nextCharIs(in, '}')){
					throw new APException("Natural numbers should be seperated by a ','");
				}
			}
		}
	}

	private void additive_Operator(Scanner in){
		if(nextCharIs(in, '+')){
			//define
			nextChar(in);
			skipSpaces(in);
		}else if(nextCharIs(in, '-')){
			//define
			nextChar(in);
			skipSpaces(in);
		}else{
			//"|" define
			nextChar(in);
			skipSpaces(in);
		}
	}

	private void multiplicative_operator(Scanner in){
		//define
		nextChar(in);
		skipSpaces(in);
	}

	private void natural_number(Scanner in) throws APException{
		if(nextCharIs(in, '0')){
			zero(in);
		} else{
			positive_number(in);
		}
	}

	private void positive_number(Scanner in) throws APException{
		while(!nextCharIs(in, ',')) {
			if (nextCharIsDigit(in)) {
				number(in);
				nextChar(in);
			} else{
				throw new APException("Sets should only contain numbers");
			}
		}
	}

	private void number(Scanner in){
		if(nextCharIs(in, '0')){
			zero(in);
		} else{
			not_zero(in);
		}
	}

	private void zero(Scanner in){
		//add zero
	}

	private void not_zero(Scanner in){
		//add not zero
	}

	private void letter(Scanner in){
		//add all letters including hoofdletters
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
