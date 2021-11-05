package nl.vu.labs.phoenix.ap;

public class Set<T extends Comparable<T>> implements SetInterface<T> {

	private LinkedList<T> list;

	public Set(){
		list = new LinkedList<T>();
	}

	 
	public void init() {
		this.list.init();
	}

	 
	public boolean add(T t) {
		if(list.find(t)){
			return false;
		}
		list.insert(t);
		return true;
	}

	public boolean checkForPresence(T t) {
		return list.find(t);
	}

	 
	public T get() {

		return list.retrieve();
	}

	 
	public boolean remove(T t) {
		if(list.find(t)){
			list.remove();
			return true;
		}
		return false;
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	 
	public int size() {
		return list.size();
	}

	 
	public Set<T> copy() {//check
		Set copy = new Set();
		copy.list = this.list.copy();
		return copy;
	}

	public Set<T> intersection(Set<T> set2) {
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

	public Set<T> union(Set<T> set2) {
		Set union = set2.copy();
		this.list.goToFirst();
		do{
			union.add(this.get());
		}
		while(this.list.goToNext());
		return union;
	}

	public Set<T> difference(Set<T> set2) {
		Set difference = this.union(set2);
		set2.list.goToFirst();
		do{
			if(difference.checkForPresence(set2.get())){
				difference.remove(set2.get());
			}
		} while(set2.list.goToNext());
		return difference;
	}

	public Set<T> symmetricDifference(Set set2) {
		Set symmetricDifference = this.union(set2);
		Set intersection = this.intersection(set2);
		intersection.list.goToFirst();
		do{
			if(symmetricDifference.checkForPresence(intersection.get())){
				symmetricDifference.remove(intersection.get());
			}
		} while(intersection.list.goToNext());
		return symmetricDifference;
	}
}