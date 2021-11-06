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

    public boolean isEmpty() {
        return nodeCount == 0;
    }

    public ListInterface<E> insert(E d) {
        if(isEmpty()){
            current = new Node(d);
            nodeCount++;
            return this;
        }
        find(d);
        if(current.data.compareTo(d) > 0) {
            current = current.prior = new Node(d, null, current);
        }
        else if(current.next != null){
            current = current.next = current.next.prior = new Node(d, current, current.next);
        }
        else {
            current = current.next = new Node(d, current, null);
        }
        nodeCount++;
        return this;
    }

    public boolean find(E d) {
        if (isEmpty()) {
            return false;
        }
        goToFirst();
        do {
            if (current.data.compareTo(d) == 0) {
                return true;
            } else if (current.data.compareTo(d) > 0) {
                goToPrevious();
                return false;
            }
        } while(goToNext());

        return false;
    }


    public int size() {
        return nodeCount;
    }

    public E retrieve() {
        return current.data;
    }

    public ListInterface<E> remove() {
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

    public LinkedList<E> copy() {
        if(isEmpty()){
            return new LinkedList();
        }
        Node placeholder = current;
        goToFirst();
        LinkedList result = new LinkedList();
        do{
            result.insert(this.retrieve());
        } while(goToNext());
        current = placeholder;
        return result;
    }
}