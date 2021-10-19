package nl.vu.labs.phoenix.ap;

public class Set<T extends Comparable<T>> implements SetInterface<T> {

	private LinkedList<T> list;

	public Set(){
		list = new LinkedList();
	}

	@Override
	public void init() {
		this.list.init();
	}

	@Override
	public boolean add(T t) {
		if(!list.find(t)){
			list.insert(t);
			return true;
		} return false;
	}

	public boolean checkForPresence(T t) {
		return list.find(t);
	}

	@Override
	public T get() {
		return list.retrieve();
	}

	@Override
	public boolean remove(T t) {//check interface
		if(list.find(t)){
			list.remove();
			return true;
		}
		return false;
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public Set<T> copy() {//check
		return null;
	}

	@Override
	public SetInterface<T> intersection(SetInterface<T> set2) {

		Set intersection = new Set();
		this.list.goToFirst();
		do{
			if(set2.checkForPresence(this.get())){
				intersection.add(this.get());
			}
		}
		while(this.list.goToNext());
		return intersection;
	}

	@Override
	public SetInterface<T> union(SetInterface<T> set2) {
		//Set union = set2.copy();//casting
		//if(union)
		return null;
	}

	@Override
	public SetInterface<T> difference(SetInterface<T> set2) {
		return null;
	}

	@Override
	public SetInterface<T> symmetricDifference(SetInterface<T> set2) {
		return null;
	}

}