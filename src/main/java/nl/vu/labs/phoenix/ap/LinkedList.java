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
    } //thijs

    public boolean isEmpty() { //dor
        return current == null;
    }

    public int size() {
        return nodeCount;
    } //dor


    public ListInterface<E> insert(E d) {
        if(isEmpty()){
            current = new Node(d);
            nodeCount++;
            return this;
        }
        find(d);
        if(!goToNext()){ //if d should be last
            current.next = new Node(d, current, null);
            current = current.next;
            nodeCount++;
            return this;
        }
        if(!goToPrevious()){//if d should be first
            current.prior= new Node(d, null, current);
            current = current.prior;
            nodeCount++;
            return this;
        }
        current.next = current.next.prior = new Node(d, current, current.next); //if d should be in the middle
        current = current.next;
        nodeCount++;
        return this;
    }

    @Override
    public E retrieve() {
        return current.data;
    } //dor

    @Override
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
    } //thijs

    @Override
    public boolean find(E d) {//check
        if(isEmpty()){
            return false;
        }

        goToFirst();
        do {
            if (current.data == d){
                return true;
            }
            if(current.data.compareTo(d) > 0){
                return false;
            }
        } while (goToNext());
        return false;
    }


    @Override
    public boolean goToFirst() {
        if(isEmpty()){
            return false;
        }
        while(current.prior != null){
            current = current.prior;
        }
        return true;
    }//thijs

    @Override
    public boolean goToLast() {
        if(isEmpty()){
            return false;
        }
        while(current.next != null){
            current = current.next;
        }
        return true;
    }//dor

    @Override
    public boolean goToNext() {
        if(isEmpty() || current.next == null){
            return false;
        }
        current = current.next;
        return true;
    }//thijs

    @Override
    public boolean goToPrevious() {
        if(isEmpty() || current.prior == null){
            return false;
        }
        current = current.prior;
        return true;
    }//dor

    @Override
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
    }//thijs
}