import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] xt;
    private int t;
    private double m;
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        validate(n,trials);
        xt = new double[trials];
        t = trials;
        m = n*n;
        for(int t=0; t<trials; t++){
            Percolation pclt = new Percolation(n);
            while (!pclt.percolates()){
                pclt.open(StdRandom.uniform(n)+1,StdRandom.uniform(n)+1); }
            xt[t] = pclt.numberOfOpenSites()/m;
        }
    }

    private void validate(int n, int t) {
        if (n <= 0 || t <= 0) {
            throw new IllegalArgumentException("index " + n + "or " + t + "is not greater than 0");
        }
    }

    // sample mean of percolation threshold
    public double mean(){
        mean = StdStats.mean(xt);
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        stddev = StdStats.stddev(xt);
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        confidenceLo = mean - 1.96*stddev/Math.sqrt(t);
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        confidenceHi = mean + 1.96*stddev/Math.sqrt(t);
        return confidenceHi;
    }

    // test client (see below)
    public static void main(String[] args){
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n,trials);
        System.out.println("mean = " + ps.mean());
        System.out.println("stddev = " + ps.stddev());
        System.out.println("95% confidence interval = " + "[" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }

}