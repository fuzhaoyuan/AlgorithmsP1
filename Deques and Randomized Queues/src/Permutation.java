import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args){
        int n = 0;
        RandomizedQueue<String> p = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            p.enqueue(StdIn.readString());
            n++;
        }
        for(int i=0;i<n;i++){
            StdOut.println(p.dequeue());
        }
    }
}