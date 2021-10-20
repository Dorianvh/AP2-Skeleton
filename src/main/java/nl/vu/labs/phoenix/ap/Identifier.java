package nl.vu.labs.phoenix.ap;

public class Identifier implements IdentifierInterface {

	private final static int INITIAL_AMOUNT_OF_ELEMENTS = 1;
	private char[] charArray;
	private int amountOfElements;

	public Identifier(){
		charArray = new char[INITIAL_AMOUNT_OF_ELEMENTS];
		amountOfElements = 0;
	}

	private void copyElements (char[] dest, char[] src, int amount) {
		for (int i = 0; i < amount; i++) {
			dest[i] = src[i];
		}
	}

	public Identifier(Identifier src) {
		charArray = new char[src.charArray.length];
		amountOfElements = src.amountOfElements;
		copyElements(charArray, src.charArray, amountOfElements);
	}

	public void init(char c) {
		amountOfElements = 0;
		add(c);
	}

	private void increaseIdentifierSize () {
		char[] result = new char[charArray.length + 1];
		copyElements(result, charArray, amountOfElements);
		charArray = result;
	}

	public void add(char character) {
		if (amountOfElements == charArray.length) {
			increaseIdentifierSize();
		}

		charArray[amountOfElements] = character;
		amountOfElements += 1;
	}

	public int size() {
		return amountOfElements;
	}

	public char getElement(int position) {
		return charArray[position];
	}

	public boolean isIdentical(Identifier id2) {
		if (amountOfElements != id2.amountOfElements) {
			return false;
		}
		for (int i = 0; i < amountOfElements; i++) {
			if (charArray[i] != id2.charArray[i]) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String value() {
		// TODO Auto-generated method stub
		return null;
	}

}

