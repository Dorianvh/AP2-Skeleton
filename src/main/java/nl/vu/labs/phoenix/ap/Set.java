package nl.vu.labs.phoenix.ap;

public class Set<T extends Comparable<T>> implements SetInterface<T> {

	private LinkedList<T> set;

	public Set(){
		set = new LinkedList();
	}

	 
	public void init() {
		this.set.init();
	}

	 
	public boolean add(T t) {
		if(!set.find(t)){
			set.insert(t);
			return true;
		} return false;
	}

	public boolean checkForPresence(T t) {
		return set.find(t);
	}

	 
	public T get() {
		return set.retrieve();
	}

	 
	public boolean remove(T t) {
		if(set.find(t)){
			set.remove();
			return true;
		}
		return false;
	}

	public boolean isEmpty() {
		return set.isEmpty();
	}

	 
	public int size() {
		return set.size();
	}

	 
	public Set<T> copy() {//check
		Set copy = new Set();
		copy.set = this.set.copy();
		return copy;
	}

	public Set<T> intersection(Set<T> set2) {
		Set intersection = new Set();
		this.set.goToFirst();
		do{
			if(set2.checkForPresence(this.get())){
				intersection.add(this.get());
			}
		}
		while(this.set.goToNext());
		return intersection;
	}

	public Set<T> union(Set<T> set2) {
		Set union = set2.copy();
		this.set.goToFirst();
		do{
			union.add(this.get());
		}
		while(this.set.goToNext());
		return union;
	}

	public Set<T> difference(Set<T> set2) {
		Set difference = this.union(set2);
		set2.set.goToFirst();
		do{
			if(difference.checkForPresence(set2.get())){
				difference.remove(set2.get());
			}
		} while(set2.set.goToNext());
		return difference;
	}

	public Set<T> symmetricDifference(Set set2) {
		Set symmetricDifference = this.union(set2);
		Set intersection = this.intersection(set2);
		intersection.set.goToFirst();
		do{
			if(symmetricDifference.checkForPresence(intersection.get())){
				symmetricDifference.remove(intersection.get());
			}
		} while(intersection.set.goToNext());
		return symmetricDifference;
	}
}