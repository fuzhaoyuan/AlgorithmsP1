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
        if(item.equals(null)) throw new IllegalArgumentException();
        if(enq == rq.length){
            if(enq != size) rq = sort(rq.length);
            else rq = sort(2 * rq.length);
        }
        rq[enq++] = item;
        size++;
        rand = StdRandom.uniform(enq);
    }

    // remove and return a random item
    public Item dequeue(){
        if(isEmpty()) throw new NoSuchElementException();
        Item item = getItem();
        rq[rand] = null;
        size--;
        if(size == rq.length/4) rq = sort(rq.length/2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample(){
        if(isEmpty()) throw new NoSuchElementException();
        return getItem();
    }

    private Item getItem(){
        while(rq[rand] == null) rand = StdRandom.uniform(enq);
        return rq[rand];
    }

    private Item[] sort(int capacity){
        Item[] sort = (Item[]) new Object[capacity];
        int n = 0;
        for(int s=0; s< rq.length; s++){
            if(rq[s] != null) sort[n++] = rq[s];
        }
        if(capacity == rq.length/2) enq = n;
        rand = 0;
        return sort;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        rqForIt = sort(size);
        StdRandom.shuffle(rqForIt);
        return new RandomizedQueue.ListIterator();
    }

    private class ListIterator implements Iterator<Item>{
        private int current = 0;

        @Override
        public boolean hasNext() {
            return current != size;
        }

        @Override
        public Item next() {
            if(current == size) throw new NoSuchElementException();
            Item item = rqForIt[current++];
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args){
        int n = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);
        for (int a : queue) {
            for (int b : queue)
                System.out.print(a + "-" + b + " ");
            System.out.println();
        }
        for (int i=0;i<17;i++) queue.enqueue(i);
        for (int i=0;i<17;i++) System.out.print(queue.sample() + " ");
    }

}