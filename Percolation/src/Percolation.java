import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF pclt;
    private int m;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        m=n;
        pclt = new WeightedQuickUnionUF(n*n);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        int p = col+m*(row-1);
        int qu=p-m, ql=p-1, qd=p+m, qr=p+1;
        pclt.union();
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){}

    // is the site (row, col) full?
    public boolean isFull(int row, int col){}

    // returns the number of open sites
    public int numberOfOpenSites(){}

    // does the system percolate?
    public boolean percolates(){}

    // test client (optional)
    public static void main(String[] args){}
}
