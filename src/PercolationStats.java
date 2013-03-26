import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Alex 2011
 * Date: 3/24/13
 * Time: 6:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class PercolationStats {
    private  double[] mean;
    //private int[] percCount;
    private double[] dev;
    private double nMean;
    private double nDev;
    private int count;
    private Percolation perc;
    public PercolationStats(int N, int T)    // perform T independent computational experiments on an N-by-N grid
    {
        if (N <= 0 || T <= 0) throw new IllegalArgumentException();
        perc = new Percolation(N);
        dev = new double[T];
        mean = new double[T];
        //percCount = new int[T];
        for (int i = 0; i < T; i++)
        {
            int opened = 0;
            while (!perc.percolates())
            {
                int j = StdRandom.uniform(N)+1;
                int k = StdRandom.uniform(N)+1;
                if(!perc.isOpen(j, k))
                {
                    perc.open(j, k);
                    opened++;
                }

            }
            double percThreshold = (double)opened/(N*N);
            System.out.println(percThreshold);
            //percCount[i] = opened;
            mean[i] = percThreshold;
            perc = new Percolation(N);


        }
        //count = percCount.length;
        count = mean.length;

    }
    public double mean()// sample mean of percolation threshold
    {         /*double[] mean*/
        double sum = 0;
        /*for (int i = 0; i < mean.length; i++)
        {
            sum += mean[i];
        }*/
        for(double i : mean)
        {
            sum += i;
        }
        nMean = sum/mean.length;
        return sum/mean.length;
    }
    public double stddev()// sample standard deviation of percolation threshold
    {

        for (int i = 0; i < mean.length; i++)
        {
            dev[i] =  Math.pow(mean[i] - nMean, 2);
        }
        double sum = 0;
        for (int i = 0; i < dev.length; i++)
        {
            sum += dev[i];
        }
        nDev = Math.sqrt(sum/dev.length);
        return nDev;
    }
    public double confidenceLo()// returns lower bound of the 95% confidence interval
    {
        return nMean - 1.96*(nDev/Math.sqrt(count));
    }
    public double confidenceHi()// returns upper bound of the 95% confidence interval
    {
        return nMean + 1.96*(nDev/Math.sqrt(count));
    }
    public static void main(String[] args)   // test client, described below
    {

        //Random generator = new Random();
        //double[] mean;
        int firstArg = Integer.parseInt(args[0]);
        int secondArg = Integer.parseInt(args[1]);
        /*Percolation perc = new Percolation(firstArg);
        for(int i = 0; i < secondArg; i++)
        {
            int opened = 0;
            while(!perc.percolates())
            {
                int j = generator.nextInt(firstArg-1);
                int k = generator.nextInt(firstArg-1);
                perc.open(j,k);
                opened++;
            }
            double percThreshold = opened/firstArg;
            mean[i] = percThreshold;

        }
        */
        PercolationStats pStats = new PercolationStats(firstArg, secondArg);
        System.out.println(pStats.mean());
        System.out.println(pStats.stddev());
        System.out.println(pStats.confidenceLo()+", "+pStats.confidenceHi());
        /* mean = pStats.mean();
        double dev = pStats.stddev();*/

    }
}