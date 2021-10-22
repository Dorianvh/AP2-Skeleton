package nl.vu.labs.phoenix.ap;

public class Identifier implements IdentifierInterface {

	private StringBuffer sb;
	private static final char PLACEHOLDER = 'p';

	public Identifier(){
		sb = new StringBuffer(PLACEHOLDER);
	}

	public Identifier(Identifier src) {
		this.sb = new StringBuffer(src.sb);
	}

	public void init(char c) {
		sb = new StringBuffer(c);
	}

	public void add(char c) {
		sb.append(c);
	}

	public int size() {
		return sb.length();
	}

	public char getElement(int position) {
		return sb.charAt(position);
	}

	public boolean isIdentical(Identifier id2) {
		return sb.equals(id2.sb);
	}

	public String value() {
		return sb.toString();
	}
}

