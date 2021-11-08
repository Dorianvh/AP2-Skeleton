package nl.vu.labs.phoenix.ap;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;


public class Interpreter<T extends SetInterface<BigInteger>> implements InterpreterInterface<T> {

    HashMap<Identifier, T> hashMap;

    public Interpreter() {
        hashMap = new HashMap<>();
    }

    @Override
    public T getMemory(String v) {
        Scanner in = new Scanner(v);
        in.useDelimiter("");
        Identifier id = new Identifier();
        try {
            id = identifier(in);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return hashMap.get(id);
    }

    @Override
    public T eval(String s) {
        Scanner in = new Scanner(s);
        in.useDelimiter("");
        try {
            return statement(in);
        } catch (APException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private T statement(Scanner in) throws APException { /**TODO**/
        skipSpaces(in);
        if (nextCharIsLetter(in)) {
            assignment(in);
        } else if (nextCharIs(in, '?')) {
            nextChar(in);
            return print_statement(in);
        } else if (nextCharIs(in, '/')) {
        } else {
            throw new APException("Command needs to start with a statement\n");
        }
        return null;
    }

    private void assignment(Scanner in) throws APException {
        Identifier id = identifier(in);

        skipSpaces(in);
        if (!(nextCharIs(in, '='))) {
            throw new APException("An assignment needs to consist of an identifier followed by = followed by an expression");
        }
        nextChar(in);

        T expr = expression(in);
        eoln(in);
        hashMap.put(id, expr);
    }

    private T print_statement(Scanner in) throws APException {
        T print = expression(in);
        eoln(in);
        return print;
    }


    private Identifier identifier(Scanner in) {
        Identifier id = new Identifier();
        id.add(nextChar(in));
        while (nextCharIsLetter(in) || nextCharIsDigit(in)) {
            id.add(nextChar(in));
        }
        return id;
    }

    private T expression(Scanner in) throws APException {
        char operator;
        T t = term(in);
        skipSpaces(in);
        while (additive_Operator(in)) {
            operator = nextChar(in);
            skipSpaces(in);
            t = calculate(t, term(in), operator);
            skipSpaces(in);
        }
        return t;
    }

    private T term(Scanner in) throws APException {
        char operator;
        T f = factor(in);
        skipSpaces(in);
        while (multiplicative_operator(in)) {
            operator = nextChar(in);
            skipSpaces(in);
            f = calculate(f, factor(in), operator);
            skipSpaces(in);
        }
        return f;
    }

    private T factor(Scanner in) throws APException {
        skipSpaces(in);
        if (nextCharIsLetter(in)) {
            Identifier id = identifier(in);
            if (hashMap.containsKey(id)) {
                return hashMap.get(id);
            }
        } else if (nextCharIs(in, '(')) {
            nextChar(in);
            skipSpaces(in);
            return complex_factor(in);
        } else if (nextCharIs(in, '{')) {
            nextChar(in);
            return set(in);
        }
        throw new APException("Factor should be a set, identifier, or a complex factor");
    }

    private T complex_factor(Scanner in) throws APException {
        T cf = expression(in);
        skipSpaces(in);
        if (!nextCharIs(in, ')')) {
            throw new APException("Complex factors should end with ')'");
        }
        nextChar(in);
        return cf;
    }

    private T set(Scanner in) throws APException {
        T set = (T) new Set<BigInteger>();

        skipSpaces(in);
        if (nextCharIsDigit(in)) {
            row_natural_numbers(in, set);
        }
        if (nextCharIs(in, '}')) {
            nextChar(in);
        } else {
            throw new APException("A set can only contain a row of natural numbers and should be closed with '}'");
        }
        return set;
    }

    private boolean additive_Operator(Scanner in) {
        return nextCharIs(in, '+') || nextCharIs(in, '-') || nextCharIs(in, '|');
    }

    private boolean multiplicative_operator(Scanner in) {
        return nextCharIs(in, '*');
    }

    private T calculate(T s1, T s2, char operator) throws APException {
        if (operator == '+') {
            return (T) s1.union(s2);
        }
        if (operator == '-') {
            return (T) s1.difference(s2);
        }
        if (operator == '|') {
            return (T) s1.symmetricDifference(s2);
        }
        if (operator == '*') {
            return (T) s1.intersection(s2);
        }
        throw new APException("Operator is not: +, -, * or |");
    }

    private T row_natural_numbers(Scanner in, T set) {
        set.add(natural_number(in));
        skipSpaces(in);
        while (nextCharIs(in, ',')) {
            nextChar(in);
            skipSpaces(in);
            if (!nextCharIsDigit(in)) {
                return set;
            }
            set.add(natural_number(in));
            skipSpaces(in);
        }
        return set;
    }

    private BigInteger natural_number(Scanner in) {
        if (nextCharIs(in, '0')) {
            nextChar(in);
            return new BigInteger("0");
        }
        return positive_number(in);
    }

    private BigInteger positive_number(Scanner in) {
        StringBuffer number = new StringBuffer();
        while (nextCharIsDigit(in)) {
            number.append(nextChar(in));
        }
        return new BigInteger(number.toString());
    }

    private char nextChar(Scanner in) {
        return in.next().charAt(0);
    }

    private void skipSpaces(Scanner in) {
        while (nextCharIs(in, ' ')) {
            nextChar(in);
        }
    }

    private boolean nextCharIs(Scanner in, char c) {
        return in.hasNext(Pattern.quote(c + ""));
    }

    private boolean nextCharIsDigit(Scanner in) {
        return in.hasNext("[0-9]");
    }

    private boolean nextCharIsLetter(Scanner in) {
        return in.hasNext("[a-zA-Z]");
    }

    private void eoln(Scanner in) throws APException {
        skipSpaces(in);
        if (in.hasNext()) {
            throw new APException("Assignment should end with an expression");
        }
    }
}
