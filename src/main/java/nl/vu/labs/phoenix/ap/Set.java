package nl.vu.labs.phoenix.ap;

public class Set<T extends Comparable<T>> implements SetInterface<T> {

    private LinkedList<T> list;

    public Set() {
        list = new LinkedList<>();
    }

    @Override
    public void init() {
        this.list.init();
    }

    @Override
    public boolean add(T t) {
        if (list.find(t)) {
            return false;
        }
        list.insert(t);
        return true;
    }

    @Override
    public boolean checkForPresence(T t) {
        return list.find(t);
    }

    @Override
    public T get() {
        return list.retrieve();
    }

    @Override
    public boolean remove(T t) {
        if (list.find(t)) {
            list.remove();
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public Set<T> copy() {
        Set<T> copy = new Set<>();
        copy.list = this.list.copy();
        return copy;
    }

    @Override
    public Set<T> intersection(SetInterface<T> set2) {
        Set<T> intersection = new Set<>();
        this.list.goToFirst();
        do {
            if (!isEmpty()) {
                if (set2.checkForPresence(this.get())) {
                    intersection.add(this.get());
                }
            }
        }
        while (this.list.goToNext());
        return intersection;
    }

    @Override
    public Set<T> union(SetInterface<T> set2) {
        Set<T> union = (Set<T>) set2.copy();
        this.list.goToFirst();
        do {
            if (!this.isEmpty()) {
                union.add(this.get());
            }
        }
        while (this.list.goToNext());
        return union;
    }

    @Override
    public Set<T> difference(SetInterface<T> set2) {
        Set<T> difference = new Set<>();
        this.list.goToFirst();
        do {
            if (!this.isEmpty()) {
                if (!set2.checkForPresence(get())) {
                    difference.add(get());
                }
            }
        } while (this.list.goToNext());
        return difference;
    }

    @Override
    public Set<T> symmetricDifference(SetInterface<T> set2) {
        Set<T> symmetricDifference = this.union(set2);
        Set<T> intersection = this.intersection(set2);
        intersection.list.goToFirst();
        do {
            if (!intersection.isEmpty()) {
                if (symmetricDifference.checkForPresence(intersection.get())) {
                    symmetricDifference.remove(intersection.get());
                }
            }
        } while (intersection.list.goToNext());
        return symmetricDifference;
    }
}