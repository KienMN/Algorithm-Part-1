import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid = null;
    private int size = 0;
    private int numberOfOpenSites = 0;
    private WeightedQuickUnionUF uf = null;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        } else {
            this.size = n;
            this.grid = new boolean[n][n];
            this.uf = new WeightedQuickUnionUF(n * n + 2);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    grid[i][j] = false;
                }
            }
        }
    }

    public void open(int row, int col) {
        if (row < 1 || row > this.size || col < 1 || col > this.size) {
            throw new IllegalArgumentException();
        }
        if (!grid[row - 1][col - 1]) {
            grid[row - 1][col - 1] = true;
            this.numberOfOpenSites += 1;
            int id = getId(row, col);
            if (row == 1) {
                uf.union(id, 0);
            }
            if (row == this.size) {
                uf.union(id, this.size * this.size + 1);
            }

            if (row > 1) {
                int neighborRow = row - 1;
                int neighborCol = col;
                if (isOpen(neighborRow, neighborCol)) {
                    int neighborId = getId(neighborRow, neighborCol);
                    uf.union(id, neighborId);
                }
            }

            if (row < this.size) {
                int neighborRow = row + 1;
                int neighborCol = col;
                if (isOpen(neighborRow, neighborCol)) {
                    int neighborId = getId(neighborRow, neighborCol);
                    uf.union(id, neighborId);
                }
            }

            if (col > 1) {
                int neighborRow = row;
                int neighborCol = col - 1;
                if (isOpen(neighborRow, neighborCol)) {
                    int neighborId = getId(neighborRow, neighborCol);
                    uf.union(id, neighborId);
                }
            }

            if (col < this.size) {
                int neighborRow = row;
                int neighborCol = col + 1;
                if (isOpen(neighborRow, neighborCol)) {
                    int neighborId = getId(neighborRow, neighborCol);
                    uf.union(id, neighborId);
                }
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 1 || row > this.size || col < 1 || col > this.size) {
            throw new IllegalArgumentException();
        }
        return grid[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        if (row < 1 || row > this.size || col < 1 || col > this.size) {
            throw new IllegalArgumentException();
        }
        if (!grid[row - 1][col - 1]) {
            return false;
        }
        int id = getId(row, col);
        return uf.connected(id, 0);
    }

    private int getId(int row, int col) {
        return (row - 1) * this.size + col;
    }

    public int numberOfOpenSites() {
        return this.numberOfOpenSites;
    }

    public boolean percolates() {
        return uf.connected(0, this.size * this.size + 1);
    }

    public static void main(String[] args) {
    }
}