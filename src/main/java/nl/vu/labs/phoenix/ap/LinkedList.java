package nl.vu.labs.phoenix.ap;

public class LinkedList<E extends Comparable<E>> implements ListInterface<E> {

    private class Node {

        E data;
        Node prior, next;

        public Node(E data) {
            this(data, null, null);
        }

        public Node(E data, Node prior, Node next) {
            this.data = data == null ? null : data;
            this.prior = prior;
            this.next = next;
        }
    }

    private Node current;
    private int nodeCount;

    public LinkedList() {
        init();
    }

    public ListInterface<E> init() {
        current = null;
        nodeCount = 0;
        return this;
    }

    public boolean isEmpty() { //dor
        return current == null;
    }

    public ListInterface<E> insert(E d) {
        if(isEmpty()){
            current = new Node(d);
            nodeCount++;
            return this;
        }
        find(d);
        if(current.prior == null) {//all cases where the current is the first node
            if(current.data.compareTo(d) < 0 && current.next == null){
                current = current.next = new Node(d, current, null);
            }
            else if(current.data.compareTo(d) < 0 && current.next != null){
                    current = current.next = current.next.prior = new Node(d, current, current.next);
            }
            else {
                current = current.prior = new Node(d, null, current);
            }
        }
        else if(current.next != null){//the current node is somewhere in the middle
            current = current.next = current.next.prior = new Node(d, current, current.next);
        }
        else {//the current node is the last element
            current = current.next = new Node(d, current, null);
        }
        nodeCount++;
        return this;
    }

    public boolean find(E d) {//check
        if(isEmpty()){
            return false;
        }
        goToFirst();
        if(current.data.compareTo(d) > 0 || current.next == null){
            return false;
        }
        goToNext();
        do {
            if (current.data == d){
                return true;
            }
            if(current.data.compareTo(d) > 0){
                current = current.prior;
                return false;
            }
        } while (goToNext());
        return false;
    }

    public int size() {
        return nodeCount;
    }

    public E retrieve() {
        return current.data;
    }

    public ListInterface<E> remove() {//check
        if(current.next == null && current.prior == null){
            return init();
        }
        if(goToPrevious()){
            current.next = current.next.next;
        }
        if(goToNext()){
            current.prior = current.prior.prior;
        }
        nodeCount --;
        return this;
    }

    public boolean goToFirst() {
        if(isEmpty()){
            return false;
        }
        while(current.prior != null){
            current = current.prior;
        }
        return true;
    }

    public boolean goToLast() {
        if(isEmpty()){
            return false;
        }
        while(current.next != null){
            current = current.next;
        }
        return true;
    }

    public boolean goToNext() {
        if(isEmpty() || current.next == null){
            return false;
        }
        current = current.next;
        return true;
    }

    public boolean goToPrevious() {
        if(isEmpty() || current.prior == null){
            return false;
        }
        current = current.prior;
        return true;
    }

    public ListInterface<E> copy() {
        Node replace = this.current;
        goToFirst();
        LinkedList result = new LinkedList();
        do{
            result.insert(this.current.data);
            result.goToNext();
        } while(goToNext());
        this.current = replace;
        return result;
    }
}