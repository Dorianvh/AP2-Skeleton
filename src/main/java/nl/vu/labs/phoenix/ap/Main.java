package nl.vu.labs.phoenix.ap;

import java.math.BigInteger;
import java.util.Scanner;

public class Main<T extends Comparable>{

	private void start() {
		InterpreterInterface<Set<BigInteger>> interpreter = new Interpreter<Set<BigInteger>>();
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()) {
			Set<BigInteger> set = interpreter.eval(in.nextLine());
			if (set != null) {
				printSet(set);
			}
		}
	}

	public static void main(String[] args) {
		new Main().start();
	}

	private void printSet(Set<BigInteger> set) {
		Set<BigInteger> setCopy = set.copy();
		if (setCopy.isEmpty()) {
			System.out.println("");
		}
		while (setCopy.size() != 0) {
			System.out.print(setCopy.get());
			setCopy.remove(setCopy.get());
		}
	}
}
