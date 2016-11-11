import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int[][] grid;                 // grid to store site's isOpen status
    private int gridSize;                 // n (of n-by-n grid)
    private int totalSites;               // n * n
    private int indexVirtualTopSite = 0;
    private int indexVirtualBottomSite;
    private WeightedQuickUnionUF wquUf;
    private String msgIndexOutOfBounds = "Error: Row or column index value(s) is out of bounds";

    /**
     * Model of a percolation system.
     * @param n the size to create n-by-n grid
     */
    public Percolation(final int n) {
      gridSize = n;
      totalSites = n * n;

      grid = new int[n][n];

      // adding virtual sites to total sites
      wquUf = new WeightedQuickUnionUF(totalSites + 2);

      indexVirtualBottomSite = totalSites + 1;
    }

    /**
     * Opens site for a given row and column if it is not open already.
     * @param row the row number
     * @param col the column number
     * @throws IndexOutOfBoundsException if row & col values are not within
     *        (1, 1) to (n, n) range
     */
    public void open(final int row, final int col) {
        int indexWquUf;

        if (isValidIndices(row, col)) {
            indexWquUf = getWquUfIndex(row, col);

            if (grid[row - 1][col - 1] == 1) {
                return;
            }

            grid[row - 1][col - 1] = 1;

            if (row > 1 && isOpen(row - 1, col)) {
                wquUf.union(indexWquUf, getWquUfIndex(row - 1, col));
            }

            if (row < gridSize && isOpen(row + 1, col)) {
                wquUf.union(indexWquUf, getWquUfIndex(row + 1, col));
            }

            if (col > 1 && isOpen(row, col - 1)) {
                wquUf.union(indexWquUf, getWquUfIndex(row, col - 1));
            }

            if (col < gridSize && isOpen(row, col + 1)) {
                wquUf.union(indexWquUf, getWquUfIndex(row, col + 1));
            }

            // open connections to top and bottom virtual sites
            if (row == 1) {
                wquUf.union(indexWquUf, indexVirtualTopSite);
            }

            if (row == gridSize) {
                wquUf.union(indexWquUf, indexVirtualBottomSite);
            }

        } else {
            throw new java.lang.IndexOutOfBoundsException(msgIndexOutOfBounds);
        }
    }


    /**
     * Checks if a site is open or not given a row and column.
     * @param row the row number
     * @param col the column number
     * @return {@code true} if site is open, {@code false} otherwise
     * @throws IndexOutOfBoundsException if row & col values are not within
     *         (1, 1) to (n, n) range
     */
    public boolean isOpen(final int row, final int col) {
        if (isValidIndices(row, col)) {
            return grid[row - 1][col - 1] == 1;
        }

        throw new java.lang.IndexOutOfBoundsException(msgIndexOutOfBounds);
    }


    /**
     * Checks if an open site can be connected to an open site in the top row
     * via neighbouring open sites.
     * @param row the row number
     * @param col the column number
     * @return {@code true} if site is a full site, {@code false} otherwise
     * @throws IndexOutOfBoundsException if row & col values are not within
     *         (1, 1) to (n, n) range
     */
    public boolean isFull(final int row, final int col) {
        boolean isFull = false;

        if (isValidIndices(row, col)) {
            if (isOpen(row, col)) {
                isFull = wquUf.connected(indexVirtualTopSite, getWquUfIndex(row, col));
            }

        } else {
            throw new java.lang.IndexOutOfBoundsException(msgIndexOutOfBounds);
        }

        return isFull;
    }


    /**
     * Checks if the system percolates (if any open site on bottom row is
     * connected to a top row open site).
     * @return {@code true} if site percolates, {@code false} otherwise
     */
    public boolean percolates() {
        return wquUf.connected(indexVirtualTopSite, indexVirtualBottomSite);
    }


    /**
     * Calculates the WeightedQuickUnionUF data structure index for given site
     * row and column values.
     * @param row the row number
     * @param col the column number
     * @return the index of WeightedQuickUnionUF data structure
     */
    private int getWquUfIndex(final int row, final int col) {
        return ((row - 1) * gridSize) + col;
    }


    /**
     * Checks if a given row and column values fall within valid range
     * - (1, 1) to (n, n).
     * @param row the row number
     * @param col the column number
     * @return {@code true} if row and column values are valid,
     *         {@code false} otherwise
     */
    private boolean isValidIndices(final int row, final int col) {
        return row > 0 && row <= gridSize && col > 0 && col <= gridSize;
    }


    /**
     * Unit tests for Percolation model.
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {

    }
}
