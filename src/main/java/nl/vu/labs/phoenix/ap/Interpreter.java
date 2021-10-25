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
		in.useDelimiter("");
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
		Scanner in = new Scanner(s);
		in.useDelimiter("");
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
		//TODO endl
	}

	private T statement(Scanner in) throws APException{
		skipSpaces(in);
		if(nextCharIsLetter(in)){
			assignment(in);
		}
		else if(nextCharIs(in, '?')){
			nextChar(in);

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

		skipSpaces(in);
		if(!(nextCharIs(in, '='))){
			throw new APException("an assignment needs to consist of an identifier followed by = followed by an expression");
		}
		nextChar(in);

		T expr = expression(in);
		//TODO endf skipspaces

		if(!eoln(in)){
			throw new APException("Assignment should end with an expression");
		}
		hashMap.put(id, expr);
	}

	private T print_statement(Scanner in) throws APException {
		T print = expression(in);
		eoln(in);
		return print;
	}
	private void comment(Scanner in){
		//goes back to program
	}

	private Identifier identifier(Scanner in) {
		Identifier id = new Identifier();
		id.init(nextChar(in));
		while(nextCharIsLetter(in) || nextCharIsDigit(in)){
			id.add(nextChar(in));
		}
		return id;
	}

	private T expression(Scanner in) throws APException {
		char operator;
		T t = term(in);
		skipSpaces(in);
		while (nextCharIs(in, '+') || nextCharIs(in, '-') || nextCharIs(in, '|')) {
			operator = nextChar(in);
			t = calculate(t, term(in), operator);
			skipSpaces(in);
		}
		return t;
	}

	private T term(Scanner in) throws APException{ //TODO same as expression
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
			f1 = calculate(f1, f2, operator);
		}
		return f1;
	}

	private T factor(Scanner in) throws APException{
		skipSpaces(in);
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

	private T set(Scanner in) throws APException{//TODO opruimen
		T set = (T) new Set<BigInteger>();
		skipSpaces(in);
		if(nextCharIsDigit(in)){
			set.add(row_natural_numbers(in));

		}
		if(nextCharIs(in, '}')){
			nextChar(in);
			skipSpaces(in);
		} else{
			throw new APException("A set should be closed with '}'");
		}
		return set;
	}

	private T row_natural_numbers(Scanner in) throws APException {
		T set = (T) new Set<BigInteger>();

		while(nextCharIs(in, ',')){
			nextChar(in);
			skipSpaces(in);
			set.add(natural_number(in));
		}
		return set;

	}

	private boolean additive_Operator(char c){
		return (c == '+' || c == '-' || c == '|');
	}

	private boolean multiplicative_operator(char c) {
		return (c == '*');
	}

	private T calculate(T s1, T s2, char operator) throws APException { //TODO calculate maken
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

		if (operator == '*'){
			Set set2 = (Set) s2;
			return (T) s1.intersection(set2);
		}
		throw new APException("Operator is not: +, -, * or |");
	}


	private BigInteger natural_number(Scanner in) throws APException{
		if(nextCharIs(in, '0')){
			nextChar(in);
			return new BigInteger("0");
		}
			return positive_number(in);


	}

	private BigInteger positive_number(Scanner in) throws APException{
		StringBuffer number = new StringBuffer(); //
		while(nextCharIsDigit(in)) {
			number.append(nextChar(in));
		}
		return new BigInteger(number.toString());
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
