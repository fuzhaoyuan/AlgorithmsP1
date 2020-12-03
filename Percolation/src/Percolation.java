import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF grid;
    private int[] status;
    private int m;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        test(n);
        grid = new WeightedQuickUnionUF(n*n+2);
        status = new int[n*n];
        for (int i = 0; i < n*n; i++) {
            status[i] = 0;
        }
        m=n;
    }

    // validate that p is a valid index
    private void validate(int r, int c) {
        if (r < 1 || r > m) {
            throw new IllegalArgumentException("index " + r + " is not between 1 and " + m);
        }else if (c < 1 || c > m) {
            throw new IllegalArgumentException("index " + c + " is not between 1 and " + m);
        }
    }
    private void test(int p) {
        if (p <= 0) {
            throw new IllegalArgumentException("index " + p + " is not greater than 0");
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        int r=row, c=col;
        validate(r,c);
        int p=c+m*(r-1)-1;
        int pu=p-m, pd=p+m, pl=p-1, pr=p+1;
        if(!isOpen(r,c)){
            if(r>1 && isOpen(r-1,c)){
                grid.union(p,pu);
            }else if(r==1) grid.union(p,m*m);
            if(r<m && isOpen(r+1,c)){
                grid.union(p,pd);
            }else if(r==m) grid.union(p,m*m+1);
            if(c>1 && isOpen(r,c-1)) grid.union(p,pl);
            if(c<m && isOpen(r,c+1)) grid.union(p,pr);
            status[p] = 1;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        int r=row, c=col;
        validate(r,c);
        int p=c+m*(r-1)-1;
        if(status[p]==1) return true;
        else return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        int r=row, c=col;
        validate(r,c);
        int p=c+m*(r-1)-1;
        return (grid.find(p) == grid.find(m*m));
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        int num = 0;
        for(int s: status){
            if(s==1) num++;
        }
        return num;
    }

    // does the system percolate?
    public boolean percolates(){
        return (grid.find(m*m) == grid.find(m*m+1));
    }

    // test client (optional)
    public static void main(String[] args){}
}
