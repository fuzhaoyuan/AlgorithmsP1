import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF grid;
    private int[] status;
    private int m, c, r;
    private int p=c+m*(r-1);
    private int pu=p-m, pd=p+m, pl=p-1, pr=p+1;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        grid = new WeightedQuickUnionUF(n*n);
        status = new int[n*n];
        for (int i = 0; i < n*n; i++) {
            status[i] = 0;
        }
        m=n;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        r=row;
        c=col;
        if(!isOpen(r,c)){
            if(isOpen(r-1,c)) grid.union(p,pu);
            if(isOpen(r+1,c)) grid.union(p,pd);
            if(isOpen(r,c-1)) grid.union(p,pl);
            if(isOpen(r,c+1)) grid.union(p,pr);
            status[p] = 1;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        r=row;
        c=col;
        if(status[p]==0) return false;
        else return true;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        r=row;
        c=col;
        for (int q=0;q<m;q++){
            if(grid.connected(p,q)) {
                return true;
            }else return false;
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites(){}

    // does the system percolate?
    public boolean percolates(){}

    // test client (optional)
    public static void main(String[] args){}
}
