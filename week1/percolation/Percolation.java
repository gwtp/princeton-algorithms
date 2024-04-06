import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private WeightedQuickUnionUF sites;
  private int gridSize;
  private int virtualTop;
  private int virtualBottom;
  // private boolean[] connectedBottom;
  private boolean[] openSite;
  private int openSiteCount;

  // creates n-by-n grid, with all sites initially blocked
  public Percolation(int n) {
    if (n < 1) {
      throw new IllegalArgumentException("invalid grid size, must be positive number");
    }
    // Init WeightQuickUnionUF with 2 virtual sites top/bottom.
    sites = new WeightedQuickUnionUF(n * n + 2);
    gridSize = n; // Initial value used to build the n-by-n grid.
    virtualTop = 0; // Explicitly set virtualTop as position 0.
    virtualBottom = n * n + 1;
    openSite = new boolean[n * n + 1];
    // connectedBottom = new boolean[n * n + 1];

    // Connect top virtual site to first row.
    for (int i = 1; i <= n; i++) {
      sites.union(virtualTop, i);
    }
  }

  // opens the site (row, col) if it is not open already
  public void open(int row, int col) {
    // If not isOpen(row, col)
    // Do open
    if (isOpen(row, col)) {
      return;
    }

    int pos = position(row, col);
    // Connect to the site above.
    if (row != 1) { // If not first row.
      if (isOpen(row - 1, col)) {
        int siteAbove = pos - gridSize;
        sites.union(pos, siteAbove);
      }
    }
    // Connect to the site below.
    if (row != gridSize) { // If not last row.
      if (isOpen(row + 1, col)) {
        int siteBelow = pos + gridSize;
        sites.union(pos, siteBelow);
      }
    }
    // Connect to the site on the left.
    if (col != 1) {
      if (isOpen(row, col - 1)) {
        int siteLeft = pos - 1;
        sites.union(pos, siteLeft);
      }
    }
    // Connect to the site on the right.
    if (col != gridSize) {
      if (isOpen(row, col + 1)) {
        int siteRight = pos + 1;
        sites.union(pos, siteRight);
      }
      sites.union(pos, pos + 1);
    }

    // TODO: Handle backwash scenario. Perfect is the enemy of good.
    if (pos > (gridSize * gridSize - gridSize)) {
      sites.union(pos, virtualBottom);
    }

    openSite[pos] = true;
    openSiteCount++;
  }

  private int position(int row, int col) {
    if (row < 1 || row > gridSize || col < 1 || col > gridSize) {
      throw new IllegalArgumentException("Grid position out of bounds");
    }

    int pos = (row - 1) * gridSize + col;
    return pos;
  }

  // is the site (row, col) open?
  public boolean isOpen(int row, int col) {
    int pos = position(row, col);
    return openSite[pos];
  }

  // isFull is the site (row, col) full?
  public boolean isFull(int row, int col) {
    int pos = position(row, col);
    // A full site is an open site that can be connected to an open site
    // in the top row via a chain of neighboring (left, right, up, down)
    // open sites.
    // Check the given site is connected to the top virtual site.
    boolean full = isOpen(row, col) && siteConnected(pos, virtualTop);

    return full;
  }

  private boolean siteConnected(int p, int q) {
    return sites.find(p) == sites.find(q);
  }

  // returns the number of open sites
  public int numberOfOpenSites() {
    return openSiteCount;
  }

  // does the system percolate?
  public boolean percolates() {
    // The system percolates if there is a full site in the bottom row.
    // Check if the virtual top is connected to the virtual bottom
    return siteConnected(virtualBottom, virtualTop);
  }

  // test client (optional)
  public static void main(String[] args) {
    Percolation p = new Percolation(4);
    StdOut.println("Percolates? " + p.percolates());
    StdOut.println("Number of open sites: " + p.numberOfOpenSites());
    int[][] rowCol = { { 1, 4 }, { 2, 4 }, { 3, 4 }, { 4, 4 }, { 4, 1 } };
    for (int i = 0; i < rowCol.length; i++) {
      int row = rowCol[i][0];
      int col = rowCol[i][1];
      StdOut.printf("Opening row:%d, col:%d\n", row, col);
      p.open(row, col);
      StdOut.println("Percolates? " + p.percolates());
    }
    StdOut.println("Number of open sites: " + p.numberOfOpenSites());
    StdOut.println("Percolates? " + p.percolates());
    return;
  }
}