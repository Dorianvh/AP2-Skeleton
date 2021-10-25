package nl.vu.labs.phoenix.ap;

public class Identifier implements IdentifierInterface {

	private StringBuffer sb;
	private static final char PLACEHOLDER = 'p';

	public Identifier(){
		init(PLACEHOLDER);
	}

	public Identifier(Identifier src) {
		sb = new StringBuffer();
		for (int i = 0; i < src.size(); i++){
			add(src.getElement(i));
		}
	}

	public void init(char c) {
		sb = new StringBuffer();
		sb.append(c);
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

	public boolean isIdentical(Identifier id2) { //TODO rename equel also in interface
		return sb.toString().equals(id2.sb.toString());
	}
	//TODO hashcode en equels in interface

	public String value() {
		return sb.toString();
	}
}

