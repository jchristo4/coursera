import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONST_STD_DEV = 1.96;

    private int numTrials;
    private double sqrtNumTrials;
    private double[] percolationThresholds;

    /**
     * Stats data type for percolation.
     * @param n the grid size
     * @param trials the number of computational experiments
     * @throws IllegalArgumentException if grid size <= 0 or trials <= 0
     */
    public PercolationStats(int n, int trials) {
        if ((n <= 0) || (trials <= 0)) {
            throw new java.lang.IllegalArgumentException();
        } else {
            numTrials = trials;
            sqrtNumTrials = Math.sqrt(numTrials);
            percolationThresholds = new double[numTrials];

            runTrials(n, numTrials);
        }
    }

    /**
     * Returns mean value of computed thresholds.
     * @return mean value of computed thresholds
     */
    public double mean() {
        return StdStats.mean(percolationThresholds);
    }

    /**
     * Returns standard deviation value of computed thresholds.
     * @return standard deviation value of computed thresholds
     */
    public double stddev() {
        return StdStats.stddev(percolationThresholds);
    }

    /**
     * Returns low end point of 95% confidence interval value of computed
     * thresholds.
     * @return low end point of 95% confidence interval value of computed
     *         thresholds
     */
    public double confidenceLo() {
        return mean() - ((CONST_STD_DEV * stddev()) / sqrtNumTrials);
    }

    /**
     * Returns high end point of 95% confidence interval value of computed
     * thresholds.
     * @return high end point of 95% confidence interval value of computed
     *         thresholds
     */
    public double confidenceHi() {
        return mean() + ((CONST_STD_DEV * stddev()) / sqrtNumTrials);
    }

    /**
     * Method that runs computational experiments for a given grid size and
     * number of trials.
     * @param n the grid size
     * @param trials the number of experiments
     */
    private void runTrials(final int n, final int trials) {
        Percolation percolation;
        int totalSites = n * n;
        int numSitesOpened, row, col;

        for (int trial = 1; trial <= trials; trial++) {
            percolation = new Percolation(n);
            numSitesOpened = 0;

            while (!percolation.percolates()) {
                row = StdRandom.uniform(1, n + 1); // n + 1 => include n
                col = StdRandom.uniform(1, n + 1); // n + 1 => include n

                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                    ++numSitesOpened;
                }
            }

            percolationThresholds[trial - 1] = (double) numSitesOpened / totalSites;
        }
    }

    /**
     * Method that prints calculated stats for percolation thresholds.
     */
    private void printResults() {
        System.out.println("mean                      = " + mean());
        System.out.println("stddev                    = " + stddev());
        System.out.println("95% confidence interval   = " + confidenceLo() + ", " + confidenceHi());
    }


    /**
     * Main method for percolation stats data type.
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {
        int n = 0, trials = 0;
        PercolationStats percolationStats;

        if (args.length > 0) {
            n = Integer.parseInt(args[0]);
        }

        if (args.length > 1) {
            trials = Integer.parseInt(args[1]);
        }

        percolationStats = new PercolationStats(n, trials);
        percolationStats.printResults();

    }
}
