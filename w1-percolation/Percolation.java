import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private final int DIM;
  private static final boolean BLOCKED = false;
  private static final boolean OPEN = true;
  private static final int TOP = 0;
  private final int BOTTOM;
  private boolean[] sites;
  private WeightedQuickUnionUF wquuf;
  private WeightedQuickUnionUF backwash;
  private int openSites = 0;

  // create N-by-N grid with all sites blocked
  public Percolation(int n) {
    if (n < 1) throw new IllegalArgumentException("n must be > 1");
    DIM = n;
    BOTTOM = DIM * DIM + 1;
    sites = new boolean[DIM * DIM + 2]; // map 2D input to 1D datastructure 
    sites[0] = OPEN;
    sites[DIM * DIM + 1] = OPEN;
    wquuf = new WeightedQuickUnionUF(DIM * DIM + 2);
    backwash = new WeightedQuickUnionUF(DIM * DIM + 1); // no virtual bottom
  }

  // open site (row, col) if it is not open already
  public void open(int row, int col) {
    this.validate(row, col);
    if (!this.isOpen(row, col)) {
      openSites++;
      sites[this.xyTo1D(row, col)] = OPEN;
      this.connect(row, col);
    }
  }
  
  private void connect(int row, int col) {
    int[][] deltas = { {1, 0}, {0, 1}, {-1, 0}, {0, -1} };
    for (int i = 0; i < deltas.length; i++) {
      int nextRow = row + deltas[i][0];
      int nextCol = col + deltas[i][1];
      if (this.isValid(nextRow, nextCol) && this.isOpen(nextRow, nextCol)) {
        wquuf.union(xyTo1D(row, col), xyTo1D(nextRow, nextCol));
        backwash.union(xyTo1D(row, col), xyTo1D(nextRow, nextCol));
      }
    }

    if (row == 1)  {
      wquuf.union(TOP, xyTo1D(row, col));
      backwash.union(TOP, xyTo1D(row, col));
    }
    if (row == DIM) wquuf.union(BOTTOM, xyTo1D(row, col));
  }

  private boolean isValid(int row, int col) {
    return this.isValid(row) && this.isValid(col);
  }

  private boolean isValid(int i) {
   return i > 0 && i <= DIM;
  }

  // is site (row, col) open?
  public boolean isOpen(int row, int col) {
    this.validate(row, col);
    return sites[this.xyTo1D(row, col)];
  } 
  
  // is site (row, col) full? 
  public boolean isFull(int row, int col) { 
    this.validate(row, col);
    return backwash.connected(xyTo1D(row, col), TOP); 
  }; 
  
  // number of open sites
  public int numberOfOpenSites() {
    return openSites;
  } 

  // does the system percolate?
  public boolean percolates() {
    return wquuf.connected(TOP, BOTTOM);
  }    

  private int xyTo1D(int row, int col) {
    return DIM * (row - 1) + (col % (DIM + 1));
  }

  private void validate(int i) {
    if (i <= 0 || i > DIM) throw new IllegalArgumentException("row index i out of bounds");
  }
  
  private void validate(int i, int j) {
    this.validate(i);
    this.validate(j);
  }

  private void printSites() {
    for (int i = 1; i <= DIM; i++) {
      for (int j = 1; j <= DIM; j++) {
        char open = this.isOpen(i, j) ? 'o' : 'x';
        System.out.print(" " + open + " ");
      }
      System.out.println();
    }
  }

  // test client (optional) 
  public static void main(String[] args) {
    Percolation p = new Percolation(4);
    p.open(1,1);
    System.out.println(p.isOpen(1,1) == true);
    System.out.println(p.isOpen(1,2) == false);
    System.out.println(p.isFull(1, 1) == true);
    System.out.println(p.percolates() == false);
    p.open(2, 1);
    p.open(3, 1);
    /* System.out.println(p.isFull(3, 1) == false); */
    p.open(3, 2);
    p.open(4, 2);
    System.out.println(p.percolates());
    p.printSites();
  }  
}
