package nl.vu.labs.phoenix.ap;

import java.math.BigInteger;
import java.util.Scanner;

public class Main{

	private void start() {
		InterpreterInterface<Set<BigInteger>> interpreter = new Interpreter<Set<BigInteger>>();
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()) {
			Set set = interpreter.eval(in.nextLine());
			if (set != null) {
				printSet(set);

			}
			//1. Create a scanner on System.in
			//2. call interpreter.eval() on each line

		}
	}

	public static void main(String[] args) {
		new Main().start();
	}

	private void printSet(Set s){
		T id = s.get();


	}

}
