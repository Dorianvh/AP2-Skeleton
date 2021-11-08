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

	@Override
	public void init(char c) {
		sb = new StringBuffer();
		sb.append(c);
	}

	@Override
	public void add(char c) {
		sb.append(c);
	}

	@Override
	public int size() {
		return sb.length();
	}

	@Override
	public char getElement(int position) {
		return sb.charAt(position);
	}

	@Override
	public int hashCode(){
		return sb.toString().hashCode();
	}

	@Override
	public boolean equals(Object o) {
		Identifier id2 = (Identifier) o;
		return sb.toString().equals(id2.sb.toString());
	}

	@Override
	public String value() {
		return sb.toString();
	}
}

