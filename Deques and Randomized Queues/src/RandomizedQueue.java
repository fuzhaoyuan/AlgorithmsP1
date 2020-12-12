import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INIT_CAPACITY = 8;
    private Item[] rq, rqForIt;
    private int enq, size, rand;

    // construct an empty randomized queue
    public RandomizedQueue(){
        rq = (Item[]) new Object[INIT_CAPACITY];
        enq = 0;
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size(){
        return size;
    }

    // add the item
    public void enqueue(Item item){
        if(item == null) throw new IllegalArgumentException();
        if(enq == rq.length){
            if(enq != size) rq = sort(rq.length);
            else rq = sort(2 * rq.length);
            refreshRand();
        }
        rq[enq++] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue(){
        if(isEmpty()) throw new NoSuchElementException();
        refreshRand();
        while(rq[rand] == null) refreshRand();
        Item item = rq[rand];
        rq[rand] = null;
        size--;
        if(size == rq.length/4){
            rq = sort(rq.length/2);
            refreshRand();
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample(){
        if(isEmpty()) throw new NoSuchElementException();
        refreshRand();
        while(rq[rand] == null) refreshRand();
        return rq[rand];
    }

    private void refreshRand(){
        rand = StdRandom.uniform(enq);
    }

    private Item[] sort(int capacity){
        Item[] sort = (Item[]) new Object[capacity];
        int n = 0;
        for(int s=0; s< rq.length; s++){
            if(rq[s] != null) sort[n++] = rq[s];
        }
        if(capacity == rq.length/2 || capacity == rq.length) enq = n;
        return sort;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        rqForIt = sort(size);
        StdRandom.shuffle(rqForIt);
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item>{
        private int current = 0;
        private final Item[] rqForLI = rqForIt;

        @Override
        public boolean hasNext() {
            return current != rqForLI.length;
        }

        @Override
        public Item next() {
            if(current == rqForLI.length) throw new NoSuchElementException();
            return rqForLI[current++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args){
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(914);
        rq.enqueue(280);
        rq.enqueue(950);
        rq.enqueue(972);
        rq.enqueue(45);
        rq.enqueue(114);
        rq.dequeue();
        rq.enqueue(933);
        rq.enqueue(296);
        rq.enqueue(599);
    }

}